package com.cursee.monolib.impl.common.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AnvilBlock;

@FunctionalInterface
public interface FabricAnvilOnBrokenAfterFallEvent {

  Event<FabricAnvilOnBrokenAfterFallEvent> EVENT = EventFactory.createArrayBacked(
      FabricAnvilOnBrokenAfterFallEvent.class, events -> (anvilBlock, level, pos, fallingBlock) -> {
        for (FabricAnvilOnBrokenAfterFallEvent event : events) {
          event.onBrokenAfterFall(anvilBlock, level, pos, fallingBlock);
        }
      });

  void onBrokenAfterFall(AnvilBlock anvilBlock, Level level, BlockPos pos,
      FallingBlockEntity fallingBlock);
}
