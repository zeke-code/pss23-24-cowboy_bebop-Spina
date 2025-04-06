package factory;

import static org.junit.jupiter.api.Assertions.*;

import github.zekecode.cowboybebop.components.*;
import github.zekecode.cowboybebop.core.Entity;
import github.zekecode.cowboybebop.core.World;
import github.zekecode.cowboybebop.factory.EntityFactory;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class EntityFactoryTest {

  private World world;
  private EntityFactory entityFactory;

  @BeforeEach
  void setUp() {
    world = new World();
    entityFactory = new EntityFactory(world);
  }

  @Test
  @DisplayName("createPlayer should create entity with all required components")
  void createPlayerAddsAllComponents() {
    double x = 100;
    double y = 200;

    Entity player = entityFactory.createPlayer(x, y);

    // Verify all expected components are present
    assertNotNull(
        player.getComponent(TransformComponent.class), "Player should have TransformComponent");
    assertNotNull(
        player.getComponent(VelocityComponent.class), "Player should have VelocityComponent");
    assertNotNull(player.getComponent(SpriteComponent.class), "Player should have SpriteComponent");
    assertNotNull(
        player.getComponent(CollisionComponent.class), "Player should have CollisionComponent");
    assertNotNull(player.getComponent(HealthComponent.class), "Player should have HealthComponent");
    assertNotNull(player.getComponent(PlayerComponent.class), "Player should have PlayerComponent");

    // Verify component properties
    assertEquals(
        x,
        player.getComponent(TransformComponent.class).getX(),
        "Player X position should match input");
    assertEquals(
        y,
        player.getComponent(TransformComponent.class).getY(),
        "Player Y position should match input");
    assertEquals(
        200,
        player.getComponent(VelocityComponent.class).getMaxSpeed(),
        "Player max speed should be 200");
    assertEquals(
        15,
        player.getComponent(CollisionComponent.class).getRadius(),
        "Player collision radius should be 15");
    assertEquals(
        "player",
        player.getComponent(CollisionComponent.class).getCollisionGroup(),
        "Player collision group should be 'player'");
    assertEquals(
        100,
        player.getComponent(HealthComponent.class).getMaxHealth(),
        "Player max health should be 100");
    assertEquals(
        0.25,
        player.getComponent(PlayerComponent.class).getShootCooldown(),
        "Player shoot cooldown should be 0.25");
    assertEquals(
        400,
        player.getComponent(PlayerComponent.class).getShootSpeed(),
        "Player shoot speed should be 400");

    // Verify sprite properties
    SpriteComponent sprite = player.getComponent(SpriteComponent.class);
    assertTrue(sprite.getShape() instanceof Rectangle, "Player sprite should be a Rectangle");
    assertEquals(Color.BLUE, sprite.getColor(), "Player color should be BLUE");
  }

  @Test
  @DisplayName("createEnemy(x,y) should create random enemy")
  void createRandomEnemy() {
    double x = 150;
    double y = 250;

    Entity enemy = entityFactory.createEnemy(x, y);

    // Verify all expected components are present
    assertNotNull(
        enemy.getComponent(TransformComponent.class), "Enemy should have TransformComponent");
    assertNotNull(
        enemy.getComponent(VelocityComponent.class), "Enemy should have VelocityComponent");
    assertNotNull(enemy.getComponent(SpriteComponent.class), "Enemy should have SpriteComponent");
    assertNotNull(
        enemy.getComponent(CollisionComponent.class), "Enemy should have CollisionComponent");
    assertNotNull(enemy.getComponent(HealthComponent.class), "Enemy should have HealthComponent");
    assertNotNull(enemy.getComponent(EnemyComponent.class), "Enemy should have EnemyComponent");

    // Verify component properties that are fixed
    assertEquals(
        x,
        enemy.getComponent(TransformComponent.class).getX(),
        "Enemy X position should match input");
    assertEquals(
        y,
        enemy.getComponent(TransformComponent.class).getY(),
        "Enemy Y position should match input");
    assertEquals(
        100,
        enemy.getComponent(VelocityComponent.class).getMaxSpeed(),
        "Enemy max speed should be 100");
    assertEquals(
        10,
        enemy.getComponent(CollisionComponent.class).getRadius(),
        "Enemy collision radius should be 10");
    assertEquals(
        "enemy",
        enemy.getComponent(CollisionComponent.class).getCollisionGroup(),
        "Enemy collision group should be 'enemy'");
    assertEquals(
        20,
        enemy.getComponent(HealthComponent.class).getMaxHealth(),
        "Enemy max health should be 20");

    // Verify range constraints for random properties
    EnemyComponent enemyComp = enemy.getComponent(EnemyComponent.class);
    assertTrue(
        enemyComp.getSpeedFactor() >= 0.5 && enemyComp.getSpeedFactor() <= 1.0,
        "Enemy speed factor should be between 0.5 and 1.0");
    assertTrue(
        enemyComp.getDamage() >= 10 && enemyComp.getDamage() <= 19,
        "Enemy damage should be between 10 and 19");
    assertTrue(
        enemyComp.getPoints() >= 50 && enemyComp.getPoints() <= 99,
        "Enemy points should be between 50 and 99");

    // Verify sprite properties
    SpriteComponent sprite = enemy.getComponent(SpriteComponent.class);
    assertTrue(sprite.getShape() instanceof Rectangle, "Enemy sprite should be a Rectangle");
    assertEquals(Color.RED, sprite.getColor(), "Enemy color should be RED");
  }

  @Test
  @DisplayName("createEnemy(x,y,type) should create 'basic' enemy")
  void createBasicEnemy() {
    double x = 200;
    double y = 300;

    Entity enemy = entityFactory.createEnemy(x, y, "basic");

    // Verify all expected components are present
    assertNotNull(
        enemy.getComponent(TransformComponent.class), "Basic enemy should have TransformComponent");

    // Verify type-specific properties
    assertEquals(
        0.8,
        enemy.getComponent(EnemyComponent.class).getSpeedFactor(),
        "Basic enemy speed factor should be 0.8");
    assertEquals(
        10,
        enemy.getComponent(EnemyComponent.class).getDamage(),
        "Basic enemy damage should be 10");
    assertEquals(
        20,
        enemy.getComponent(HealthComponent.class).getMaxHealth(),
        "Basic enemy max health should be 20");
    assertEquals(
        10,
        enemy.getComponent(CollisionComponent.class).getRadius(),
        "Basic enemy collision radius should be 10");

    // Verify sprite properties
    SpriteComponent sprite = enemy.getComponent(SpriteComponent.class);
    assertEquals(Color.RED, sprite.getColor(), "Basic enemy color should be RED");
  }

  @Test
  @DisplayName("createEnemy(x,y,type) should create 'speedy' enemy")
  void createSpeedyEnemy() {
    double x = 200;
    double y = 300;

    Entity enemy = entityFactory.createEnemy(x, y, "speedy");

    // Verify type-specific properties
    assertEquals(
        1.2,
        enemy.getComponent(EnemyComponent.class).getSpeedFactor(),
        "Speedy enemy speed factor should be 1.2");
    assertEquals(
        5, enemy.getComponent(EnemyComponent.class).getDamage(), "Speedy enemy damage should be 5");
    assertEquals(
        15,
        enemy.getComponent(HealthComponent.class).getMaxHealth(),
        "Speedy enemy max health should be 15");
    assertEquals(
        7.5,
        enemy.getComponent(CollisionComponent.class).getRadius(),
        "Speedy enemy collision radius should be 7.5");

    // Verify sprite properties
    SpriteComponent sprite = enemy.getComponent(SpriteComponent.class);
    assertEquals(Color.ORANGE, sprite.getColor(), "Speedy enemy color should be ORANGE");
  }

  @Test
  @DisplayName("createEnemy(x,y,type) should create 'tank' enemy")
  void createTankEnemy() {
    double x = 200;
    double y = 300;

    Entity enemy = entityFactory.createEnemy(x, y, "tank");

    // Verify type-specific properties
    assertEquals(
        0.6,
        enemy.getComponent(EnemyComponent.class).getSpeedFactor(),
        "Tank enemy speed factor should be 0.6");
    assertEquals(
        15, enemy.getComponent(EnemyComponent.class).getDamage(), "Tank enemy damage should be 15");
    assertEquals(
        40,
        enemy.getComponent(HealthComponent.class).getMaxHealth(),
        "Tank enemy max health should be 40");
    assertEquals(
        12.5,
        enemy.getComponent(CollisionComponent.class).getRadius(),
        "Tank enemy collision radius should be 12.5");

    // Verify sprite properties
    SpriteComponent sprite = enemy.getComponent(SpriteComponent.class);
    assertEquals(Color.DARKRED, sprite.getColor(), "Tank enemy color should be DARKRED");
  }

  @Test
  @DisplayName("createEnemy(x,y,type) with invalid type should create basic enemy")
  void createEnemyWithInvalidType() {
    double x = 200;
    double y = 300;

    Entity enemy = entityFactory.createEnemy(x, y, "nonexistent");

    // Should default to basic enemy properties
    assertEquals(
        0.8,
        enemy.getComponent(EnemyComponent.class).getSpeedFactor(),
        "Invalid enemy type should default to speed factor 0.8");
    assertEquals(
        10,
        enemy.getComponent(EnemyComponent.class).getDamage(),
        "Invalid enemy type should default to damage 10");
    assertEquals(
        20,
        enemy.getComponent(HealthComponent.class).getMaxHealth(),
        "Invalid enemy type should default to health 20");

    // Verify sprite properties
    SpriteComponent sprite = enemy.getComponent(SpriteComponent.class);
    assertEquals(Color.RED, sprite.getColor(), "Invalid enemy type should default to RED color");
  }

  @Test
  @DisplayName("Created entities should be added to the world")
  void createdEntitiesAddedToWorld() {
    Entity player = entityFactory.createPlayer(100, 100);
    Entity basicEnemy = entityFactory.createEnemy(200, 200, "basic");
    Entity randomEnemy = entityFactory.createEnemy(300, 300);

    assertTrue(world.getEntities().contains(player), "Player should be added to the world");
    assertTrue(
        world.getEntities().contains(basicEnemy), "Basic enemy should be added to the world");
    assertTrue(
        world.getEntities().contains(randomEnemy), "Random enemy should be added to the world");
  }
}
