/**
 * This interface is used to create a new bullet when a bullet is fired.
 */
package dk.sdu.mmmi.cbse.common.data.bullet;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;

public interface IBulletSPI {

    /**
     * This method is responsible for creating a new bullet when a bullet is fired.
     * Pre-condition: A bullet must be fired.
     * Post-condition: A new bullet is created.
     * @param entity The entity to create the new bullet from.
     * @param gameData The game data to create the new bullet in.
     * @return The new bullet.
     */
    Entity createBullet(Entity entity, GameData gameData);
}
