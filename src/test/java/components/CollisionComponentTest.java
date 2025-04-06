package components;

import static org.junit.jupiter.api.Assertions.*;

import github.zekecode.cowboybebop.components.CollisionComponent;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CollisionComponentTest {

  private static final double TEST_RADIUS = 10.0;
  private static final boolean TEST_IS_SOLID = true;
  private static final String TEST_COLLISION_GROUP = "player";

  @Test
  @DisplayName("Two-parameter constructor should initialize with default collision group")
  void twoParameterConstructorInitializesCorrectly() {
    CollisionComponent collision = new CollisionComponent(TEST_RADIUS, TEST_IS_SOLID);

    assertEquals(TEST_RADIUS, collision.getRadius(), "Radius should match constructor parameter");
    assertEquals(
        TEST_IS_SOLID, collision.isSolid(), "isSolid flag should match constructor parameter");
    assertEquals(
        "default", collision.getCollisionGroup(), "Collision group should be set to default");
  }

  @Test
  @DisplayName("Three-parameter constructor should initialize all fields")
  void threeParameterConstructorInitializesCorrectly() {
    CollisionComponent collision =
        new CollisionComponent(TEST_RADIUS, TEST_IS_SOLID, TEST_COLLISION_GROUP);

    assertEquals(TEST_RADIUS, collision.getRadius(), "Radius should match constructor parameter");
    assertEquals(
        TEST_IS_SOLID, collision.isSolid(), "isSolid flag should match constructor parameter");
    assertEquals(
        TEST_COLLISION_GROUP,
        collision.getCollisionGroup(),
        "Collision group should match constructor parameter");
  }

  @Test
  @DisplayName("Radius getter and setter should work correctly")
  void radiusGetterAndSetter() {
    CollisionComponent collision = new CollisionComponent(TEST_RADIUS, TEST_IS_SOLID);

    // Test initial value
    assertEquals(
        TEST_RADIUS, collision.getRadius(), "Initial radius should match constructor value");

    // Test setting new value
    double newRadius = 20.0;
    collision.setRadius(newRadius);
    assertEquals(newRadius, collision.getRadius(), "Radius should update when set");
  }

  @Test
  @DisplayName("isSolid getter and setter should work correctly")
  void isSolidGetterAndSetter() {
    CollisionComponent collision = new CollisionComponent(TEST_RADIUS, TEST_IS_SOLID);

    // Test initial value
    assertEquals(
        TEST_IS_SOLID, collision.isSolid(), "Initial isSolid should match constructor value");

    // Test toggling the value
    collision.setSolid(!TEST_IS_SOLID);
    assertEquals(!TEST_IS_SOLID, collision.isSolid(), "isSolid should update when set");
  }

  @Test
  @DisplayName("CollisionGroup getter and setter should work correctly")
  void collisionGroupGetterAndSetter() {
    CollisionComponent collision =
        new CollisionComponent(TEST_RADIUS, TEST_IS_SOLID, TEST_COLLISION_GROUP);

    // Test initial value
    assertEquals(
        TEST_COLLISION_GROUP,
        collision.getCollisionGroup(),
        "Initial collision group should match constructor value");

    // Test setting new value
    String newGroup = "enemy";
    collision.setCollisionGroup(newGroup);
    assertEquals(newGroup, collision.getCollisionGroup(), "Collision group should update when set");
  }

  @Test
  @DisplayName("Should handle zero radius")
  void handlesZeroRadius() {
    CollisionComponent collision = new CollisionComponent(0.0, TEST_IS_SOLID);
    assertEquals(0.0, collision.getRadius(), "Should allow zero radius for point collisions");
  }

  @Test
  @DisplayName("Should handle negative radius by storing the negative value")
  void handlesNegativeRadius() {
    double negativeRadius = -5.0;
    CollisionComponent collision = new CollisionComponent(negativeRadius, TEST_IS_SOLID);

    // This tests the current behavior - storing negative values as-is
    // If the component should reject negative values, this test would need to be adjusted
    assertEquals(negativeRadius, collision.getRadius(), "Negative radius should be stored as-is");
  }

  @Test
  @DisplayName("Should handle null collision group by setting it to null")
  void handlesNullCollisionGroup() {
    // First create with valid group
    CollisionComponent collision =
        new CollisionComponent(TEST_RADIUS, TEST_IS_SOLID, TEST_COLLISION_GROUP);

    // Then set to null
    collision.setCollisionGroup(null);

    // This tests the current behavior - allowing null values
    // If the component should reject null values, this test would need to be adjusted
    assertNull(collision.getCollisionGroup(), "Should allow setting collision group to null");
  }

  @Test
  @DisplayName("Should handle empty collision group")
  void handlesEmptyCollisionGroup() {
    CollisionComponent collision = new CollisionComponent(TEST_RADIUS, TEST_IS_SOLID, "");
    assertEquals("", collision.getCollisionGroup(), "Should allow empty string as collision group");
  }
}
