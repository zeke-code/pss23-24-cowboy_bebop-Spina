package github.zekecode.cowboybebop.components;

import github.zekecode.cowboybebop.core.Component;

public class PlayerComponent implements Component {
  private double shootCooldown;
  private double timeSinceLastShot;
  private double shootSpeed;
  private int score;

  public PlayerComponent(double shootCooldown, double shootSpeed) {
    this.shootCooldown = shootCooldown;
    this.timeSinceLastShot = shootCooldown;
    this.shootSpeed = shootSpeed;
    this.score = 0;
  }

  public double getShootCooldown() {
    return shootCooldown;
  }

  public void setShootCooldown(double shootCooldown) {
    this.shootCooldown = shootCooldown;
  }

  public double getTimeSinceLastShot() {
    return timeSinceLastShot;
  }

  public void setTimeSinceLastShot(double timeSinceLastShot) {
    this.timeSinceLastShot = timeSinceLastShot;
  }

  public boolean canShoot() {
    return timeSinceLastShot >= shootCooldown;
  }

  public void resetShootTimer() {
    timeSinceLastShot = 0;
  }

  public void updateTimer(double deltaTime) {
    timeSinceLastShot += deltaTime;
  }

  public double getShootSpeed() {
    return shootSpeed;
  }

  public void setShootSpeed(double shootSpeed) {
    this.shootSpeed = shootSpeed;
  }

  public int getScore() {
    return score;
  }

  public void addScore(int points) {
    this.score += points;
  }
}
