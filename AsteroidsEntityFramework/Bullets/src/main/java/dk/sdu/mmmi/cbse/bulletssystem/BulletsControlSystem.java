/**
 * This class is responsible for controlling the bullets.
 * It implements the IEntityProcessingService interface.
 * It implements the IBulletSPI interface.
 */
package dk.sdu.mmmi.cbse.bulletssystem;

import dk.sdu.mmmi.cbse.common.data.bullet.Bullet;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.LifePart;
import dk.sdu.mmmi.cbse.common.data.entityparts.MovingPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.TimerPart;
import dk.sdu.mmmi.cbse.common.data.bullet.IBulletSPI;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

public class BulletsControlSystem implements IEntityProcessingService, IBulletSPI {

    /**
     * This method is responsible for processing the bullets.
     * Pre-condition: A game world must exist and the bullets must be created.
     * Post-condition: The bullets are processed.
     *
     * @param gameData The current game state.
     * @param world    The game world containing all entities.
     */
    @Override
    public void process(GameData gameData, World world) {
        for (Entity bullet : world.getEntities(Bullet.class)) {
            PositionPart positionPart = bullet.getPart(PositionPart.class);
            MovingPart movingPart = bullet.getPart(MovingPart.class);
            TimerPart timerPart = bullet.getPart(TimerPart.class);

            movingPart.setUp(true);
            if (timerPart.getExpiration() < 0) {
                world.removeEntity(bullet);
            }

            timerPart.process(gameData, bullet);
            movingPart.process(gameData, bullet);
            positionPart.process(gameData, bullet);

            setShape(bullet);
        }
    }

    /**
     * This method is responsible for creating the bullet.
     * Pre-condition: A player entity must exist and the game state must exist.
     * Post-condition: A bullet is created.
     *
     * @param player   The player entity.
     * @param gameData The current game state.
     * @return The entity of the bullet.
     */
    @Override
    public Entity createBullet(Entity player, GameData gameData) {
        PositionPart playerPos = player.getPart(PositionPart.class);

        float x = playerPos.getX();
        float y = playerPos.getY();
        float radians = playerPos.getRadians();
        float speedBullet = 450; //Pixel pr. sec

        Entity bullet = new Bullet(); // Creates a new entity(obejct) of bullet
        bullet.setRadius(2);

        //The little gap between my player and the actual bullet shot
        float gap = player.getRadius() * 1.5f;
        float bx = (float) cos(radians) * player.getRadius() * bullet.getRadius();
        float by = (float) sin(radians) * player.getRadius() * bullet.getRadius();

        bullet.add(new PositionPart(bx + x, by + y, radians));
        bullet.add(new LifePart(1));
        bullet.add(new MovingPart(0, 5000000, speedBullet, 5));
        bullet.add(new TimerPart(1));


        return bullet;
    }

    /**
     * This method is responsible for setting the shape of the bullet.
     * Pre-condition: The entity of the bullet must exist.
     * Post-condition: The shape of the bullet is set.
     *
     * @param entity The entity of the bullet.
     */
    private void setShape(Entity entity) {
        float[] shapex = entity.getShapeX();
        float[] shapey = entity.getShapeY();
        PositionPart positionPart = entity.getPart(PositionPart.class);
        float x = positionPart.getX();
        float y = positionPart.getY();
        float radians = positionPart.getRadians();
        float radius = 3;

        for (int i = 0; i < 6; i++) {
            shapex[i] = (float) (x + radius * Math.cos(radians + i * Math.PI / 3));
            shapey[i] = (float) (y + radius * Math.sin(radians + i * Math.PI / 3));
        }

        entity.setShapeX(shapex);
        entity.setShapeY(shapey);

    }
}
