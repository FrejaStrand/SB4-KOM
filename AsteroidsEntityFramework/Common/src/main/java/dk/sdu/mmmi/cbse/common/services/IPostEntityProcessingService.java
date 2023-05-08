package dk.sdu.mmmi.cbse.common.services;

import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;

/**
 * @author jcs
 */

/**
 * The IPostEntityProcessingService interface provides a method for processing game state after
 * entities have been updated.
 */
public interface IPostEntityProcessingService {
    /**
     * Processes game state after entities have been updated.
     * Pre-condition: A game tick has occurred and all entities have been updated.
     * Post-condition: The game state is updated.
     *
     * @param gameData The current game state.
     * @param world The game world containing all entities.
     */
    void process(GameData gameData, World world);
}
