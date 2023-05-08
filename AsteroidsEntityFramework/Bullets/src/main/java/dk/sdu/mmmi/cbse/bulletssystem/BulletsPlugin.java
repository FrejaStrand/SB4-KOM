/**
 * This class is responsible for creating the bullets.
 * It implements the IGamePluginService interface.
 */
package dk.sdu.mmmi.cbse.bulletssystem;

import dk.sdu.mmmi.cbse.common.data.bullet.Bullet;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;

public class BulletsPlugin implements IGamePluginService {

    /**
     * This method is responsible for starting the plugin.
     * Pre-condition: The game is running and a game world must exist. The game data and world must not be null.
     * Post-condition: The game plugin is started and the game data and world are updated.
     *
     * @param gameData The game data object to use for the plugin.
     * @param world    The world object to use for the plugin.
     */
    @Override
    public void start(GameData gameData, World world) {
    }

    /**
     * This method is responsible for removing the bullets.
     * Pre-condition: The game is running and a game world must exist. The game data and world must not be null.
     * Post-condition: The game plugin is stopped and the game data and world are updated so the entities are removed.
     *
     * @param gameData The game data object to use for the plugin.
     * @param world    The world object to use for the plugin.
     */
    @Override
    public void stop(GameData gameData, World world) {
        for (Entity bullet : world.getEntities()) {
            if (bullet.getClass() == Bullet.class) {
                world.removeEntity(bullet);
            }
        }
    }
}
