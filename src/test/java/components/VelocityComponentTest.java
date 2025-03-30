package components;

import static org.junit.jupiter.api.Assertions.*;

import github.zekecode.cowboybebop.components.VelocityComponent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class VelocityComponentTest {

  private static final double MAX_SPEED = 100.0;
  private static final double DELTA = 0.001; // Tolerance for double comparisons
  private VelocityComponent velocity;

  @BeforeEach
  void setUp() {
    velocity = new VelocityComponent(MAX_SPEED);
  }

  @Test
  @DisplayName("Constructor should initialize with zero velocity and specified max speed")
  void constructorInitializesCorrectly() {
    assertEquals(0, velocity.getVx(), "Initial vx should be zero");
    assertEquals(0, velocity.getVy(), "Initial vy should be zero");
    assertEquals(MAX_SPEED, velocity.getMaxSpeed(), "Max speed should match constructor value");
  }

  @Test
  @DisplayName("Velocity getters and setters work correctly")
  void velocityGettersAndSetters() {
    double newVx = 50.0;
    double newVy = -30.0;

    velocity.setVx(newVx);
    velocity.setVy(newVy);

    assertEquals(newVx, velocity.getVx(), "Vx should match the set value");
    assertEquals(newVy, velocity.getVy(), "Vy should match the set value");

    double newMaxSpeed = 200.0;
    velocity.setMaxSpeed(newMaxSpeed);
    assertEquals(newMaxSpeed, velocity.getMaxSpeed(), "Max speed should match the set value");
  }

  @Test
  @DisplayName("Velocity below max speed should not be normalized")
  void belowMaxSpeedNotNormalized() {
    double vx = 30.0;
    double vy = 40.0;
    velocity.setVx(vx);
    velocity.setVy(vy);

    velocity.normalize();

    assertEquals(vx, velocity.getVx(), DELTA, "Vx should not change when below max speed");
    assertEquals(vy, velocity.getVy(), DELTA, "Vy should not change when below max speed");
  }

  @Test
  @DisplayName("Velocity above max speed should be normalized")
  void aboveMaxSpeedIsNormalized() {
    double vx = 150.0;
    double vy = 200.0;
    velocity.setVx(vx);
    velocity.setVy(vy);

    double magnitude = Math.sqrt(vx * vx + vy * vy);
    double expectedVx = (vx / magnitude) * MAX_SPEED;
    double expectedVy = (vy / magnitude) * MAX_SPEED;

    velocity.normalize();

    assertEquals(
        expectedVx, velocity.getVx(), DELTA, "Vx should be normalized when above max speed");
    assertEquals(
        expectedVy, velocity.getVy(), DELTA, "Vy should be normalized when above max speed");

    double newMagnitude =
        Math.sqrt(velocity.getVx() * velocity.getVx() + velocity.getVy() * velocity.getVy());
    assertEquals(
        MAX_SPEED, newMagnitude, DELTA, "Magnitude should equal max speed after normalization");
  }

  @Test
  @DisplayName("Direction should be preserved after normalization")
  void directionPreservedAfterNormalization() {
    double vx = 300.0;
    double vy = 400.0;
    velocity.setVx(vx);
    velocity.setVy(vy);

    double angleBefore = Math.atan2(vy, vx);

    velocity.normalize();

    double angleAfter = Math.atan2(velocity.getVy(), velocity.getVx());

    assertEquals(
        angleBefore, angleAfter, DELTA, "Direction should be preserved after normalization");
  }

  @Test
  @DisplayName("Zero velocity should remain zero after normalization")
  void zeroVelocityRemainsZero() {
    velocity.setVx(0);
    velocity.setVy(0);

    velocity.normalize();

    assertEquals(0, velocity.getVx(), "Vx should remain zero after normalizing zero velocity");
    assertEquals(0, velocity.getVy(), "Vy should remain zero after normalizing zero velocity");
  }

  @Test
  @DisplayName("Velocity at exact max speed should remain unchanged")
  void exactMaxSpeedUnchanged() {
    // Set velocity with magnitude exactly equal to max speed
    velocity.setVx(0);
    velocity.setVy(MAX_SPEED);

    velocity.normalize();

    assertEquals(0, velocity.getVx(), DELTA, "Vx should be unchanged when at exact max speed");
    assertEquals(
        MAX_SPEED, velocity.getVy(), DELTA, "Vy should be unchanged when at exact max speed");
  }

  @Test
  @DisplayName("Velocity with negative components should normalize correctly")
  void negativeComponentsNormalizeCorrectly() {
    velocity.setVx(-120);
    velocity.setVy(160);

    velocity.normalize();

    double magnitude =
        Math.sqrt(velocity.getVx() * velocity.getVx() + velocity.getVy() * velocity.getVy());
    assertEquals(MAX_SPEED, magnitude, DELTA, "Magnitude should be normalized to max speed");

    // The ratio of components should be preserved
    double originalRatio = -120.0 / 160.0;
    double newRatio = velocity.getVx() / velocity.getVy();
    assertEquals(originalRatio, newRatio, DELTA, "Ratio of components should be preserved");
  }
}
