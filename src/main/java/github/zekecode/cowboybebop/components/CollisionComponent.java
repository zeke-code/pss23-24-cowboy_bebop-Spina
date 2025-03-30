package github.zekecode.cowboybebop.components;

import github.zekecode.cowboybebop.core.Component;

public class CollisionComponent implements Component {
  private double radius;
  private boolean isSolid;
  private String collisionGroup;

  public CollisionComponent(double radius, boolean isSolid) {
    this.radius = radius;
    this.isSolid = isSolid;
    this.collisionGroup = "default";
  }

  public CollisionComponent(double radius, boolean isSolid, String collisionGroup) {
    this.radius = radius;
    this.isSolid = isSolid;
    this.collisionGroup = collisionGroup;
  }

  public double getRadius() {
    return radius;
  }

  public void setRadius(double radius) {
    this.radius = radius;
  }

  public boolean isSolid() {
    return isSolid;
  }

  public void setSolid(boolean solid) {
    isSolid = solid;
  }

  public String getCollisionGroup() {
    return collisionGroup;
  }

  public void setCollisionGroup(String collisionGroup) {
    this.collisionGroup = collisionGroup;
  }
}
