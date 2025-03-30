package github.zekecode.cowboybebop.components;

import github.zekecode.cowboybebop.core.Component;

public class VelocityComponent implements Component {
  private double vx;
  private double vy;
  private double maxSpeed;

  public VelocityComponent(double maxSpeed) {
    this.vx = 0;
    this.vy = 0;
    this.maxSpeed = maxSpeed;
  }

  public double getVx() {
    return vx;
  }

  public void setVx(double vx) {
    this.vx = vx;
  }

  public double getVy() {
    return vy;
  }

  public void setVy(double vy) {
    this.vy = vy;
  }

  public double getMaxSpeed() {
    return maxSpeed;
  }

  public void setMaxSpeed(double maxSpeed) {
    this.maxSpeed = maxSpeed;
  }

  public void normalize() {
    double magnitude = Math.sqrt(vx * vx + vy * vy);
    if (magnitude > maxSpeed) {
      vx = (vx / magnitude) * maxSpeed;
      vy = (vy / magnitude) * maxSpeed;
    }
  }
}
