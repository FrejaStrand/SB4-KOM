import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import dk.sdu.mmmi.cbse.common.data.bullet.IBulletSPI;

module Bullets {
    requires Common;
    requires CommonBullet;
    provides IEntityProcessingService with dk.sdu.mmmi.cbse.bulletssystem.BulletsControlSystem;
    provides IGamePluginService with dk.sdu.mmmi.cbse.bulletssystem.BulletsPlugin;
    provides IBulletSPI with dk.sdu.mmmi.cbse.bulletssystem.BulletsControlSystem;
}