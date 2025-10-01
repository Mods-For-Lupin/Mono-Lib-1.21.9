package com.cursee.monolib;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.javafmlmod.FMLModContainer;

public class MonoLibClientNeoForge {

  public static IEventBus modEventBus;

  public MonoLibClientNeoForge(IEventBus modEventBus, FMLModContainer fmlModContainer) {
    MonoLibClientNeoForge.modEventBus = modEventBus;
    MonoLibClient.init();
  }
}
