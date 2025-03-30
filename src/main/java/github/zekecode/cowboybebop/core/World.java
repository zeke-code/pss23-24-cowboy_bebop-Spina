package github.zekecode.cowboybebop.core;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * This class is essential as it acts as a central repository for my game,
 * keeping track of all entites and systems that will be in place.
 */
public class World {
  /** The collection of all entities currently existing in the game world */
  private final Set<Entity> entities;
  /** Ordered list of game systems that are executed during each update cycle */
  private final List<System> gameSystems;

  /**
  * Creates a new empty game world.
  * Initializes the entity collection and system list with no initial entities or systems.
  */
  public World() {
    entities = new HashSet<>();
    gameSystems = new ArrayList<>();
  }

  /**
   * Creates and registers a new entity in the world.
   * @return a new Entity instance ready to have components added
   */
  public Entity createEntity() {
    Entity entity = new Entity();
    entities.add(entity);
    return entity;
  }

  /**
   * Removes an entity from the world.
   * @param entity the entity to be removed
   */
  public void removeEntity(Entity entity) {
    entities.remove(entity);
  }

  /**
   * Adds a system to the world to be executed during updates.
   * @param gameSystem the system to be added
   */
  public void addSystem(System gameSystem) {
    gameSystems.add(gameSystem);
  }

  /**
   * Removes a system from the world's update cycle.
   * 
   * @param <T> the specific type of System being removed, extending the base System interface
   * @param system the system instance to be removed from the world
   * @return true if the system was found and removed, false if the system wasn't in the world
   */
  public <T extends System> void removeSystem(T system) {
    gameSystems.remove(system);
  }

  /**
   * Executes the update cycle for all systems registered in the world.
   * This is a core method of the game loop that advances the game state 
   * by the specified amount of time.
   * 
   * @param deltaTime the time elapsed between consecutive frames in seconds,
   *                  used to ensure frame-rate independent behavior
   */
  public void update(double deltaTime) {
    for (System gameSystem : gameSystems) {
        gameSystem.update(deltaTime);
    }
  }

  /**
   * Returns the set of entities that are currently present and tracked in the world.
   * The set returned by this function is a defensive copy of all entities currently in the world.
   * Any modifications to the returned set won't affect the actual game state.
   * 
   * @return a new HashSet containing all entities in the world
   */
  public Set<Entity> getEntities() {
    return new HashSet<>(entities);
  }

  /**
   * Filters and returns entities that have a specific component type.
   * 
   * @param <T> the type of component to filter entities by
   * @param componentClass the class object representing the component type
   * @return a new HashSet containing only entities that possess the specified component
   */
  public <T extends Component> Set<Entity> getEntitiesWithComponent(Class<T> componentClass) {
    Set<Entity> result = new HashSet<>();
    for (Entity entity : entities) {
      if (entity.hasComponent(componentClass)) {
        result.add(entity);
      }
    }
    return result;
  }
}
