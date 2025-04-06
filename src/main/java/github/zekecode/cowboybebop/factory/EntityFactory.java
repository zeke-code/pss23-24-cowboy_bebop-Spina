package github.zekecode.cowboybebop.factory;

import github.zekecode.cowboybebop.components.*;
import github.zekecode.cowboybebop.core.Entity;
import github.zekecode.cowboybebop.core.World;
import java.util.Random;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class EntityFactory {
  private final World world;
  private final Random random = new Random();

  public EntityFactory(World world) {
    this.world = world;
  }

  public Entity createPlayer(double x, double y) {
    Entity player = world.createEntity();

    // Transform component
    player.addComponent(new TransformComponent(x, y));

    // Velocity component (movement speed)
    player.addComponent(new VelocityComponent(200));

    // Sprite component
    Rectangle playerShape = new Rectangle(30, 30);
    player.addComponent(new SpriteComponent(playerShape, Color.BLUE));

    // Collision component
    player.addComponent(new CollisionComponent(15, true, "player"));

    // Health component
    player.addComponent(new HealthComponent(100));

    // Player component
    player.addComponent(new PlayerComponent(0.25, 400)); // 0.25s cooldown, 400 projectile speed

    return player;
  }

  public Entity createEnemy(double x, double y) {
    Entity enemy = world.createEntity();

    double speedFactor = 0.5 + random.nextDouble() * 0.5;
    int damage = 10 + random.nextInt(10);
    int points = 50 + random.nextInt(50);

    enemy.addComponent(new TransformComponent(x, y));
    enemy.addComponent(new VelocityComponent(100));
    enemy.addComponent(new SpriteComponent(new Rectangle(20, 20), Color.RED));
    enemy.addComponent(new CollisionComponent(10, true, "enemy"));
    enemy.addComponent(new HealthComponent(20));
    enemy.addComponent(new EnemyComponent(speedFactor, damage, points));

    return enemy;
  }

  public Entity createEnemy(double x, double y, String type) {
    Entity enemy = world.createEntity();

    // Set properties based on type
    double speedFactor;
    int damage;
    int health;
    Color color;
    double size;

    switch (type) {
      case "basic":
        speedFactor = 0.8;
        damage = 10;
        health = 20;
        color = Color.RED;
        size = 20;
        break;
      case "speedy":
        speedFactor = 1.2;
        damage = 5;
        health = 15;
        color = Color.ORANGE;
        size = 15;
        break;
      case "tank":
        speedFactor = 0.6;
        damage = 15;
        health = 40;
        color = Color.DARKRED;
        size = 25;
        break;
      default:
        speedFactor = 0.8;
        damage = 10;
        health = 20;
        color = Color.RED;
        size = 20;
    }

    // Add components
    enemy.addComponent(new TransformComponent(x, y));
    enemy.addComponent(new VelocityComponent(100));
    enemy.addComponent(new SpriteComponent(new Rectangle(size, size), color));
    enemy.addComponent(new CollisionComponent(size / 2, true, "enemy"));
    enemy.addComponent(new HealthComponent(health));
    enemy.addComponent(new EnemyComponent(speedFactor, damage, 50));

    return enemy;
  }
}
