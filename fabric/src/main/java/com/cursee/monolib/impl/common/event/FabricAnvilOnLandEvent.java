package com.cursee.monolib.impl.common.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AnvilBlock;
import net.minecraft.world.level.block.state.BlockState;

@FunctionalInterface
public interface FabricAnvilOnLandEvent {

  Event<FabricAnvilOnLandEvent> EVENT = EventFactory.createArrayBacked(FabricAnvilOnLandEvent.class,
      events -> (anvilBlock, level, pos, state, replaceableState, fallingBlock) -> {
        for (FabricAnvilOnLandEvent event : events) {
          event.onLand(anvilBlock, level, pos, state, replaceableState, fallingBlock);
        }
      });

  void onLand(AnvilBlock anvilBlock, Level level, BlockPos pos, BlockState state,
      BlockState replaceableState, FallingBlockEntity fallingBlock);
}
