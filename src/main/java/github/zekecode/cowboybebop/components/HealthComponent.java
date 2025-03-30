package github.zekecode.cowboybebop.components;

import github.zekecode.cowboybebop.core.Component;

public class HealthComponent implements Component {
  private int maxHealth;
  private int currentHealth;

  public HealthComponent(int maxHealth) {
    this.maxHealth = maxHealth;
    this.currentHealth = maxHealth;
  }

  public int getMaxHealth() {
    return maxHealth;
  }

  public void setMaxHealth(int maxHealth) {
    this.maxHealth = maxHealth;
  }

  public int getCurrentHealth() {
    return currentHealth;
  }

  public void setCurrentHealth(int currentHealth) {
    this.currentHealth = Math.min(maxHealth, Math.max(0, currentHealth));
  }

  public void damage(int amount) {
    currentHealth = Math.max(0, currentHealth - amount);
  }

  public void heal(int amount) {
    currentHealth = Math.min(maxHealth, currentHealth + amount);
  }

  public boolean isDead() {
    return currentHealth <= 0;
  }
}
