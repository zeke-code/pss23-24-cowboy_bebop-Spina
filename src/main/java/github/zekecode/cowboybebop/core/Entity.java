package github.zekecode.cowboybebop.core;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * The Entity class represents a single game object in the Entity-Component-System architecture.
 * 
 * Entities are lightweight containers that exist primarily as identifiers and holders for
 * components. They have no behavior of their own - all game logic related to entities is
 * implemented in Systems that operate on their components. An entity's identity and behavior
 * is defined entirely by its unique combination of components.
 *
 * Each entity is assigned a unique identifier upon creation and provides methods for adding,
 * checking, retrieving, and removing components.
 */
public class Entity {
  /** Unique identifier for this Entity */

  /** Map of components attached to this entity, indexed by component Type */
  private final String id;
  private final Map<Class<? extends Component>, Component> components;

  /**
   * Creates a new entity with a randomly generated unique identifier and no components.
   * Components must be added using the {@link #addComponent} method.
   */
  public Entity() {
    this.id = UUID.randomUUID().toString();
    this.components = new HashMap<>();
  }

  /**
   * Returns the ID for this entity.
   * 
   * @return the entity's UUID as a String
   */
  public String getId() {
    return id;
  }

  /**
   * Adds a component to this entity. If a component of the same type already exists,
   * it will be replaced by the new component.
   * 
   * @param <T> the component type
   * @param component the component instance to add
   * @return this entity instance for method chaining
   */
  public <T extends Component> Entity addComponent(T component) {
    components.put(component.getClass(), component);
    return this;
  }

  /**
   * Checks if this entity has a component of the specified type.
   *
   * @param <T> the component type to check for
   * @param componentClass the class object representing the component type
   * @return true if the entity has the component, false otherwise
   */
  public <T extends Component> boolean hasComponent(Class<T> componentClass) {
    return components.containsKey(componentClass);
  }

  /**
   * Retrieves a component of the specified type from this entity.
   *
   * Note: This method assumes the component exists. It's recommended to check
   * with {@link #hasComponent} before calling this method to avoid null returns.
   *
   * @param <T> the component type to retrieve
   * @param componentClass the class object representing the component type
   * @return the component instance, or null if the entity doesn't have this component
   */
  @SuppressWarnings("unchecked")
  public <T extends Component> T getComponent(Class<T> componentClass) {
    return (T) components.get(componentClass);
  }

  /**
   * Removes a component of the specified type from this entity
   * 
   * @param <T> the component type to remove
   * @param componentClass the class object representing the component type
   */
  public <T extends Component> void removeComponent(Class<T> componentClass) {
    components.remove(componentClass);
  }

  /**
   * Compares this entity with another object for equality.
   * Two entities are equal if they have the same unique identifier.
   *
   * @param obj the object to compare with
   * @return true if the objects are equal, false otherwise
   */
  @Override
  public boolean equals(Object obj) {
    if (obj == null) return false;
    if (!(obj instanceof Entity)) return false;
    Entity other = (Entity) obj;
    return this.id.equals(other.id);
  }

  /**
   * Returns a hash code value for this entity.
   * The hash code is derived from the entity's unique identifier.
   *
   * @return a hash code value for this entity
   */
  @Override
  public int hashCode() {
    return id.hashCode();
  }
}
