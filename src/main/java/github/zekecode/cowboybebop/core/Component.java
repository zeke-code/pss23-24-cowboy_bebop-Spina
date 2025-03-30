package github.zekecode.cowboybebop.core;

/**
 * The Component interface serves as a marker for all components in the Entity-Component-System
 * architecture. This marker interface allows for type safety when adding components to entities.
 *
 * <p>Components are pure data containers that represent specific attributes or capabilities of
 * entities. They generally contain no behavior or logic - instead, they store state that is
 * processed by Systems. This separation of data (Components) from behavior (Systems) is a
 * fundamental principle of the ECS pattern.
 *
 * @see Entity
 * @see System
 * @see World
 */
public interface Component {
  // Marker interface for all components, has no methods (yeah pretty empty here if you ask me)
}
