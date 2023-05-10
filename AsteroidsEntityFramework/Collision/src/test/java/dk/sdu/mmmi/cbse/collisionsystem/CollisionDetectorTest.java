package dk.sdu.mmmi.cbse.collisionsystem;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CollisionDetectorTest {
    /**
     * Test of collides method, of class CollisionDetection.
     * It is done by creating two entities with the same position and radius.
     * Then the method is called and the result is compared to the expected result.
     * The expected result should true, because the two entities are colliding.
     */
    @Test
    public void collisiontrue() {
        Entity entity1 = new Entity();
        Entity entity2 = new Entity();
        entity1.setRadius(15);
        entity2.setRadius(15);
        entity1.add(new PositionPart(15,15,15));
        entity2.add(new PositionPart(15,15,15));
        CollisionDetector collisionDetection = new CollisionDetector();
        boolean result = collisionDetection.collides(entity1, entity2);
        assertEquals(true, result);
    }

    /**
     * Test of collides method, of class CollisionDetection.
     * It is done by creating two entities with different positions and radius.
     * Then the method is called and the result is compared to the expected result.
     * The expected result should be false, because the two entities are not colliding.
     */
    @Test
    public void collisionfalse() {
        Entity entity1 = new Entity();
        Entity entity2 = new Entity();
        entity1.setRadius(10);
        entity2.setRadius(10);
        entity1.add(new PositionPart(20,20,20));
        entity2.add(new PositionPart(50,50,50));
        CollisionDetector collisionDetection = new CollisionDetector();
        boolean result = collisionDetection.collides(entity1, entity2);
        assertEquals(false, result);
    }
}