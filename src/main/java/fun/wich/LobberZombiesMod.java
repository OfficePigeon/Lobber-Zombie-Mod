package fun.wich;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.entity.*;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.world.Heightmap;
import net.minecraft.world.biome.Biome;

import java.util.function.Function;

public class LobberZombiesMod implements ModInitializer {
	public static final String MOD_ID = "wich";
	public static final SoundEvent ENTITY_LOBBER_ZOMBIE_AMBIENT = register("entity.lobber_zombie.ambient");
	public static final SoundEvent ENTITY_LOBBER_ZOMBIE_DEATH = register("entity.lobber_zombie.death");
	public static final SoundEvent ENTITY_LOBBER_ZOMBIE_HURT = register("entity.lobber_zombie.hurt");
	public static final SoundEvent ENTITY_LOBBER_ZOMBIE_STEP = register("entity.lobber_zombie.step");
	public static final SoundEvent ENTITY_LOBBER_ZOMBIE_THROW = register("entity.lobber_zombie.throw");
	public static final SoundEvent ENTITY_PARROT_IMITATE_LOBBER_ZOMBIE = register("entity.parrot.imitate.lobber_zombie");
	private static SoundEvent register(String path) {
		Identifier id = Identifier.of(MOD_ID, path);
		return Registry.register(Registries.SOUND_EVENT, id, SoundEvent.of(id));
	}
	public static final TagKey<Biome> TAG_SPAWNS_LOBBER_ZOMBIES = TagKey.of(RegistryKeys.BIOME, Identifier.of(MOD_ID, "spawns_lobber_zombies"));
	public static final EntityType<LobberZombieEntity> LOBBER_ZOMBIE = register(
			"lobber_zombie",
			EntityType.Builder.create(LobberZombieEntity::new, SpawnGroup.MONSTER)
					.dimensions(0.6F, 1.99F)
					.eyeHeight(1.74F)
					.vehicleAttachment(-0.7F)
					.maxTrackingRange(8)
					.notAllowedInPeaceful()
	);
	public static final EntityType<LobberZombieThrownFleshEntity> LOBBER_ZOMBIE_THROWN_FLESH = register(
			"lobber_zombie_thrown_flesh",
			EntityType.Builder.<LobberZombieThrownFleshEntity>create(LobberZombieThrownFleshEntity::new, SpawnGroup.MISC)
					.dropsNothing()
					.dimensions(0.25F, 0.25F)
					.maxTrackingRange(4)
					.trackingTickInterval(10)
	);
	private static <T extends Entity> EntityType<T> register(String name, EntityType.Builder<T> type) {
		RegistryKey<EntityType<?>> key = RegistryKey.of(RegistryKeys.ENTITY_TYPE, Identifier.of(MOD_ID, name));
		EntityType<T> entityType = type.build(key);
		Registry.register(Registries.ENTITY_TYPE, key, entityType);
		return entityType;
	}
	public static final Item LOBBER_ZOMBIE_SPAWN_EGG = register("lobber_zombie_spawn_egg", SpawnEggItem::new, new Item.Settings().spawnEgg(LOBBER_ZOMBIE));
	public static Item register(String name, Function<Item.Settings, Item> itemFactory, Item.Settings settings) {
		RegistryKey<Item> key = RegistryKey.of(RegistryKeys.ITEM, Identifier.of(MOD_ID, name));
		Item item = itemFactory.apply(settings.registryKey(key));
		Registry.register(Registries.ITEM, key, item);
		return item;
	}

	@Override
	public void onInitialize() {
		//Attributes
		FabricDefaultAttributeRegistry.register(LOBBER_ZOMBIE, LobberZombieEntity.createZombieAttributes());
		//Spawning
		SpawnRestriction.register(LOBBER_ZOMBIE, SpawnLocationTypes.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, LobberZombieEntity::canSpawnInDark);
		BiomeModifications.addSpawn(BiomeSelectors.tag(TAG_SPAWNS_LOBBER_ZOMBIES),
				SpawnGroup.MONSTER, LOBBER_ZOMBIE, 20, 1, 4);
		//Items
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.SPAWN_EGGS).register(itemGroup -> itemGroup.add(LOBBER_ZOMBIE_SPAWN_EGG));
	}
}