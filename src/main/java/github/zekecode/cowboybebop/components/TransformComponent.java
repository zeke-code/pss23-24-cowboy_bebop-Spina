package github.zekecode.cowboybebop.components;

import github.zekecode.cowboybebop.core.Component;

public class TransformComponent implements Component {
  private double x;
  private double y;
  private double rotation; // in radians

  public TransformComponent(double x, double y) {
    this.x = x;
    this.y = y;
    this.rotation = 0;
  }

  public TransformComponent(double x, double y, double rotation) {
    this.x = x;
    this.y = y;
    this.rotation = rotation;
  }

  public double getX() {
    return x;
  }

  public void setX(double x) {
    this.x = x;
  }

  public double getY() {
    return y;
  }

  public void setY(double y) {
    this.y = y;
  }

  public double getRotation() {
    return rotation;
  }

  public void setRotation(double rotation) {
    this.rotation = rotation;
  }
}
