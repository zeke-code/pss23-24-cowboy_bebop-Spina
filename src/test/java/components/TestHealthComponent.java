package components;

import static org.junit.jupiter.api.Assertions.*;

import github.zekecode.cowboybebop.components.HealthComponent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class HealthComponentTest {

  private HealthComponent healthComponent;
  private final int DEFAULT_MAX_HEALTH = 100;

  @BeforeEach
  void setUp() {
    healthComponent = new HealthComponent(DEFAULT_MAX_HEALTH);
  }

  @Nested
  @DisplayName("Constructor Tests")
  class ConstructorTests {
    @Test
    @DisplayName("Should initialize with max health")
    void initializesWithMaxHealth() {
      assertEquals(
          DEFAULT_MAX_HEALTH,
          healthComponent.getMaxHealth(),
          "Max health should match constructor parameter");
    }

    @Test
    @DisplayName("Should initialize current health equal to max health")
    void initializesCurrentHealthToMax() {
      assertEquals(
          DEFAULT_MAX_HEALTH,
          healthComponent.getCurrentHealth(),
          "Current health should equal max health on initialization");
    }
  }

  @Nested
  @DisplayName("Health Modification Tests")
  class HealthModificationTests {
    @Test
    @DisplayName("Should reduce health when damaged")
    void damageReducesHealth() {
      healthComponent.damage(30);
      assertEquals(
          DEFAULT_MAX_HEALTH - 30,
          healthComponent.getCurrentHealth(),
          "Health should be reduced by damage amount");
    }

    @Test
    @DisplayName("Should not reduce health below zero")
    void damageDoesNotReduceBelowZero() {
      healthComponent.damage(DEFAULT_MAX_HEALTH + 50);
      assertEquals(
          0, healthComponent.getCurrentHealth(), "Health should not go below zero when damaged");
    }

    @Test
    @DisplayName("Should increase health when healed")
    void healIncreasesHealth() {
      // First damage to have room for healing
      healthComponent.damage(50);
      // Then heal
      healthComponent.heal(20);
      assertEquals(
          DEFAULT_MAX_HEALTH - 30,
          healthComponent.getCurrentHealth(),
          "Health should increase by heal amount");
    }

    @Test
    @DisplayName("Should not increase health above max")
    void healDoesNotIncreaseAboveMax() {
      healthComponent.heal(50);
      assertEquals(
          DEFAULT_MAX_HEALTH,
          healthComponent.getCurrentHealth(),
          "Health should not go above max when healed");
    }

    @Test
    @DisplayName("Should properly set current health with constraints")
    void setCurrentHealthRespectsLimits() {
      // Test setting below zero
      healthComponent.setCurrentHealth(-10);
      assertEquals(
          0, healthComponent.getCurrentHealth(), "Setting health below 0 should clamp to 0");

      // Test setting above max
      healthComponent.setCurrentHealth(DEFAULT_MAX_HEALTH + 10);
      assertEquals(
          DEFAULT_MAX_HEALTH,
          healthComponent.getCurrentHealth(),
          "Setting health above max should clamp to max");

      // Test setting within valid range
      healthComponent.setCurrentHealth(75);
      assertEquals(
          75,
          healthComponent.getCurrentHealth(),
          "Setting health within range should work properly");
    }
  }

  @Nested
  @DisplayName("Status Tests")
  class StatusTests {
    @Test
    @DisplayName("Should not be dead when health is above zero")
    void notDeadWhenHealthAboveZero() {
      healthComponent.setCurrentHealth(1);
      assertFalse(healthComponent.isDead(), "Entity should not be dead when health is above zero");
    }

    @Test
    @DisplayName("Should be dead when health is zero")
    void deadWhenHealthIsZero() {
      healthComponent.damage(DEFAULT_MAX_HEALTH);
      assertTrue(healthComponent.isDead(), "Entity should be dead when health is zero");
    }

    @Test
    @DisplayName("Should update max health")
    void updatesMaxHealth() {
      int newMax = 150;
      healthComponent.setMaxHealth(newMax);
      assertEquals(newMax, healthComponent.getMaxHealth(), "Max health should be updated");
    }
  }
}
