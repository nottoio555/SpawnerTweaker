package spawnertweaker;

import net.fabricmc.api.ModInitializer;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.MobSpawnerBlockEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;

public class SpawnerTweaker implements ModInitializer {
    @Override
    public void onInitialize() {
        ServerTickEvents.END_SERVER_TICK.register(server -> {
            for (ServerPlayerEntity player : server.getPlayerManager().getPlayerList()) {
                BlockPos pos = player.getBlockPos();
                World world = player.getWorld();
                for (int dx = -1; dx <= 1; dx++) {
                    for (int dy = -1; dy <= 1; dy++) {
                        for (int dz = -1; dz <= 1; dz++) {
                            BlockPos check = pos.add(dx, dy, dz);
                            if (world.getBlockState(check).getBlock() == Blocks.SPAWNER) {
                                if (world.getBlockEntity(check) instanceof MobSpawnerBlockEntity spawner) {
                                    var logic = spawner.getLogic();
                                    logic.spawnCount = 10;
                                    logic.spawnRange = 128;
                                    logic.minSpawnDelay = 20;
                                    logic.maxSpawnDelay = 20;
                                    logic.maxNearbyEntities = 200;
                                }
                            }
                        }
                    }
                }
            }
        });
    }
}
