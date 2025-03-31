package github.zekecode.cowboybebop.components;

import github.zekecode.cowboybebop.core.Component;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;

public class SpriteComponent implements Component {
  private Shape shape;
  private Color color;
  private double width;
  private double height;

  public SpriteComponent(Shape shape, Color color) {
    this.shape = shape;
    this.color = color;

    if (shape != null) {
      this.width = shape.getBoundsInLocal().getWidth();
      this.height = shape.getBoundsInLocal().getHeight();
    }
  }

  public Shape getShape() {
    return shape;
  }

  public void setShape(Shape shape) {
    this.shape = shape;
    if (shape != null) {
      this.width = shape.getBoundsInLocal().getWidth();
      this.height = shape.getBoundsInLocal().getHeight();
    }
  }

  public Color getColor() {
    return color;
  }

  public void setColor(Color color) {
    this.color = color;
  }

  public double getWidth() {
    return width;
  }

  public double getHeight() {
    return height;
  }
}
