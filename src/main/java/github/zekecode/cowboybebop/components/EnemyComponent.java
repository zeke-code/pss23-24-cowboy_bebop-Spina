package github.zekecode.cowboybebop.components;

import github.zekecode.cowboybebop.core.Component;

public class EnemyComponent implements Component {
  private double speedFactor;
  private int damage;
  private int points;

  public EnemyComponent(double speedFactor, int damage, int points) {
    this.speedFactor = speedFactor;
    this.damage = damage;
    this.points = points;
  }

  public double getSpeedFactor() {
    return speedFactor;
  }

  public void setSpeedFactor(double speedFactor) {
    this.speedFactor = speedFactor;
  }

  public int getDamage() {
    return damage;
  }

  public void setDamage(int damage) {
    this.damage = damage;
  }

  public int getPoints() {
    return points;
  }

  public void setPoints(int points) {
    this.points = points;
  }
}
