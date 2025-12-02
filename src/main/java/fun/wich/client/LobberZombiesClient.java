package fun.wich.client;

import fun.wich.LobberZombiesMod;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class LobberZombiesClient implements ClientModInitializer {
	public static final EntityModelLayer LOBBER_ZOMBIE = new EntityModelLayer(Identifier.of(LobberZombiesMod.MOD_ID, "lobber_zombie"), "main");
	@Override
	public void onInitializeClient() {
		EntityModelLayerRegistry.registerModelLayer(LOBBER_ZOMBIE, LobberZombieEntityModel::getTexturedModelData);
		EntityRendererRegistry.register(LobberZombiesMod.LOBBER_ZOMBIE, LobberZombieEntityRenderer::new);
		EntityRendererRegistry.register(LobberZombiesMod.LOBBER_ZOMBIE_THROWN_FLESH, FlyingItemEntityRenderer::new);
	}
}
