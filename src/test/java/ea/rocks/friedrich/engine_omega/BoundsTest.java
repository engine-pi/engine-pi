package ea.rocks.friedrich.engine_omega;

import rocks.friedrich.engine_omega.internal.Bounds;
import rocks.friedrich.engine_omega.Vector;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BoundsTest {
  @Test
  public void methodGetCenter () {
    Bounds bounds = new Bounds(0, 0, 1, 1);
    Vector vector = bounds.getCenter();
    assertEquals(vector.getX(), 0.5f, 0);
    assertEquals(vector.getY(), 0.5f, 0);
  }
}
