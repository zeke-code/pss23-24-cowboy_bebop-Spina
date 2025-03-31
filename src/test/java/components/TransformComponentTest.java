package components;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import github.zekecode.cowboybebop.components.TransformComponent;

public class TransformComponentTest {

    private static final double DELTA = 0.0001; // Tolerance for double comparisons
    private static final double TEST_X = 100.0;
    private static final double TEST_Y = 200.0;
    private static final double TEST_ROTATION = Math.PI / 4; // 45 degrees in radians
    
    @Test
    @DisplayName("Two-parameter constructor should initialize position with zero rotation")
    void twoParameterConstructorInitializesCorrectly() {
        TransformComponent transform = new TransformComponent(TEST_X, TEST_Y);
        
        assertEquals(TEST_X, transform.getX(), DELTA, 
                "X position should match constructor parameter");
        assertEquals(TEST_Y, transform.getY(), DELTA, 
                "Y position should match constructor parameter");
        assertEquals(0.0, transform.getRotation(), DELTA, 
                "Rotation should default to 0");
    }
    
    @Test
    @DisplayName("Three-parameter constructor should initialize position and rotation")
    void threeParameterConstructorInitializesCorrectly() {
        TransformComponent transform = new TransformComponent(TEST_X, TEST_Y, TEST_ROTATION);
        
        assertEquals(TEST_X, transform.getX(), DELTA, 
                "X position should match constructor parameter");
        assertEquals(TEST_Y, transform.getY(), DELTA, 
                "Y position should match constructor parameter");
        assertEquals(TEST_ROTATION, transform.getRotation(), DELTA, 
                "Rotation should match constructor parameter");
    }
    
    @Test
    @DisplayName("X position getter and setter should work correctly")
    void xPositionGetterAndSetter() {
        TransformComponent transform = new TransformComponent(TEST_X, TEST_Y);
        assertEquals(TEST_X, transform.getX(), DELTA, "Initial X should match constructor value");
        
        double newX = 300.0;
        transform.setX(newX);
        assertEquals(newX, transform.getX(), DELTA, "X should update when set");
    }
    
    @Test
    @DisplayName("Y position getter and setter should work correctly")
    void yPositionGetterAndSetter() {
        TransformComponent transform = new TransformComponent(TEST_X, TEST_Y);
        assertEquals(TEST_Y, transform.getY(), DELTA, "Initial Y should match constructor value");
        
        double newY = 400.0;
        transform.setY(newY);
        assertEquals(newY, transform.getY(), DELTA, "Y should update when set");
    }
    
    @Test
    @DisplayName("Rotation getter and setter should work correctly")
    void rotationGetterAndSetter() {
        TransformComponent transform = new TransformComponent(TEST_X, TEST_Y, TEST_ROTATION);
        assertEquals(TEST_ROTATION, transform.getRotation(), DELTA, 
                "Initial rotation should match constructor value");
        
        double newRotation = Math.PI; // 180 degrees
        transform.setRotation(newRotation);
        assertEquals(newRotation, transform.getRotation(), DELTA, 
                "Rotation should update when set");
    }
    
    @Test
    @DisplayName("Should handle negative position values")
    void handlesNegativePositions() {
        double negX = -100.0;
        double negY = -200.0;
        TransformComponent transform = new TransformComponent(negX, negY);
        
        assertEquals(negX, transform.getX(), DELTA, "Should allow negative X position");
        assertEquals(negY, transform.getY(), DELTA, "Should allow negative Y position");
    }
    
    @Test
    @DisplayName("Should handle negative and positive rotation values")
    void handlesRotationValues() {
        // Test positive rotation
        double posRotation = 2 * Math.PI; // 360 degrees
        TransformComponent transform1 = new TransformComponent(TEST_X, TEST_Y, posRotation);
        assertEquals(posRotation, transform1.getRotation(), DELTA, 
                "Should handle positive rotation values");
        
        // Test negative rotation
        double negRotation = -Math.PI / 2; // -90 degrees
        TransformComponent transform2 = new TransformComponent(TEST_X, TEST_Y, negRotation);
        assertEquals(negRotation, transform2.getRotation(), DELTA, 
                "Should handle negative rotation values");
    }
    
    @Test
    @DisplayName("Should handle edge case zero values")
    void handlesZeroValues() {
        TransformComponent transform = new TransformComponent(0.0, 0.0, 0.0);
        
        assertEquals(0.0, transform.getX(), DELTA, "Should handle zero X position");
        assertEquals(0.0, transform.getY(), DELTA, "Should handle zero Y position");
        assertEquals(0.0, transform.getRotation(), DELTA, "Should handle zero rotation");
    }
    
    @Test
    @DisplayName("Should handle very large values")
    void handlesLargeValues() {
        double largeX = 1000000.0;
        double largeY = 2000000.0;
        double largeRotation = 1000 * Math.PI;
        
        TransformComponent transform = new TransformComponent(largeX, largeY, largeRotation);
        
        assertEquals(largeX, transform.getX(), DELTA, "Should handle large X position");
        assertEquals(largeY, transform.getY(), DELTA, "Should handle large Y position");
        assertEquals(largeRotation, transform.getRotation(), DELTA, "Should handle large rotation");
    }
    
    @Test
    @DisplayName("Should handle very small values")
    void handlesSmallValues() {
        double smallX = 0.000001;
        double smallY = 0.000002;
        double smallRotation = 0.000003;
        
        TransformComponent transform = new TransformComponent(smallX, smallY, smallRotation);
        
        assertEquals(smallX, transform.getX(), DELTA, "Should handle small X position");
        assertEquals(smallY, transform.getY(), DELTA, "Should handle small Y position");
        assertEquals(smallRotation, transform.getRotation(), DELTA, "Should handle small rotation");
    }
}