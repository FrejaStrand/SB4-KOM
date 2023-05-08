/**
 * This class is responsible for splitting the asteroids.
 * It handles the creation of the new asteroids.
 * It also updates the shape of the asteroids.
 * It implements the IAsteroidSplitter interface.
 */
package dk.sdu.mmmi.cbse.asteroidssystem;

import dk.sdu.mmmi.cbse.common.asteroids.Asteroids;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.asteroids.IAsteroidSplitter;
import dk.sdu.mmmi.cbse.common.data.entityparts.LifePart;
import dk.sdu.mmmi.cbse.common.data.entityparts.MovingPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

public class AsteroidSplitterImpl implements IAsteroidSplitter {

    /**
     * This method is responsible for creating the new asteroids.
     * pre-condition: The entity must be an asteroid.
     * post-condition: The entity is split into two new asteroids.
     *
     * @param e    The entity to be split.
     * @param world The game world containing all entities.
     */
    @Override
    public void createSplitAsteroid(Entity e, World world) {
        PositionPart otherPos = e.getPart(PositionPart.class);
        LifePart otherLife = e.getPart(LifePart.class);
        float radians = otherPos.getRadians();
        int radius = 0;
        float speed = 5;
        int life = otherLife.getLife() - 1;
        if (life == 1) {
            radius = 10;
            speed = (float) Math.random() * 30f + 70f;
        } else if (life == 2) {
            radius = 15;
            speed = (float) Math.random() * 10f + 50f;
        } else if (life <= 0) {
            world.removeEntity(e);
            return;
        }

        Entity asteroid1 = new Asteroids();

        asteroid1.setRadius(radius);
        float radians1 = radians - 0.5f;

        float by1 = (float) sin(radians1) * e.getRadius() * asteroid1.getRadius();
        float bx1 = (float) cos(radians1) * e.getRadius() * asteroid1.getRadius();

        PositionPart astPositionPart1 = new PositionPart(otherPos.getX() + bx1, otherPos.getY() + by1, radians1);
        asteroid1.add(new MovingPart(0, 5000, speed, 0));
        asteroid1.add(astPositionPart1);
        asteroid1.add(new LifePart(life));

        world.addEntity(asteroid1);

        Entity asteroid2 = new Asteroids();

        asteroid2.setRadius(radius);
        float radians2 = radians + 0.5f;

        float by2 = (float) sin(radians2) * e.getRadius() * asteroid2.getRadius();
        float bx2 = (float) cos(radians2) * e.getRadius() * asteroid2.getRadius();
        PositionPart astPositionPart2 = new PositionPart(otherPos.getX() + bx2, otherPos.getY() + by2, radians2);

        asteroid2.add(new MovingPart(0, 5000, speed, 0));
        asteroid2.add(astPositionPart2);
        asteroid2.add(new LifePart(life));

        world.addEntity(asteroid2);

        world.removeEntity(e);
    }
}
