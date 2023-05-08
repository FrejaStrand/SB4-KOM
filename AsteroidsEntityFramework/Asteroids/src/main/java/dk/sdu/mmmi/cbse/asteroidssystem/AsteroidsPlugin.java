/**
 * This class is responsible for creating the asteroids.
 */
package dk.sdu.mmmi.cbse.asteroidssystem;

import dk.sdu.mmmi.cbse.common.asteroids.Asteroids;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.LifePart;
import dk.sdu.mmmi.cbse.common.data.entityparts.MovingPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;

import java.util.Random;

public class AsteroidsPlugin implements IGamePluginService {
    private Entity asteriod;
    private int xAxis;

    public AsteroidsPlugin() {
    }

    /**
     * This method is responsible for starting the plugin.
     * pre-condition: The game data and world must not be null.
     * post-condition: The asteroids are created and added to the world.
     *
     * @param gameData The game data object to use for the plugin.
     * @param world    The world object to use for the plugin.
     */
    @Override
    public void start(GameData gameData, World world) {
        xAxis = 0;

        for (int i = 0; i < 10; i++) {
            // Add entities to the world
            asteriod = createAsteroidShip(gameData);
            world.addEntity(asteriod);
            xAxis += 90;
        }
    }

    /**
     * This method is responsible for creating the asteroids.
     * pre-condition: New asteroids must be created. The game data must not be null.
     * post-condition: The entity of the asteroid is returned.
     *
     * @param gameData The game data object to use for the plugin.
     * @return The entity of the asteroid.
     */
    private Entity createAsteroidShip(GameData gameData) {

        Random random = new Random();

        float deacceleration = 10;
        float acceleration = 100;
        float maxSpeed = 100;
        float rotationSpeed = 5;
        float x = gameData.getDisplayWidth() / 2 - xAxis;
        float y = gameData.getDisplayHeight() / 2 - random.nextFloat();
        float radians = 3.1415f / 2;

        Entity asteroids = new Asteroids();
        asteroids.setRadius(20);
        asteroids.add(new MovingPart(deacceleration, acceleration, maxSpeed, rotationSpeed));
        asteroids.add(new PositionPart(x, y, radians));
        asteroids.add(new LifePart(3));

        return asteroids;
    }

    /**
     * This method is responsible for stopping the plugin.
     * pre-condition: Stop the plugin. The game data and world must not be null.
     * post-condition: The asteroids are removed from the world.
     *
     * @param gameData The game data object to use for the plugin.
     * @param world    The world object to use for the plugin.
     */
    @Override
    public void stop(GameData gameData, World world) {
        // Remove entities
        for (int i = 0; i < 10; i++) {
            world.removeEntity(asteriod);
        }
    }
}
