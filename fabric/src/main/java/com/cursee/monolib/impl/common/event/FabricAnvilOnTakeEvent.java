package com.cursee.monolib.impl.common.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AnvilMenu;
import net.minecraft.world.item.ItemStack;

/**
 * Similar to Forge's AnvilRepairEvent, but does not modify the break chance applied to the anvil
 * due to restrictions around injecting into/redirecting calls/modifying constants into synthetic
 * static methods created via lambda expressions.
 */
@FunctionalInterface
public interface FabricAnvilOnTakeEvent {

  Event<FabricAnvilOnTakeEvent> EVENT = EventFactory.createArrayBacked(FabricAnvilOnTakeEvent.class,
      events -> (anvilMenu, player, output, left, right) -> {
        for (FabricAnvilOnTakeEvent event : events) {
          event.onTake(anvilMenu, player, output, left, right);
        }
      });

  void onTake(AnvilMenu anvilMenu, Player player, ItemStack output, ItemStack left,
      ItemStack right);
}
