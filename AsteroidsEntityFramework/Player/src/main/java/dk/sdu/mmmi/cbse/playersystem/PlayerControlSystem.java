/**
 * This class is responsible for controlling the player.
 * It handles the movement of the player and the shooting of the player.
 * It also updates the shape of the player.
 */
package dk.sdu.mmmi.cbse.playersystem;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;

import static dk.sdu.mmmi.cbse.common.data.GameKeys.LEFT;
import static dk.sdu.mmmi.cbse.common.data.GameKeys.RIGHT;
import static dk.sdu.mmmi.cbse.common.data.GameKeys.UP;
import static java.util.stream.Collectors.toList;

import dk.sdu.mmmi.cbse.common.data.GameKeys;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.LifePart;
import dk.sdu.mmmi.cbse.common.data.entityparts.MovingPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.data.bullet.IBulletSPI;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import dk.sdu.mmmi.cbse.common.util.SPILocator;

import java.util.ServiceLoader;
import java.util.Collection;

/**
 * @author jcs
 */
public class PlayerControlSystem implements IEntityProcessingService {
    private long lastShotTime;

    /**
     * This method is responsible for processing the player.
     * Pre-condition: A game world must exist and the player must be created.
     * Post-condition: The player is processed.
     *
     * @param gameData The current game state.
     * @param world    The game world containing all entities.
     */
    @Override
    public void process(GameData gameData, World world) {

        for (Entity player : world.getEntities(Player.class)) {
            PositionPart positionPart = player.getPart(PositionPart.class);
            MovingPart movingPart = player.getPart(MovingPart.class);
            LifePart lifePart = player.getPart(LifePart.class);

            movingPart.setLeft(gameData.getKeys().isDown(LEFT));
            movingPart.setRight(gameData.getKeys().isDown(RIGHT));
            movingPart.setUp(gameData.getKeys().isDown(UP));

            if (gameData.getKeys().isDown(GameKeys.SPACE)) {
                long currentTime = System.currentTimeMillis();
                if (currentTime - lastShotTime > 300) {
                    for (IBulletSPI bullet : getBulletSPIs()) {
                        world.addEntity(bullet.createBullet(player, gameData));
                    }
                    lastShotTime = currentTime;
                }
            }

            movingPart.process(gameData, player);
            positionPart.process(gameData, player);
            lifePart.process(gameData, player);

            updateShape(player);
        }
    }

    /**
     * This method is responsible for updating the shape of the player.
     * Pre-condition: A player entity must exist.
     * Post-condition: The shape of the player is updated.
     *
     * @param entity The player entity.
     */
    private void updateShape(Entity entity) {
        float[] shapex = entity.getShapeX();
        float[] shapey = entity.getShapeY();
        PositionPart positionPart = entity.getPart(PositionPart.class);
        float x = positionPart.getX();
        float y = positionPart.getY();
        float radians = positionPart.getRadians();
        float size = 10;

        shapex[0] = (float) (x + Math.cos(radians) * 20);
        shapey[0] = (float) (y + Math.sin(radians) * 20);

        shapex[1] = (float) (x + Math.cos(radians + Math.PI / 3) * size);
        shapey[1] = (float) (y + Math.sin(radians + Math.PI / 3) * size);

        shapex[2] = (float) (x + Math.cos(radians + 2 * Math.PI / 3) * size);
        shapey[2] = (float) (y + Math.sin(radians + 2 * Math.PI / 3) * size);

        shapex[3] = (float) (x + Math.cos(radians + Math.PI) * size);
        shapey[3] = (float) (y + Math.sin(radians + Math.PI) * size);

        shapex[4] = (float) (x + Math.cos(radians + 4 * Math.PI / 3) * size);
        shapey[4] = (float) (y + Math.sin(radians + 4 * Math.PI / 3) * size);

        shapex[5] = (float) (x + Math.cos(radians + 5 * Math.PI / 3) * size);
        shapey[5] = (float) (y + Math.sin(radians + 5 * Math.PI / 3) * size);

        entity.setShapeX(shapex);
        entity.setShapeY(shapey);
    }

    /**
     * This method is responsible for getting all the bullet SPIs.
     * A bullet SPI is a service provider interface for bullets.
     * Post-condition: A collection of all the bullet SPIs is returned.
     * @return A collection of all the bullet SPIs.
     */
    private Collection<? extends IBulletSPI> getBulletSPIs() {
        return ServiceLoader.load(IBulletSPI.class).stream().map(ServiceLoader.Provider::get).collect(toList());
    }

}
