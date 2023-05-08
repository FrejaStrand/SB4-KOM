/**
 * This class is responsible for the game loop.
 * It is also responsible for creating the game plugins and processing the entities.
 * It is also responsible for rendering the entities.
 * It is also responsible for updating the game data.
 * It is also responsible for updating the game world.
 * It is also responsible for updating the game camera.
 * It is also responsible for updating the game input processor.
 * It is also responsible for updating the game shape renderer.
 */
package dk.sdu.mmmi.cbse.main;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import dk.sdu.mmmi.cbse.common.asteroids.Asteroids;
import dk.sdu.mmmi.cbse.common.data.bullet.Bullet;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import dk.sdu.mmmi.cbse.common.services.IPostEntityProcessingService;

import java.util.ServiceLoader;
import java.util.Collection;

import dk.sdu.mmmi.cbse.enemysystem.Enemy;
import dk.sdu.mmmi.cbse.managers.GameInputProcessor;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class Game implements ApplicationListener {

    private static OrthographicCamera cam;
    private ShapeRenderer sr;

    private final GameData gameData = new GameData();
    private List<IEntityProcessingService> entityProcessors = new ArrayList<>();
    private List<IGamePluginService> entityPlugins = new ArrayList<>();
    private World world = new World();

    /**
     * This method is responsible for returning the game data.
     * @param gamePluginServices The game plugin services to set.
     * @param entityPluginServices The entity plugin services to set.
     * @param postEntityServices The post entity services to set.
     */
    public Game(List<IGamePluginService> gamePluginServices, List<IEntityProcessingService> entityPluginServices, List<IPostEntityProcessingService> postEntityServices) {}

    @Override
    public void create() {

        gameData.setDisplayWidth(Gdx.graphics.getWidth());
        gameData.setDisplayHeight(Gdx.graphics.getHeight());

        cam = new OrthographicCamera(gameData.getDisplayWidth(), gameData.getDisplayHeight());
        cam.translate(gameData.getDisplayWidth() / 2, gameData.getDisplayHeight() / 2);
        cam.update();

        sr = new ShapeRenderer();

        Gdx.input.setInputProcessor(
                new GameInputProcessor(gameData)
        );

        // Lookup all Game Plugins using ServiceLoader
        for (IGamePluginService iGamePlugin : getPluginServices()) {
            iGamePlugin.start(gameData, world);
        }
    }

    /**
     * This method is responsible for rendering the entities.
     * It is also responsible for updating the game data.
     */
    @Override
    public void render() {

        // clear screen to black
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        gameData.setDelta(Gdx.graphics.getDeltaTime());

        update();

        draw();

        gameData.getKeys().update();
    }

    /**
     * This method is responsible for updating the game camera.
     */
    private void update() {
        // Update
        for (IEntityProcessingService entityProcessorService : getEntityProcessingServices()) {
            entityProcessorService.process(gameData, world);
        }
        for (IPostEntityProcessingService postEntityProcessingService : getPostEntityProcessingServices()) {
            postEntityProcessingService.process(gameData, world);

        }
    }

    /**
     * This method is responsible for getting all the entity processing services.
     */
    private void draw() {
        for (Entity entity : world.getEntities()) {
            if (entity instanceof Enemy) {
                sr.setColor(0, 1, 0, 0);
            } else if (entity instanceof Asteroids) {
                sr.setColor(164 / 255F, 87 / 255F, 41 / 255F, 1);
            } else if (entity instanceof Bullet) {
                sr.setColor(1, 215 / 225F, 0, 1);
            } else {
                sr.setColor(1, 1, 1, 1);
            }

            sr.begin(ShapeRenderer.ShapeType.Line);

            float[] shapex = entity.getShapeX();
            float[] shapey = entity.getShapeY();

            for (int i = 0, j = shapex.length - 1;
                 i < shapex.length;
                 j = i++) {

                sr.line(shapex[i], shapey[i], shapex[j], shapey[j]);
            }

            sr.end();
        }
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
    }

    /**
     * This method is responsible for getting all the game plugins.
     * @return A collection of game plugins.
     */
    private Collection<? extends IGamePluginService> getPluginServices() {
        return ServiceLoader.load(IGamePluginService.class).stream().map(ServiceLoader.Provider::get).collect(toList());
    }

    /**
     * This method is responsible for getting all the entity processing services.
     * @return A collection of entity processing services.
     */

    private Collection<? extends IEntityProcessingService> getEntityProcessingServices() {
        return ServiceLoader.load(IEntityProcessingService.class).stream().map(ServiceLoader.Provider::get).collect(toList());
    }

    /**
     * This method is responsible for getting all the post entity processing services.
     * @return A collection of post entity processing services.
     */
    private Collection<? extends IPostEntityProcessingService> getPostEntityProcessingServices() {
        return ServiceLoader.load(IPostEntityProcessingService.class).stream().map(ServiceLoader.Provider::get).collect(toList());
    }
}
