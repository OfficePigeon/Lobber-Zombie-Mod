package fun.wich.client;

import fun.wich.LobberZombieEntity;
import fun.wich.LobberZombiesMod;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.ZombieBaseEntityRenderer;
import net.minecraft.client.render.entity.feature.HeldItemFeatureRenderer;
import net.minecraft.client.render.entity.model.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class LobberZombieEntityRenderer extends ZombieBaseEntityRenderer<LobberZombieEntity, ZombieEntityModel<LobberZombieEntity>> {
	private static final Identifier TEXTURE = Identifier.of(LobberZombiesMod.MOD_ID, "textures/entity/zombie/lobber.png");
	public LobberZombieEntityRenderer(EntityRendererFactory.Context context) {
		super(context, new LobberZombieEntityModel(context.getPart(LobberZombiesClient.LOBBER_ZOMBIE)), new ZombieEntityModel<>(context.getPart(EntityModelLayers.ZOMBIE_INNER_ARMOR)), new ZombieEntityModel<>(context.getPart(EntityModelLayers.ZOMBIE_OUTER_ARMOR)));
		this.features.removeIf(feature -> feature instanceof HeldItemFeatureRenderer);
		this.addFeature(new HeldItemFeatureRenderer<>(this, context.getHeldItemRenderer()) {
			@Override
			public void render(MatrixStack matrixStack, VertexConsumerProvider orderedRenderCommandQueue, int light, LobberZombieEntity state, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
				if (state.IsAttackingRanged()) super.render(matrixStack, orderedRenderCommandQueue, light, state, limbAngle, limbDistance, tickDelta, animationProgress, headYaw, headPitch);
			}
		});
	}
	@Override public Identifier getTexture(LobberZombieEntity state) { return TEXTURE; }
}