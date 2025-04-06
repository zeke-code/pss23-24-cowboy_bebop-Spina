package github.zekecode.cowboybebop.systems;

import github.zekecode.cowboybebop.components.*;
import github.zekecode.cowboybebop.core.Entity;
import github.zekecode.cowboybebop.core.System;
import github.zekecode.cowboybebop.core.World;
import github.zekecode.cowboybebop.util.InputManager;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class PlayerControlSystem implements System {
  private final World world;
  private final InputManager inputManager;

  public PlayerControlSystem(World world, InputManager inputManager) {
    this.world = world;
    this.inputManager = inputManager;
  }

  @Override
  public void update(double deltaTime) {
    for (Entity entity : world.getEntitiesWithComponent(PlayerComponent.class)) {
      if (entity.hasComponent(VelocityComponent.class)) {
        handlePlayerMovement(entity);
      }

      if (entity.hasComponent(TransformComponent.class)) {
        handlePlayerShooting(entity, deltaTime);
      }
    }
  }

  private void handlePlayerMovement(Entity player) {
    VelocityComponent velocity = player.getComponent(VelocityComponent.class);

    // Reset velocity
    velocity.setVx(0);
    velocity.setVy(0);

    // Apply velocity based on input
    if (inputManager.isKeyPressed("W")) {
      velocity.setVy(-velocity.getMaxSpeed());
    }
    if (inputManager.isKeyPressed("S")) {
      velocity.setVy(velocity.getMaxSpeed());
    }
    if (inputManager.isKeyPressed("A")) {
      velocity.setVx(-velocity.getMaxSpeed());
    }
    if (inputManager.isKeyPressed("D")) {
      velocity.setVx(velocity.getMaxSpeed());
    }
  }

  private void handlePlayerShooting(Entity player, double deltaTime) {
    PlayerComponent playerComponent = player.getComponent(PlayerComponent.class);
    TransformComponent transform = player.getComponent(TransformComponent.class);

    // Update shooting cooldown
    playerComponent.updateTimer(deltaTime);

    // Check if player wants to shoot using the mouse
    if (inputManager.isMousePressed() && playerComponent.canShoot()) {
      // Calculate direction from player to mouse
      double dx = inputManager.getMouseX() - transform.getX();
      double dy = inputManager.getMouseY() - transform.getY();

      // Normalize the vector
      double length = Math.sqrt(dx * dx + dy * dy);
      if (length > 0) {
        dx = dx / length;
        dy = dy / length;
      }

      // Create projectile with the direction determined by mouse position
      createProjectile(
          transform.getX(),
          transform.getY(),
          dx * playerComponent.getShootSpeed(),
          dy * playerComponent.getShootSpeed());

      // Reset shoot timer
      playerComponent.resetShootTimer();
    }
  }

  private void createProjectile(double x, double y, double vx, double vy) {
    Entity projectile = world.createEntity();

    // Transform component for position
    projectile.addComponent(new TransformComponent(x, y));

    // Velocity component
    VelocityComponent velocity = new VelocityComponent(500);
    velocity.setVx(vx);
    velocity.setVy(vy);
    projectile.addComponent(velocity);

    // Sprite component
    Rectangle bulletShape = new Rectangle(10, 4);
    projectile.addComponent(new SpriteComponent(bulletShape, Color.YELLOW));

    // Collision component
    projectile.addComponent(new CollisionComponent(5, false, "projectile"));

    // Projectile component
    projectile.addComponent(new ProjectileComponent(10, 2.0, "player"));
  }
}
