module Core {
    exports dk.sdu.mmmi.cbse.main;
    opens dk.sdu.mmmi.cbse.main to spring.core;
    requires Common;
    requires CommonBullet;
    requires CommonAsteroids;
    requires com.badlogic.gdx;
    requires spring.context;
    requires spring.beans;
    uses dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
    uses dk.sdu.mmmi.cbse.common.services.IGamePluginService;
    uses dk.sdu.mmmi.cbse.common.services.IPostEntityProcessingService;
}