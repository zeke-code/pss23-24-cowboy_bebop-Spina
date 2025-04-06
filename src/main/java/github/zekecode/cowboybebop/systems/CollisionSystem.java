package github.zekecode.cowboybebop.systems;

import github.zekecode.cowboybebop.components.*;
import github.zekecode.cowboybebop.core.Entity;
import github.zekecode.cowboybebop.core.System;
import github.zekecode.cowboybebop.core.World;
import java.util.HashSet;
import java.util.Set;

public class CollisionSystem implements System {
  private final World world;

  public CollisionSystem(World world) {
    this.world = world;
  }

  @Override
  public void update(double deltaTime) {
    Set<Entity> collidables = world.getEntitiesWithComponent(CollisionComponent.class);
    Set<Entity> toRemove = new HashSet<>();

    for (Entity entity1 : collidables) {
      if (toRemove.contains(entity1)) continue;

      TransformComponent transform1 = entity1.getComponent(TransformComponent.class);
      CollisionComponent collision1 = entity1.getComponent(CollisionComponent.class);

      if (transform1 == null) continue;

      for (Entity entity2 : collidables) {
        if (entity1 == entity2 || toRemove.contains(entity2)) continue;

        TransformComponent transform2 = entity2.getComponent(TransformComponent.class);
        CollisionComponent collision2 = entity2.getComponent(CollisionComponent.class);

        if (transform2 == null) continue;

        // Simple circle collision check
        double dx = transform1.getX() - transform2.getX();
        double dy = transform1.getY() - transform2.getY();
        double distance = Math.sqrt(dx * dx + dy * dy);
        double minDistance = collision1.getRadius() + collision2.getRadius();

        if (distance < minDistance) {
          // Collision detected
          handleCollision(entity1, entity2, toRemove);
        }
      }
    }

    // Remove entities marked for removal
    for (Entity entity : toRemove) {
      world.removeEntity(entity);
    }
  }

  private void handleCollision(Entity entity1, Entity entity2, Set<Entity> toRemove) {
    // Check for projectile-enemy collision
    if (entity1.hasComponent(ProjectileComponent.class)
        && entity2.hasComponent(EnemyComponent.class)) {
      ProjectileComponent projectile = entity1.getComponent(ProjectileComponent.class);

      // Only player projectiles hit enemies
      if ("player".equals(projectile.getOwner())) {
        damageEntity(entity2, projectile.getDamage());
        toRemove.add(entity1); // Remove the projectile
      }
    } else if (entity2.hasComponent(ProjectileComponent.class)
        && entity1.hasComponent(EnemyComponent.class)) {
      ProjectileComponent projectile = entity2.getComponent(ProjectileComponent.class);

      if ("player".equals(projectile.getOwner())) {
        damageEntity(entity1, projectile.getDamage());
        toRemove.add(entity2);
      }
    }

    // Check for player-enemy collision
    if (entity1.hasComponent(PlayerComponent.class) && entity2.hasComponent(EnemyComponent.class)) {
      EnemyComponent enemy = entity2.getComponent(EnemyComponent.class);
      damageEntity(entity1, enemy.getDamage());
      toRemove.add(entity2); // Enemy is destroyed on impact
    } else if (entity2.hasComponent(PlayerComponent.class)
        && entity1.hasComponent(EnemyComponent.class)) {
      EnemyComponent enemy = entity1.getComponent(EnemyComponent.class);
      damageEntity(entity2, enemy.getDamage());
      toRemove.add(entity1);
    }
  }

  private void damageEntity(Entity entity, int damage) {
    if (entity.hasComponent(HealthComponent.class)) {
      HealthComponent health = entity.getComponent(HealthComponent.class);
      health.damage(damage);

      if (health.isDead()) {
        // If entity is enemy, and it died, award points to player
        if (entity.hasComponent(EnemyComponent.class)) {
          EnemyComponent enemy = entity.getComponent(EnemyComponent.class);

          // Find player to award points
          for (Entity playerEntity : world.getEntitiesWithComponent(PlayerComponent.class)) {
            PlayerComponent player = playerEntity.getComponent(PlayerComponent.class);
            player.addScore(enemy.getPoints());
            break;
          }
        }

        world.removeEntity(entity);
      }
    }
  }
}
