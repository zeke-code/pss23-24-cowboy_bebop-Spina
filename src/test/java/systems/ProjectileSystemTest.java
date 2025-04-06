package systems;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import github.zekecode.cowboybebop.components.ProjectileComponent;
import github.zekecode.cowboybebop.components.TransformComponent;
import github.zekecode.cowboybebop.core.Entity;
import github.zekecode.cowboybebop.core.World;
import github.zekecode.cowboybebop.systems.ProjectileSystem;

public class ProjectileSystemTest {

    private World world;
    private ProjectileSystem projectileSystem;
    
    @BeforeEach
    void setUp() {
        world = new World();
        projectileSystem = new ProjectileSystem(world);
    }
    
    @Test
    @DisplayName("Projectile time alive should increase with deltaTime")
    void projectileTimeAliveIncreasesWithDeltaTime() {
        // Create a projectile
        Entity projectile = world.createEntity();
        ProjectileComponent projectileComponent = new ProjectileComponent(10, 3.0, "player");
        projectile.addComponent(projectileComponent);
        projectile.addComponent(new TransformComponent(100, 100));
        
        // Initial time alive should be zero
        assertEquals(0.0, projectileComponent.getTimeAlive(), 
                "Initial time alive should be zero");
        
        // Update with deltaTime
        double deltaTime = 0.5;
        projectileSystem.update(deltaTime);
        
        // Time alive should increase by deltaTime
        assertEquals(deltaTime, projectileComponent.getTimeAlive(), 
                "Time alive should increase by deltaTime");
        
        // Update again
        projectileSystem.update(deltaTime);
        
        // Time alive should accumulate
        assertEquals(deltaTime * 2, projectileComponent.getTimeAlive(), 
                "Time alive should accumulate with multiple updates");
    }
    
    @Test
    @DisplayName("Projectiles should be removed after lifetime expires")
    void projectilesRemovedAfterLifetimeExpires() {
        // Create a projectile with short lifetime
        Entity projectile = world.createEntity();
        ProjectileComponent projectileComponent = new ProjectileComponent(10, 1.0, "player");
        projectile.addComponent(projectileComponent);
        projectile.addComponent(new TransformComponent(100, 100));
        
        // Projectile should be in the world initially
        assertTrue(world.getEntities().contains(projectile), 
                "Projectile should be in the world initially");
        
        // Update with time less than lifetime
        projectileSystem.update(0.5);
        
        // Projectile should still be in the world
        assertTrue(world.getEntities().contains(projectile), 
                "Projectile should remain in the world when time alive < lifetime");
        
        // Update to exactly reach lifetime
        projectileSystem.update(0.5);
        
        // Projectile should be removed
        assertFalse(world.getEntities().contains(projectile), 
                "Projectile should be removed when time alive = lifetime");
    }
    
    @Test
    @DisplayName("Projectiles should be removed if time exceeds lifetime")
    void projectilesRemovedIfTimeExceedsLifetime() {
        // Create a projectile
        Entity projectile = world.createEntity();
        ProjectileComponent projectileComponent = new ProjectileComponent(10, 1.0, "player");
        projectile.addComponent(projectileComponent);
        projectile.addComponent(new TransformComponent(100, 100));
        
        // Update with time greater than lifetime
        projectileSystem.update(1.5);
        
        // Projectile should be removed
        assertFalse(world.getEntities().contains(projectile), 
                "Projectile should be removed when time alive > lifetime");
    }
    
    @Test
    @DisplayName("Multiple projectiles should be tracked independently")
    void multipleProjectilesTrackedIndependently() {
        // Create first projectile with long lifetime
        Entity projectile1 = world.createEntity();
        ProjectileComponent projectileComponent1 = new ProjectileComponent(10, 3.0, "player");
        projectile1.addComponent(projectileComponent1);
        projectile1.addComponent(new TransformComponent(100, 100));
        
        // Create second projectile with short lifetime
        Entity projectile2 = world.createEntity();
        ProjectileComponent projectileComponent2 = new ProjectileComponent(10, 1.0, "player");
        projectile2.addComponent(projectileComponent2);
        projectile2.addComponent(new TransformComponent(200, 200));
        
        // Update with time enough to expire second projectile but not first
        projectileSystem.update(2.0);
        
        // First projectile should still be in the world
        assertTrue(world.getEntities().contains(projectile1), 
                "Long-lifetime projectile should remain in the world");
        
        // Second projectile should be removed
        assertFalse(world.getEntities().contains(projectile2), 
                "Short-lifetime projectile should be removed");
        
        // First projectile's time should be updated
        assertEquals(2.0, projectileComponent1.getTimeAlive(), 
                "Long-lifetime projectile time should be updated");
    }
    
    @Test
    @DisplayName("Projectiles with zero lifetime should be removed immediately")
    void zeroLifetimeProjectilesRemovedImmediately() {
        // Create a projectile with zero lifetime
        Entity projectile = world.createEntity();
        projectile.addComponent(new ProjectileComponent(10, 0.0, "player"));
        projectile.addComponent(new TransformComponent(100, 100));
        
        // Projectile should be in the world before update
        assertTrue(world.getEntities().contains(projectile), 
                "Zero-lifetime projectile should be in world before update");
        
        // Update with any time
        projectileSystem.update(0.001);
        
        // Projectile should be removed
        assertFalse(world.getEntities().contains(projectile), 
                "Zero-lifetime projectile should be removed after update");
    }
    
    @Test
    @DisplayName("Negative lifetime projectiles should be removed immediately")
    void negativeLifetimeProjectilesRemovedImmediately() {
        // Create a projectile with negative lifetime (edge case)
        Entity projectile = world.createEntity();
        projectile.addComponent(new ProjectileComponent(10, -1.0, "player"));
        projectile.addComponent(new TransformComponent(100, 100));
        
        // Update with any time
        projectileSystem.update(0.001);
        
        // Projectile should be removed
        assertFalse(world.getEntities().contains(projectile), 
                "Negative-lifetime projectile should be removed after update");
    }
    
    @Test
    @DisplayName("System should handle entities without ProjectileComponent")
    void handleEntitiesWithoutProjectileComponent() {
        // Create an entity without ProjectileComponent
        Entity entity = world.createEntity();
        entity.addComponent(new TransformComponent(100, 100));
        
        // Update should not throw exceptions
        assertDoesNotThrow(() -> projectileSystem.update(1.0),
                "Update should not throw exception for entities without ProjectileComponent");
        
        // Entity should still be in the world
        assertTrue(world.getEntities().contains(entity),
                "Non-projectile entity should remain in the world");
    }
    
    @Test
    @DisplayName("System should handle empty world")
    void handleEmptyWorld() {
        // No entities in world
        
        // Update should not throw exceptions
        assertDoesNotThrow(() -> projectileSystem.update(1.0),
                "Update should not throw exception for empty world");
    }
    
    @Test
    @DisplayName("Multiple updates should accumulate time correctly")
    void multipleUpdatesAccumulateTimeCorrectly() {
        // Create a projectile
        Entity projectile = world.createEntity();
        ProjectileComponent projectileComponent = new ProjectileComponent(10, 3.0, "player");
        projectile.addComponent(projectileComponent);
        projectile.addComponent(new TransformComponent(100, 100));
        
        // Series of updates with different delta times
        projectileSystem.update(0.5);  // 0.5
        projectileSystem.update(0.7);  // 1.2
        projectileSystem.update(0.3);  // 1.5
        projectileSystem.update(0.8);  // 2.3
        
        // Time alive should be sum of all updates
        assertEquals(2.3, projectileComponent.getTimeAlive(), 0.001, 
                "Time alive should be the sum of all delta times");
        
        // Projectile should still be in the world (lifetime is 3.0)
        assertTrue(world.getEntities().contains(projectile), 
                "Projectile should remain in the world when time alive < lifetime");
        
        // Final update to exceed lifetime
        projectileSystem.update(1.0);  // 3.3
        
        // Projectile should be removed
        assertFalse(world.getEntities().contains(projectile), 
                "Projectile should be removed when accumulated time exceeds lifetime");
    }
}