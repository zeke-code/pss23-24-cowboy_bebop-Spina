package github.zekecode.cowboybebop.core;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Entity {
  private final String id;
  private final Map<Class<? extends Component>, Component> components;

  public Entity() {
    this.id = UUID.randomUUID().toString();
    this.components = new HashMap<>();
  }

  public String getId() {
    return id;
  }

  public <T extends Component> Entity addComponent(T component) {
    components.put(component.getClass(), component);
    return this;
  }

  public <T extends Component> boolean hasComponent(Class<T> componentClass) {
    return components.containsKey(componentClass);
  }

  @SuppressWarnings("unchecked")
  public <T extends Component> T getComponent(Class<T> componentClass) {
    return (T) components.get(componentClass);
  }

  public <T extends Component> void removeComponent(Class<T> componentClass) {
    components.remove(componentClass);
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == null) return false;
    if (!(obj instanceof Entity)) return false;
    Entity other = (Entity) obj;
    return this.id.equals(other.id);
  }

  @Override
  public int hashCode() {
    return id.hashCode();
  }
}
