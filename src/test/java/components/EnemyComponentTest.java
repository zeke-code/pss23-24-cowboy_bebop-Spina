package components;

import static org.junit.jupiter.api.Assertions.*;

import github.zekecode.cowboybebop.components.EnemyComponent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class EnemyComponentTest {

  private static final double INITIAL_SPEED_FACTOR = 1.5;
  private static final int INITIAL_DAMAGE = 10;
  private static final int INITIAL_POINTS = 100;

  private EnemyComponent enemy;

  @BeforeEach
  void setUp() {
    enemy = new EnemyComponent(INITIAL_SPEED_FACTOR, INITIAL_DAMAGE, INITIAL_POINTS);
  }

  @Test
  @DisplayName("Constructor should properly initialize values")
  void constructorInitializesCorrectly() {
    assertEquals(
        INITIAL_SPEED_FACTOR,
        enemy.getSpeedFactor(),
        "Speed factor should match constructor value");
    assertEquals(INITIAL_DAMAGE, enemy.getDamage(), "Damage should match constructor value");
    assertEquals(INITIAL_POINTS, enemy.getPoints(), "Points should match constructor value");
  }

  @Test
  @DisplayName("Should update speed factor correctly")
  void updatesSpeedFactor() {
    double newSpeed = 2.0;
    enemy.setSpeedFactor(newSpeed);
    assertEquals(newSpeed, enemy.getSpeedFactor(), "Speed factor should update when set");
  }

  @Test
  @DisplayName("Should update damage correctly")
  void updatesDamage() {
    int newDamage = 20;
    enemy.setDamage(newDamage);
    assertEquals(newDamage, enemy.getDamage(), "Damage should update when set");
  }

  @Test
  @DisplayName("Should update points correctly")
  void updatesPoints() {
    int newPoints = 200;
    enemy.setPoints(newPoints);
    assertEquals(newPoints, enemy.getPoints(), "Points should update when set");
  }

  @Test
  @DisplayName("Should handle zero values")
  void handlesZeroValues() {
    EnemyComponent zeroEnemy = new EnemyComponent(0.0, 0, 0);
    assertEquals(0.0, zeroEnemy.getSpeedFactor(), "Should handle zero speed factor");
    assertEquals(0, zeroEnemy.getDamage(), "Should handle zero damage");
    assertEquals(0, zeroEnemy.getPoints(), "Should handle zero points");
  }

  @Test
  @DisplayName("Should handle negative speed factor")
  void handlesNegativeSpeedFactor() {
    double negativeSpeed = -0.5;
    enemy.setSpeedFactor(negativeSpeed);
    assertEquals(
        negativeSpeed,
        enemy.getSpeedFactor(),
        "Should allow negative speed factor (for reverse movement)");
  }
}
