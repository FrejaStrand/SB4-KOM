/**
 * This class is responsible for detecting collisions.
 * It implements the IPostEntityProcessingService interface.
 *
 */
package dk.sdu.mmmi.cbse.collisionsystem;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.LifePart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.services.IPostEntityProcessingService;

public class CollisionDetector implements IPostEntityProcessingService {

    /**
     * This method is responsible for detecting collisions.
     * Pre-condition: The game is running and a game world must exist. The game data and world must not be null.
     * Post-condition: The game plugin is started and the game data and world are updated.
     *
     * @param gameData The game data object to use for the plugin.
     * @param world    The world object to use for the plugin.
     */
    @Override
    public void process(GameData gameData, World world) {
        for(Entity entity : world.getEntities()) {
            for (Entity collisionDetection : world.getEntities()) {
                LifePart entityLife = entity.getPart(LifePart.class);

                if (entity.getID().equals(collisionDetection.getID())) {
                    continue;
                }

                if (this.collides(entity, collisionDetection)) {
                    if (entityLife.getLife() > 0) {
                        entityLife.setLife(entityLife.getLife() - 1);
                        entityLife.setIsHit(true);

                        if (entityLife.getLife() <= 0) {
                            world.removeEntity(entity);
                        }
                    }
                }
            }
        }
    }

    /**
     * This method is responsible for detecting collisions.
     *
     * @param entity The entity to check for collisions.
     * @param entity2 The entity to check for collisions.
     * @return Returns true if the entities collide, otherwise false.
     */
    public Boolean collides(Entity entity, Entity entity2) {
        PositionPart entityMove = entity.getPart(PositionPart.class);
        PositionPart entityMove2 = entity2.getPart(PositionPart.class);
        float dx = entityMove.getX() - entityMove2.getX();
        float dy = entityMove.getY() - entityMove2.getY();
        float distance = (float) Math.sqrt(dx*dx+dy*dy);
        return distance < (entity.getRadius() + entity2.getRadius());
    }
}
