package components;

import static org.junit.jupiter.api.Assertions.*;

import github.zekecode.cowboybebop.components.ProjectileComponent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ProjectileComponentTest {

  private static final int TEST_DAMAGE = 25;
  private static final double TEST_LIFETIME = 3.0;
  private static final String TEST_OWNER = "player";

  private ProjectileComponent projectile;

  @BeforeEach
  void setUp() {
    projectile = new ProjectileComponent(TEST_DAMAGE, TEST_LIFETIME, TEST_OWNER);
  }

  @Test
  @DisplayName("Constructor should initialize fields correctly")
  void constructorInitializesCorrectly() {
    assertEquals(TEST_DAMAGE, projectile.getDamage(), "Damage should match constructor parameter");
    assertEquals(
        TEST_LIFETIME, projectile.getLifetime(), "Lifetime should match constructor parameter");
    assertEquals(TEST_OWNER, projectile.getOwner(), "Owner should match constructor parameter");
    assertEquals(0.0, projectile.getTimeAlive(), "TimeAlive should be initialized to zero");
  }

  @Test
  @DisplayName("Damage getter and setter should work correctly")
  void damageGetterAndSetter() {
    int newDamage = 50;
    projectile.setDamage(newDamage);
    assertEquals(newDamage, projectile.getDamage(), "Damage should update when set");
  }

  @Test
  @DisplayName("Lifetime getter and setter should work correctly")
  void lifetimeGetterAndSetter() {
    double newLifetime = 5.0;
    projectile.setLifetime(newLifetime);
    assertEquals(newLifetime, projectile.getLifetime(), "Lifetime should update when set");
  }

  @Test
  @DisplayName("Owner getter and setter should work correctly")
  void ownerGetterAndSetter() {
    String newOwner = "enemy";
    projectile.setOwner(newOwner);
    assertEquals(newOwner, projectile.getOwner(), "Owner should update when set");
  }

  @Test
  @DisplayName("addTimeAlive should increment timeAlive correctly")
  void addTimeAliveIncrementsCorrectly() {
    double increment1 = 1.0;
    projectile.addTimeAlive(increment1);
    assertEquals(
        increment1, projectile.getTimeAlive(), "TimeAlive should increase by the amount added");

    double increment2 = 0.5;
    projectile.addTimeAlive(increment2);
    assertEquals(
        increment1 + increment2,
        projectile.getTimeAlive(),
        "TimeAlive should accumulate multiple adds");
  }

  @Test
  @DisplayName("shouldDespawn should return false when timeAlive is less than lifetime")
  void shouldDespawnReturnsFalseWhenTimeAliveIsLessThanLifetime() {
    projectile.addTimeAlive(TEST_LIFETIME - 0.1);
    assertFalse(
        projectile.shouldDespawn(), "Projectile should not despawn when timeAlive < lifetime");
  }

  @Test
  @DisplayName("shouldDespawn should return true when timeAlive equals lifetime")
  void shouldDespawnReturnsTrueWhenTimeAliveEqualsLifetime() {
    projectile.addTimeAlive(TEST_LIFETIME);
    assertTrue(projectile.shouldDespawn(), "Projectile should despawn when timeAlive = lifetime");
  }

  @Test
  @DisplayName("shouldDespawn should return true when timeAlive exceeds lifetime")
  void shouldDespawnReturnsTrueWhenTimeAliveExceedsLifetime() {
    projectile.addTimeAlive(TEST_LIFETIME + 0.1);
    assertTrue(projectile.shouldDespawn(), "Projectile should despawn when timeAlive > lifetime");
  }

  @Test
  @DisplayName("Should handle edge case with zero lifetime")
  void handlesZeroLifetime() {
    ProjectileComponent zeroLifetimeProjectile =
        new ProjectileComponent(TEST_DAMAGE, 0.0, TEST_OWNER);

    // Should despawn immediately with zero lifetime
    assertTrue(
        zeroLifetimeProjectile.shouldDespawn(),
        "Projectile with zero lifetime should despawn immediately");
  }

  @Test
  @DisplayName("Should handle negative time additions")
  void handlesNegativeTimeAdditions() {
    projectile.addTimeAlive(1.0);
    double initialTimeAlive = projectile.getTimeAlive();

    // Add negative time - the current implementation allows this
    projectile.addTimeAlive(-0.5);

    assertEquals(
        initialTimeAlive - 0.5,
        projectile.getTimeAlive(),
        "Negative time additions should decrease timeAlive");
  }

  @Test
  @DisplayName("Should handle null owner")
  void handlesNullOwner() {
    ProjectileComponent nullOwnerProjectile =
        new ProjectileComponent(TEST_DAMAGE, TEST_LIFETIME, null);

    assertNull(nullOwnerProjectile.getOwner(), "Constructor should accept null owner");
  }
}
