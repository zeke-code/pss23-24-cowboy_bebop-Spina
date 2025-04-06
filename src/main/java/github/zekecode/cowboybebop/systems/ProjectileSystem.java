package github.zekecode.cowboybebop.systems;

import github.zekecode.cowboybebop.components.ProjectileComponent;
import github.zekecode.cowboybebop.core.Entity;
import github.zekecode.cowboybebop.core.System;
import github.zekecode.cowboybebop.core.World;
import java.util.HashSet;
import java.util.Set;

public class ProjectileSystem implements System {
  private final World world;

  public ProjectileSystem(World world) {
    this.world = world;
  }

  @Override
  public void update(double deltaTime) {
    Set<Entity> toRemove = new HashSet<>();

    for (Entity entity : world.getEntitiesWithComponent(ProjectileComponent.class)) {
      ProjectileComponent projectile = entity.getComponent(ProjectileComponent.class);

      // Update lifetime
      projectile.addTimeAlive(deltaTime);

      // Check if projectile should despawn
      if (projectile.shouldDespawn()) {
        toRemove.add(entity);
      }
    }

    // Remove expired projectiles
    for (Entity entity : toRemove) {
      world.removeEntity(entity);
    }
  }
}
