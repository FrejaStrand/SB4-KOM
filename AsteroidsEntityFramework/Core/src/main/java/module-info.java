module Core {
    requires Common;
    requires CommonBullet;
    requires CommonAsteroids;
    requires com.badlogic.gdx;
    requires Enemy;
    requires spring.context;
    uses dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
    uses dk.sdu.mmmi.cbse.common.services.IGamePluginService;
    uses dk.sdu.mmmi.cbse.common.services.IPostEntityProcessingService;
}