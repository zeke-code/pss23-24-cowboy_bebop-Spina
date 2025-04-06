package github.zekecode.cowboybebop.systems;

import github.zekecode.cowboybebop.components.SpriteComponent;
import github.zekecode.cowboybebop.components.TransformComponent;
import github.zekecode.cowboybebop.core.Entity;
import github.zekecode.cowboybebop.core.System;
import github.zekecode.cowboybebop.core.World;
import javafx.scene.canvas.GraphicsContext;

public class RenderSystem implements System {
  private final World world;
  private final GraphicsContext gc;

  public RenderSystem(World world, GraphicsContext gc) {
    this.world = world;
    this.gc = gc;
  }

  @Override
  public void update(double deltaTime) {
    // Clear the canvas
    gc.clearRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());

    // Render all entities with both Transform and Sprite components
    for (Entity entity : world.getEntities()) {
      if (entity.hasComponent(TransformComponent.class)
          && entity.hasComponent(SpriteComponent.class)) {

        TransformComponent transform = entity.getComponent(TransformComponent.class);
        SpriteComponent sprite = entity.getComponent(SpriteComponent.class);

        // Save the current state
        gc.save();

        // Apply transformations
        gc.translate(transform.getX(), transform.getY());
        gc.rotate(Math.toDegrees(transform.getRotation()));

        // Set the color
        gc.setFill(sprite.getColor());
        gc.setStroke(sprite.getColor());

        // Draw the shape
        if (sprite.getShape() != null) {
          // Get the bounds of the shape
          double width = sprite.getWidth();
          double height = sprite.getHeight();

          // Draw a simple rectangle (in a real game, you'd draw the actual shape or image)
          gc.fillRect(-width / 2, -height / 2, width, height);
        }

        // Restore the state
        gc.restore();
      }
    }
  }
}
