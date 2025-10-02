package com.cursee.monolib;

import com.cursee.monolib.impl.common.sailing.SailingClientHelper;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientEntityEvents;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.Entity;

public class MonoLibClientFabric implements ClientModInitializer {

  @Override
  public void onInitializeClient() {
    MonoLibClient.init();

    ClientEntityEvents.ENTITY_LOAD.register(this::onEntityJoinClientLevel);
  }

  private void onEntityJoinClientLevel(Entity entity, ClientLevel level) {
    if (!(entity instanceof LocalPlayer player)) {
      return;
    }
    SailingClientHelper.onPlayerJoinClientLevel(player, level);
  }
}
