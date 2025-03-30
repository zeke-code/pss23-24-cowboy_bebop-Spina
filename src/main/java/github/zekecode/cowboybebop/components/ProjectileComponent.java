package github.zekecode.cowboybebop.components;

import github.zekecode.cowboybebop.core.Component;

public class ProjectileComponent implements Component {
  private int damage;
  private double lifetime;
  private double timeAlive;
  private String owner;

  public ProjectileComponent(int damage, double lifetime, String owner) {
    this.damage = damage;
    this.lifetime = lifetime;
    this.timeAlive = 0;
    this.owner = owner;
  }

  public int getDamage() {
    return damage;
  }

  public void setDamage(int damage) {
    this.damage = damage;
  }

  public double getLifetime() {
    return lifetime;
  }

  public void setLifetime(double lifetime) {
    this.lifetime = lifetime;
  }

  public double getTimeAlive() {
    return timeAlive;
  }

  public void addTimeAlive(double time) {
    timeAlive += time;
  }

  public boolean shouldDespawn() {
    return timeAlive >= lifetime;
  }

  public String getOwner() {
    return owner;
  }

  public void setOwner(String owner) {
    this.owner = owner;
  }
}
