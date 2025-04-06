package github.zekecode.cowboybebop.ui;

import github.zekecode.cowboybebop.components.HealthComponent;
import github.zekecode.cowboybebop.components.PlayerComponent;
import github.zekecode.cowboybebop.core.Entity;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class HUD {
  private final GraphicsContext gc;
  private final double screenWidth;
  private final double screenHeight;

  public HUD(GraphicsContext gc, double screenWidth, double screenHeight) {
    this.gc = gc;
    this.screenWidth = screenWidth;
    this.screenHeight = screenHeight;
  }

  public void render(Entity player) {
    if (player == null) return;

    // Draw health bar
    if (player.hasComponent(HealthComponent.class)) {
      HealthComponent health = player.getComponent(HealthComponent.class);

      double barWidth = 200;
      double barHeight = 20;
      double barX = 20;
      double barY = 20;

      // Background
      gc.setFill(Color.DARKGRAY);
      gc.fillRect(barX, barY, barWidth, barHeight);

      // Health amount
      double healthPercentage = (double) health.getCurrentHealth() / health.getMaxHealth();
      gc.setFill(
          healthPercentage > 0.5
              ? Color.GREEN
              : healthPercentage > 0.25 ? Color.ORANGE : Color.RED);
      gc.fillRect(barX, barY, barWidth * healthPercentage, barHeight);

      // Border
      gc.setStroke(Color.BLACK);
      gc.strokeRect(barX, barY, barWidth, barHeight);

      // Text
      gc.setFill(Color.WHITE);
      gc.setFont(new Font(14));
      gc.fillText(
          "Health: " + health.getCurrentHealth() + "/" + health.getMaxHealth(),
          barX + 10,
          barY + 15);
    }

    // Draw score
    if (player.hasComponent(PlayerComponent.class)) {
      PlayerComponent playerComponent = player.getComponent(PlayerComponent.class);

      gc.setFill(Color.WHITE);
      gc.setFont(new Font(18));
      gc.fillText("Score: " + playerComponent.getScore(), 20, 60);
    }
  }

  public void renderGameOver(int finalScore) {
    gc.setFill(new Color(0, 0, 0, 0.7));
    gc.fillRect(0, 0, screenWidth, screenHeight);

    gc.setFill(Color.RED);
    gc.setFont(new Font(48));
    gc.fillText("GAME OVER", screenWidth / 2 - 140, screenHeight / 2 - 24);

    gc.setFill(Color.WHITE);
    gc.setFont(new Font(24));
    gc.fillText("Final Score: " + finalScore, screenWidth / 2 - 80, screenHeight / 2 + 20);

    gc.setFont(new Font(18));
    gc.fillText("Press SPACE to restart", screenWidth / 2 - 90, screenHeight / 2 + 60);
  }
}
