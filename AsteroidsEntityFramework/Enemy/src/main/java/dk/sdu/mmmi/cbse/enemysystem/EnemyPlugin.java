/**
 * This class is responsible for creating the enemy entity.
 */
package dk.sdu.mmmi.cbse.enemysystem;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.LifePart;
import dk.sdu.mmmi.cbse.common.data.entityparts.MovingPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;

import java.util.Random;

public class EnemyPlugin implements IGamePluginService {
    private Entity enemy;

    public EnemyPlugin() {
    }

    /**
     * This method is responsible for starting the plugin.
     * Pre-condition: The game data and world must not be null.
     * Post-condition: The enemy is created and added to the world.
     * @param gameData The game data object to use for the plugin.
     * @param world    The world object to use for the plugin.
     */
    @Override
    public void start(GameData gameData, World world) {

        // Add entities to the world
        enemy = createEnemyShip(gameData);
        world.addEntity(enemy);
    }

    /**
     * This method is responsible for creating the enemy entity.
     * Pre-condition: New enemy must be created. The game data must not be null.
     * Post-condition: The entity of the enemy is returned.
     *
     * @param gameData The game data object to use for the plugin.
     * @return        The enemy entity.
     */
    private Entity createEnemyShip(GameData gameData) {

        Random random = new Random();

        float deacceleration = 10;
        float acceleration = 200;
        float maxSpeed = 100;
        float rotationSpeed = 5;
        float x = gameData.getDisplayWidth() - random.nextFloat();
        float y = gameData.getDisplayHeight() - random.nextFloat();
        float radians = 3.1415f / 2;

        Entity enemyShip = new Enemy();
        enemyShip.setRadius(10);
        enemyShip.add(new MovingPart(deacceleration, acceleration, maxSpeed, rotationSpeed));
        enemyShip.add(new PositionPart(x, y, radians));
        enemyShip.add(new LifePart(5));

        return enemyShip;
    }

    /**
     * This method is responsible for removing the entities from the world.
     * Pre-condition: The game data and world must not be null.
     * Post-condition: The entities are removed from the world.
     *
     * @param gameData The game data object to use for the plugin.
     * @param world    The world object to use for the plugin.
     */
    @Override
    public void stop(GameData gameData, World world) {
        // Remove entities
        world.removeEntity(enemy);
    }
}
