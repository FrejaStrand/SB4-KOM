package dk.sdu.mmmi.cbse.main;

import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import dk.sdu.mmmi.cbse.common.services.IPostEntityProcessingService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.ServiceLoader;


@Configuration
public class ModuleConfiguration {

    /**
     * This method is responsible for returning the game data.
     * @return The game data.
     */
    @Bean
    public Game game() {return new Game(gamePluginServices(), entityPluginServices(), postEntityServices());}

    /**
     * This method is responsible for returning the game plugin services.
     * @return The game plugin services.
     */
    @Bean
    public List<IGamePluginService> gamePluginServices() {
        return ServiceLoader.load(IGamePluginService.class).stream().map(ServiceLoader.Provider::get).collect(java.util.stream.Collectors.toList());
    }

    /**
     * This method is responsible for returning the entity plugin services.
     * @return The entity plugin services.
     */
    @Bean
    public List<IEntityProcessingService> entityPluginServices() {
        return ServiceLoader.load(IEntityProcessingService.class).stream().map(ServiceLoader.Provider::get).collect(java.util.stream.Collectors.toList());
    }

    /**
     * This method is responsible for returning the post entity processing services.
     * @return The post entity processing services.
     */
    @Bean
    public List<IPostEntityProcessingService> postEntityServices() {
        return ServiceLoader.load(IPostEntityProcessingService.class).stream().map(ServiceLoader.Provider::get).collect(java.util.stream.Collectors.toList());
    }

}
