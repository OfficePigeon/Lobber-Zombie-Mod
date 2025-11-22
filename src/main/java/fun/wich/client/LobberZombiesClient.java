package fun.wich.client;

import fun.wich.LobberZombiesMod;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.minecraft.client.render.entity.EntityRendererFactories;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class LobberZombiesClient implements ClientModInitializer {
	public static final EntityModelLayer LOBBER_ZOMBIE = MakeModelLayer("lobber_zombie");
	public static final EntityModelLayer LOBBER_ZOMBIE_BABY = MakeModelLayer("lobber_zombie_baby");
	private static EntityModelLayer MakeModelLayer(String path) {
		return new EntityModelLayer(Identifier.of(LobberZombiesMod.MOD_ID, path), "main");
	}
	@Override
	public void onInitializeClient() {
		EntityModelLayerRegistry.registerModelLayer(LOBBER_ZOMBIE, LobberZombieEntityModel::getTexturedModelData);
		EntityModelLayerRegistry.registerModelLayer(LOBBER_ZOMBIE_BABY, () -> LobberZombieEntityModel.getTexturedModelData().transform(BipedEntityModel.BABY_TRANSFORMER));
		EntityRendererFactories.register(LobberZombiesMod.LOBBER_ZOMBIE, LobberZombieEntityRenderer::new);
		EntityRendererFactories.register(LobberZombiesMod.LOBBER_ZOMBIE_THROWN_FLESH, FlyingItemEntityRenderer::new);
	}
}
