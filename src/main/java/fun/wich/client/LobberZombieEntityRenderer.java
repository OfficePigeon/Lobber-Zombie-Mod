package fun.wich.client;

import fun.wich.LobberZombieEntity;
import fun.wich.LobberZombiesMod;
import net.minecraft.client.render.command.OrderedRenderCommandQueue;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.ZombieBaseEntityRenderer;
import net.minecraft.client.render.entity.feature.HeldItemFeatureRenderer;
import net.minecraft.client.render.entity.model.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class LobberZombieEntityRenderer extends ZombieBaseEntityRenderer<LobberZombieEntity, LobberZombieEntityRenderState, ZombieEntityModel<LobberZombieEntityRenderState>> {
	private static final Identifier TEXTURE = Identifier.of(LobberZombiesMod.MOD_ID, "textures/entity/zombie/lobber.png");
	public LobberZombieEntityRenderer(EntityRendererFactory.Context context) {
		super(context, new LobberZombieEntityModel(context.getPart(LobberZombiesClient.LOBBER_ZOMBIE)), new LobberZombieEntityModel(context.getPart(LobberZombiesClient.LOBBER_ZOMBIE_BABY)), EquipmentModelData.mapToEntityModel(EntityModelLayers.ZOMBIE_EQUIPMENT, context.getEntityModels(), ZombieEntityModel<LobberZombieEntityRenderState>::new), EquipmentModelData.mapToEntityModel(EntityModelLayers.ZOMBIE_BABY_EQUIPMENT, context.getEntityModels(), ZombieEntityModel<LobberZombieEntityRenderState>::new));
		this.features.removeIf(feature -> feature instanceof HeldItemFeatureRenderer);
		this.addFeature(new HeldItemFeatureRenderer<>(this) {
			public void render(MatrixStack matrixStack, OrderedRenderCommandQueue orderedRenderCommandQueue, int i, LobberZombieEntityRenderState state, float headYaw, float headPitch) {
				if (state.attackingRanged) super.render(matrixStack, orderedRenderCommandQueue, i, state, headYaw, headPitch);
			}
		});
	}
	@Override public LobberZombieEntityRenderState createRenderState() { return new LobberZombieEntityRenderState(); }
	@Override
	public void updateRenderState(LobberZombieEntity entity, LobberZombieEntityRenderState state, float f) {
		super.updateRenderState(entity, state, f);
		state.attackingRanged = entity.IsAttackingRanged();
	}
	@Override public Identifier getTexture(LobberZombieEntityRenderState state) { return TEXTURE; }
}