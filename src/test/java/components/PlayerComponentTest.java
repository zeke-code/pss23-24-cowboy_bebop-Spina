package components;

import static org.junit.jupiter.api.Assertions.*;

import github.zekecode.cowboybebop.components.PlayerComponent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class PlayerComponentTest {

  private static final double COOLDOWN = 0.5;
  private static final double SHOOT_SPEED = 400.0;
  private PlayerComponent player;

  @BeforeEach
  void setUp() {
    player = new PlayerComponent(COOLDOWN, SHOOT_SPEED);
  }

  @Test
  @DisplayName("Constructor should initialize values correctly")
  void constructorInitializesCorrectly() {
    assertEquals(COOLDOWN, player.getShootCooldown(), "Cooldown should match constructor value");
    assertEquals(SHOOT_SPEED, player.getShootSpeed(), "Shoot speed should match constructor value");
    assertEquals(
        COOLDOWN,
        player.getTimeSinceLastShot(),
        "Time since last shot should initially equal cooldown");
    assertEquals(0, player.getScore(), "Score should initially be zero");
  }

  @Nested
  @DisplayName("Shooting mechanics")
  class ShootingTests {

    @Test
    @DisplayName("Should be able to shoot initially")
    void canShootInitially() {
      assertTrue(player.canShoot(), "Should be able to shoot on initialization");
    }

    @Test
    @DisplayName("Should not be able to shoot after reset")
    void cannotShootAfterReset() {
      player.resetShootTimer();
      assertFalse(player.canShoot(), "Should not be able to shoot right after reset");
    }

    @Test
    @DisplayName("Should be able to shoot after cooldown period")
    void canShootAfterCooldown() {
      player.resetShootTimer(); // Reset to 0
      player.updateTimer(COOLDOWN); // Add exactly cooldown time
      assertTrue(player.canShoot(), "Should be able to shoot after cooldown period");
    }

    @Test
    @DisplayName("Should update timer correctly")
    void updatesTimer() {
      player.resetShootTimer(); // Start at 0
      double updateTime = 0.3;
      player.updateTimer(updateTime);
      assertEquals(
          updateTime,
          player.getTimeSinceLastShot(),
          "Time since last shot should increase by update amount");
    }
  }

  @Nested
  @DisplayName("Score management")
  class ScoreTests {

    @Test
    @DisplayName("Should add points to score")
    void addsPointsToScore() {
      int points = 100;
      player.addScore(points);
      assertEquals(points, player.getScore(), "Score should increase by points added");

      // Add more points
      player.addScore(150);
      assertEquals(points + 150, player.getScore(), "Score should accumulate points added");
    }
  }

  @Nested
  @DisplayName("Property updates")
  class PropertyTests {

    @Test
    @DisplayName("Should update cooldown")
    void updatesCooldown() {
      double newCooldown = 0.3;
      player.setShootCooldown(newCooldown);
      assertEquals(newCooldown, player.getShootCooldown(), "Cooldown should update when set");
    }

    @Test
    @DisplayName("Should update shoot speed")
    void updatesShootSpeed() {
      double newSpeed = 500.0;
      player.setShootSpeed(newSpeed);
      assertEquals(newSpeed, player.getShootSpeed(), "Shoot speed should update when set");
    }

    @Test
    @DisplayName("Should update timer directly")
    void updatesTimerDirectly() {
      double newTime = 0.2;
      player.setTimeSinceLastShot(newTime);
      assertEquals(
          newTime, player.getTimeSinceLastShot(), "Time since last shot should update when set");
    }
  }

  @Test
  @DisplayName("Should handle edge cases with small values")
  void handlesSmallValues() {
    PlayerComponent twitchPlayer = new PlayerComponent(0.01, 1.0);
    assertTrue(twitchPlayer.canShoot(), "Should handle very small cooldown values");

    twitchPlayer.resetShootTimer();
    assertFalse(twitchPlayer.canShoot(), "Timer reset should work with small values");

    twitchPlayer.updateTimer(0.005); // Half cooldown
    assertFalse(twitchPlayer.canShoot(), "Should respect small cooldown increments");

    twitchPlayer.updateTimer(0.005); // Now at full cooldown
    assertTrue(twitchPlayer.canShoot(), "Should detect small cooldown completion");
  }
}
