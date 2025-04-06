package github.zekecode.cowboybebop.systems;

import github.zekecode.cowboybebop.components.*;
import github.zekecode.cowboybebop.core.Entity;
import github.zekecode.cowboybebop.core.System;
import github.zekecode.cowboybebop.core.World;
import github.zekecode.cowboybebop.factory.EntityFactory;
import java.util.Random;

public class EnemySystem implements System {
  private final World world;
  private final EntityFactory entityFactory;
  private final double screenWidth;
  private final double screenHeight;
  private final Random random;

  private double spawnTimer;
  private double spawnRate;
  private int enemiesSpawned;
  private int maxEnemies;

  public EnemySystem(
      World world, EntityFactory entityFactory, double screenWidth, double screenHeight) {
    this.world = world;
    this.entityFactory = entityFactory;
    this.screenWidth = screenWidth;
    this.screenHeight = screenHeight;
    this.random = new Random();
    this.spawnTimer = 0;
    this.spawnRate = 1.0; // One enemy per second initially
    this.enemiesSpawned = 0;
    this.maxEnemies = 100; // Limit total enemies spawned
  }

  @Override
  public void update(double deltaTime) {
    // Update all existing enemies
    updateEnemies(deltaTime);

    // Handle enemy spawning
    spawnTimer += deltaTime;
    if (spawnTimer >= spawnRate && enemiesSpawned < maxEnemies) {
      spawnEnemy();
      spawnTimer = 0;

      // Gradually increase spawn rate
      spawnRate = Math.max(0.5, spawnRate * 0.99);
      enemiesSpawned++;
    }
  }

  private void updateEnemies(double deltaTime) {
    // Find the player entity
    Entity playerEntity = null;
    for (Entity entity : world.getEntitiesWithComponent(PlayerComponent.class)) {
      playerEntity = entity;
      break;
    }

    // If no player is found, enemies don't need to move
    if (playerEntity == null) return;

    TransformComponent playerTransform = playerEntity.getComponent(TransformComponent.class);

    // Update enemy movement to follow player
    for (Entity enemy : world.getEntitiesWithComponent(EnemyComponent.class)) {
      if (enemy.hasComponent(TransformComponent.class)
          && enemy.hasComponent(VelocityComponent.class)) {

        TransformComponent enemyTransform = enemy.getComponent(TransformComponent.class);
        VelocityComponent enemyVelocity = enemy.getComponent(VelocityComponent.class);
        EnemyComponent enemyComponent = enemy.getComponent(EnemyComponent.class);

        // Calculate direction to player
        double dx = playerTransform.getX() - enemyTransform.getX();
        double dy = playerTransform.getY() - enemyTransform.getY();
        double length = Math.sqrt(dx * dx + dy * dy);

        // Set velocity towards player
        if (length > 0) {
          enemyVelocity.setVx(
              (dx / length) * enemyVelocity.getMaxSpeed() * enemyComponent.getSpeedFactor());
          enemyVelocity.setVy(
              (dy / length) * enemyVelocity.getMaxSpeed() * enemyComponent.getSpeedFactor());
        }
      }
    }
  }

  private void spawnEnemy() {
    // Determine spawn position (from edges of the screen)
    double x, y;
    int side = random.nextInt(4); // 0-3 for top, right, bottom, left

    switch (side) {
      case 0: // Top
        x = random.nextDouble() * screenWidth;
        y = 0;
        break;
      case 1: // Right
        x = screenWidth;
        y = random.nextDouble() * screenHeight;
        break;
      case 2: // Bottom
        x = random.nextDouble() * screenWidth;
        y = screenHeight;
        break;
      default: // Left
        x = 0;
        y = random.nextDouble() * screenHeight;
        break;
    }

    // Create enemy with random properties
    String enemyType;
    double rand = random.nextDouble();
    if (rand < 0.7) {
      enemyType = "basic";
    } else if (rand < 0.9) {
      enemyType = "speedy";
    } else {
      enemyType = "tank";
    }

    // Use the factory to create the enemy
    entityFactory.createEnemy(x, y, enemyType);

    // Note: enemiesSpawned counter is already incremented in the update method
  }
}
