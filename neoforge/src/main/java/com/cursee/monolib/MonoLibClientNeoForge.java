package com.cursee.monolib;

import com.cursee.monolib.impl.common.sailing.SailingClientHelper;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.javafmlmod.FMLModContainer;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;

public class MonoLibClientNeoForge {

  public static IEventBus modEventBus;

  public MonoLibClientNeoForge(IEventBus modEventBus, FMLModContainer fmlModContainer) {
    MonoLibClientNeoForge.modEventBus = modEventBus;
    MonoLibClient.init();

    NeoForge.EVENT_BUS.addListener(this::onEntityJoinLevel);
  }

  private void onEntityJoinLevel(EntityJoinLevelEvent event) {
    if (!(event.getEntity() instanceof LocalPlayer player
        && event.getLevel() instanceof ClientLevel level)) {
      return;
    }
    SailingClientHelper.onPlayerJoinClientLevel(player, level);
  }
}
