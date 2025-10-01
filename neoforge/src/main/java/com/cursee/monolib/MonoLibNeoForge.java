package com.cursee.monolib;


import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.javafmlmod.FMLModContainer;

@Mod(Constants.MOD_ID)
public class MonoLibNeoForge {

  public static IEventBus modEventBus;

  public MonoLibNeoForge(IEventBus modEventBus, FMLModContainer fmlModContainer, Dist dist) {
    MonoLibNeoForge.modEventBus = modEventBus;
    MonoLib.init();
    if (dist == Dist.CLIENT) new MonoLibClientNeoForge(modEventBus, fmlModContainer);
  }
}