/**
 * This interface is used to create a new asteroid when an asteroid is destroyed.
 */
package dk.sdu.mmmi.cbse.common.asteroids;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.World;

public interface IAsteroidSplitter {

    /**
     * This method is responsible for creating a new asteroid when an asteroid is destroyed.
     * Pre-condition: An asteroid must be destroyed.
     * Post-condition: A new asteroid is created.
     * @param entity The entity to create a new asteroid from.
     * @param world The world to create the new asteroid in.
     */
    void createSplitAsteroid(Entity entity, World world);
}
