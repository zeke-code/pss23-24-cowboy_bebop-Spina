package github.zekecode.cowboybebop.util;

import java.util.HashSet;
import java.util.Set;
import javafx.scene.Scene;
import javafx.scene.input.MouseButton;

public class InputManager {
  private final Set<String> pressedKeys = new HashSet<>();
  private boolean mousePressed = false;
  private double mouseX = 0;
  private double mouseY = 0;

  public InputManager(Scene scene) {
    // Key press handlers
    scene.setOnKeyPressed(
        e -> {
          pressedKeys.add(e.getCode().toString());
        });

    scene.setOnKeyReleased(
        e -> {
          pressedKeys.remove(e.getCode().toString());
        });

    // Mouse press handlers
    scene.setOnMousePressed(
        e -> {
          if (e.getButton() == MouseButton.PRIMARY) {
            mousePressed = true;
          }
        });

    scene.setOnMouseReleased(
        e -> {
          if (e.getButton() == MouseButton.PRIMARY) {
            mousePressed = false;
          }
        });

    // Mouse move handler
    scene.setOnMouseMoved(
        e -> {
          mouseX = e.getX();
          mouseY = e.getY();
        });

    scene.setOnMouseDragged(
        e -> {
          mouseX = e.getX();
          mouseY = e.getY();
        });
  }

  public boolean isKeyPressed(String keyCode) {
    return pressedKeys.contains(keyCode);
  }

  public boolean isMousePressed() {
    return mousePressed;
  }

  public double getMouseX() {
    return mouseX;
  }

  public double getMouseY() {
    return mouseY;
  }
}
