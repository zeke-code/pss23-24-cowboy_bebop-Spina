package github.zekecode.cowboybebop.core;

/**
 * The System interface defines the core behavior for all systems in the Entity-Component-System
 * architecture.
 *
 * <p>Systems are responsible for implementing game logic and behavior by operating on entities that
 * have specific combinations of components. Each system focuses on a particular aspect of gameplay
 * such as movement, rendering, collisions, or AI. Systems execute during each game loop update
 * cycle and process all relevant entities based on their components.
 *
 * <p>Systems are registered with the {@link World} class and are automatically invoked during the
 * world's update cycle in the order they were added.
 */
public interface System {

  /**
   * Executes this system's logic for the current frame.
   *
   * <p>The update method is called once per frame by the game loop and represents the core
   * processing function of the system. Implementations should use this method to locate relevant
   * entities and apply the system's specialized logic to them.
   *
   * <p>When implementing this method, care should be taken to ensure frame-rate independence by
   * multiplying time-based values by the deltaTime parameter.
   *
   * @param deltaTime the elapsed time since the previous frame in seconds, used to ensure
   *     frame-rate independent behavior
   */
  void update(double deltaTime);
}
