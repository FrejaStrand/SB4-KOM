package dk.sdu.mmmi.cbse.common.services;

import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;

/**
 * The IEntityProcessingService interface provides a method for processing entities in a game world.
 */
public interface IEntityProcessingService {

    /**
     * Processes all entities in the game world.
     * Pre-condition: A game world must exist.
     * Post-condition: All entities in the game world are processed.
     *
     * @param gameData The current game state.
     * @param world    The game world containing all entities.
     */
    void process(GameData gameData, World world);
}
