package dk.sdu.mmmi.cbse.common.services;

import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;

/**
 * The IGamePluginService interface provides methods for starting and stopping game plugins.
 */
public interface IGamePluginService {

    /**
     * Starts a game plugin with the given game data and world.
     * Pre-condition: The game is running and a game world must exist. The game data and world must not be null.
     * Post-condition: The game plugin is started and the game data and world are updated.
     *
     * @param gameData The game data object to use for the plugin.
     * @param world    The world object to use for the plugin.
     */
    void start(GameData gameData, World world);

    /**
     * Stops a game plugin with the given game data and world.
     * Pre-condition: The game is running and a game world must exist. The game data and world must not be null.
     * Post-condition: The game plugin is stopped and the game data and world are updated so the entities are removed.
     *
     * @param gameData The game data object to use for the plugin.
     * @param world    The world object to use for the plugin.
     */
    void stop(GameData gameData, World world);
}
