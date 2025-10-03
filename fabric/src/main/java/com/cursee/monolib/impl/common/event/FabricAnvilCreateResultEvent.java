package com.cursee.monolib.impl.common.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AnvilMenu;
import net.minecraft.world.item.ItemStack;
import oshi.util.tuples.Triplet;

@FunctionalInterface
public interface FabricAnvilCreateResultEvent {

  Event<FabricAnvilCreateResultEvent> EVENT = EventFactory.createArrayBacked(
      FabricAnvilCreateResultEvent.class,
      events -> (anvilMenu, leftStack, rightStack, output, itemName, baseCost, player) -> {

        for (FabricAnvilCreateResultEvent event : events) {
          var result = event.createResult(anvilMenu, leftStack, rightStack, output, itemName,
              baseCost, player);
            if (result != null) {
                return result;
            }
        }

        return null;
      });

  Triplet<Integer, Integer, ItemStack> createResult(AnvilMenu anvilMenu, ItemStack leftStack,
      ItemStack rightStack, ItemStack output, String itemName, int baseCost, Player player);
}
