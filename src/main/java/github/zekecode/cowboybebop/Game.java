package github.zekecode.cowboybebop;

import github.zekecode.cowboybebop.components.HealthComponent;
import github.zekecode.cowboybebop.components.PlayerComponent;
import github.zekecode.cowboybebop.core.Entity;
import github.zekecode.cowboybebop.core.World;
import github.zekecode.cowboybebop.factory.EntityFactory;
import github.zekecode.cowboybebop.systems.*;
import github.zekecode.cowboybebop.ui.HUD;
import github.zekecode.cowboybebop.util.InputManager;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Game extends Application {
  private static final double WIDTH = 800;
  private static final double HEIGHT = 600;

  private World world;
  private InputManager inputManager;
  private EntityFactory entityFactory;
  private HUD hud;
  private Canvas canvas;

  private Entity player;
  private boolean gameOver = false;

  @Override
  public void start(Stage primaryStage) {
    // Create the canvas and get the graphics context
    canvas = new Canvas(WIDTH, HEIGHT);
    GraphicsContext gc = canvas.getGraphicsContext2D();

    StackPane root = new StackPane(canvas);
    Scene scene = new Scene(root, WIDTH, HEIGHT);

    inputManager = new InputManager(scene);

    world = new World();

    entityFactory = new EntityFactory(world);

    // Create HUD
    hud = new HUD(gc, WIDTH, HEIGHT);

    initGame();

    // Game loop
    AnimationTimer gameLoop =
        new AnimationTimer() {
          private long lastUpdate = 0;

          @Override
          public void handle(long now) {
            // Calculate delta time
            if (lastUpdate == 0) {
              lastUpdate = now;
              return;
            }

            double deltaTime = (now - lastUpdate) / 1_000_000_000.0; // Convert to seconds
            lastUpdate = now;

            // Clear the screen
            gc.setFill(Color.BLACK);
            gc.fillRect(0, 0, WIDTH, HEIGHT);

            if (!gameOver) {
              // Update world
              world.update(deltaTime);

              // Check for game over
              checkGameOver();

              // Render HUD
              hud.render(player);
            } else {
              // Get final score
              int finalScore = 0;
              if (player != null && player.hasComponent(PlayerComponent.class)) {
                finalScore = player.getComponent(PlayerComponent.class).getScore();
              }

              // Render game over screen
              hud.renderGameOver(finalScore);

              // Check for restart
              if (inputManager.isKeyPressed("SPACE")) {
                initGame();
              }
            }
          }
        };

    gameLoop.start();

    // Setup window
    primaryStage.setTitle("Cowboy Bebop");
    primaryStage.setResizable(false);
    primaryStage.setScene(scene);
    primaryStage.show();
  }

  private void initGame() {
    // Reset game state
    world = new World();
    gameOver = false;

    // Update factory with new world
    entityFactory = new EntityFactory(world);

    // Create player
    player = entityFactory.createPlayer(WIDTH / 2, HEIGHT / 2);

    // Add systems
    world.addSystem(new MovementSystem(world, WIDTH, HEIGHT));
    world.addSystem(new PlayerControlSystem(world, inputManager));
    world.addSystem(new EnemySystem(world, entityFactory, WIDTH, HEIGHT));
    world.addSystem(new ProjectileSystem(world));
    world.addSystem(new CollisionSystem(world));
    world.addSystem(new RenderSystem(world, canvas.getGraphicsContext2D()));
  }

  private void checkGameOver() {
    if (player == null || !player.hasComponent(HealthComponent.class)) {
      gameOver = true;
      return;
    }

    HealthComponent health = player.getComponent(HealthComponent.class);
    if (health.isDead()) {
      gameOver = true;
    }
  }

  public static void main(String[] args) {
    launch(args);
  }
}
