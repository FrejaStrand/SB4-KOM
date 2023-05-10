/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.common.data.entityparts;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;

/**
 * @author Alexander
 */
public interface EntityPart {
    /**
     * This method is responsible for processing the entity.
     * Pre-condition: The entity must exist and the game state must exist.
     * Post-condition: The entity is processed.
     * @param gameData The current game state.
     * @param entity The entity to be processed.
     */
    void process(GameData gameData, Entity entity);
}
