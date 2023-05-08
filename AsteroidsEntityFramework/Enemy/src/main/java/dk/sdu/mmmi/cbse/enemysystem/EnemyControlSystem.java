/**
 * This class is responsible for processing the enemies.
 */
package dk.sdu.mmmi.cbse.enemysystem;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.LifePart;
import dk.sdu.mmmi.cbse.common.data.entityparts.MovingPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.data.bullet.IBulletSPI;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;

import java.util.ServiceLoader;
import java.util.Collection;

import java.util.Random;

import static java.util.stream.Collectors.toList;

public class EnemyControlSystem implements IEntityProcessingService {
    private long lastShotTime;

    /**
     * This method is responsible for processing the enemies.
     * Pre-condition: A game world must exist and the enemies must be created.
     * Post-condition: The enemies are processed.
     *
     * @param gameData The current game state.
     * @param world    The game world containing all entities.
     */
    @Override
    public void process(GameData gameData, World world) {

        for (Entity enemy : world.getEntities(Enemy.class)) {
            PositionPart positionPart = enemy.getPart(PositionPart.class);
            MovingPart movingPart = enemy.getPart(MovingPart.class);
            LifePart lifePart = enemy.getPart(LifePart.class);

            Random random = new Random();
            movingPart.setLeft(random.nextBoolean());
            movingPart.setRight(random.nextBoolean());
            movingPart.setUp(random.nextBoolean());

            long currentTime = System.currentTimeMillis();
            if (currentTime - lastShotTime > 360) {
                for (IBulletSPI bullet : getBulletSPIs()) {
                    world.addEntity(bullet.createBullet(enemy, gameData));
                }
                lastShotTime = currentTime;
            }


            movingPart.process(gameData, enemy);
            positionPart.process(gameData, enemy);
            lifePart.process(gameData, enemy);

            updateShape(enemy);
        }
    }

    /**
     * This method is responsible for updating the shape of the enemy.
     * Pre-condition: An enemy must exist.
     * Post-condition: The shape of the enemy is updated.
     * @param entity   The entity to be updated.
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
     * Pre-condition: The bullet SPIs must be created.
     * Post-condition: All the bullet SPIs are returned.
     * @return A collection of all the bullet SPIs.
     */
    private Collection<? extends IBulletSPI> getBulletSPIs() {
        return ServiceLoader.load(IBulletSPI.class).stream().map(ServiceLoader.Provider::get).collect(toList());
    }
}
