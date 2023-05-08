/**
 * This class is responsible for controlling the asteroids.
 * It handles the movement of the asteroids and the splitting of the asteroids.
 * It also updates the shape of the asteroids.
 */
package dk.sdu.mmmi.cbse.asteroidssystem;

import dk.sdu.mmmi.cbse.common.asteroids.Asteroids;
import dk.sdu.mmmi.cbse.common.asteroids.IAsteroidSplitter;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.LifePart;
import dk.sdu.mmmi.cbse.common.data.entityparts.MovingPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;

import java.util.Random;

public class AsteroidsControlSystem implements IEntityProcessingService {

    /**
     * This method is responsible for processing the asteroids.
     */
    private IAsteroidSplitter asteroidSplitter = new AsteroidSplitterImpl();

    /**
     * This method is responsible for processing the asteroids.
     * pre-condition: The entity must be an asteroid.
     * post-condition: The entity is split into two new asteroids.
     *
     * @param gameData The current game state.
     * @param world    The game world containing all entities.
     */
    @Override
    public void process(GameData gameData, World world) {
        for (Entity asteroid : world.getEntities(Asteroids.class)) {
            PositionPart positionPart = asteroid.getPart(PositionPart.class);
            MovingPart movingPart = asteroid.getPart(MovingPart.class);
            LifePart lifePart = asteroid.getPart(LifePart.class);

            int numPoints = 12;
            if (lifePart.getLife() == 1) {
                numPoints = 8;
            } else if (lifePart.getLife() == 2) {
                numPoints = 10;
            }

            Random random = new Random();

            movingPart.setLeft(random.nextBoolean());
            movingPart.setRight(random.nextBoolean());
            movingPart.setUp(random.nextBoolean());

            movingPart.process(gameData, asteroid);
            positionPart.process(gameData, asteroid);
            lifePart.process(gameData, asteroid);

            // Split event
            if (lifePart.isIsHit()) {
                asteroidSplitter.createSplitAsteroid(asteroid, world);
            }
            updateShape(asteroid, numPoints);

        }
    }

    /**
     * This method is responsible for updating the shape of the asteroids.
     * pre-condition: The entity must be an asteroid.
     * post-condition: The entity has a new shape.
     *
     * @param entity    The entity to be updated.
     * @param numPoints The number of points the asteroid should have.
     */
    private void updateShape(Entity entity, int numPoints) {
        float[] shapex = new float[numPoints];
        float[] shapey = new float[numPoints];
        PositionPart positionPart = entity.getPart(PositionPart.class);
        float x = positionPart.getX();
        float y = positionPart.getY();
        float radians = positionPart.getRadians();
        float radius = entity.getRadius();

        float angle = 0;

        for (int i = 0; i < numPoints; i++) {
            shapex[i] = x + (float) Math.cos(angle + radians) * radius;
            shapey[i] = y + (float) Math.sin(angle + radians) * radius;
            angle += 2 * 3.1415f / numPoints;
        }

        entity.setShapeX(shapex);
        entity.setShapeY(shapey);
    }
}
