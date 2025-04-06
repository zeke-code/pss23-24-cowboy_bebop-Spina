package github.zekecode.cowboybebop.systems;

import github.zekecode.cowboybebop.components.TransformComponent;
import github.zekecode.cowboybebop.components.VelocityComponent;
import github.zekecode.cowboybebop.core.Entity;
import github.zekecode.cowboybebop.core.System;
import github.zekecode.cowboybebop.core.World;

public class MovementSystem implements System {
  private final World world;
  private final double screenWidth;
  private final double screenHeight;

  public MovementSystem(World world, double screenWidth, double screenHeight) {
    this.world = world;
    this.screenWidth = screenWidth;
    this.screenHeight = screenHeight;
  }

  @Override
  public void update(double deltaTime) {
    for (Entity entity : world.getEntities()) {
      if (entity.hasComponent(TransformComponent.class)
          && entity.hasComponent(VelocityComponent.class)) {

        TransformComponent transform = entity.getComponent(TransformComponent.class);
        VelocityComponent velocity = entity.getComponent(VelocityComponent.class);

        // Normalize velocity if needed
        velocity.normalize();

        // Update position based on velocity
        double newX = transform.getX() + velocity.getVx() * deltaTime;
        double newY = transform.getY() + velocity.getVy() * deltaTime;

        // Keep entities within screen bounds
        newX = Math.max(0, Math.min(screenWidth, newX));
        newY = Math.max(0, Math.min(screenHeight, newY));

        transform.setX(newX);
        transform.setY(newY);

        // Update rotation based on movement direction (if moving)
        if (velocity.getVx() != 0 || velocity.getVy() != 0) {
          transform.setRotation(Math.atan2(velocity.getVy(), velocity.getVx()));
        }
      }
    }
  }
}
