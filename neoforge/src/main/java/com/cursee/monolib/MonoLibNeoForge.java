package com.cursee.monolib;


import com.cursee.monolib.impl.common.registry.MonoLibCommands;
import com.cursee.monolib.impl.common.sailing.SailingServerHelper;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands.CommandSelection;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.javafmlmod.FMLModContainer;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.event.server.ServerStartedEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;

@Mod(Constants.MOD_ID)
public class MonoLibNeoForge {

  public static IEventBus modEventBus;

  public MonoLibNeoForge(IEventBus modEventBus, FMLModContainer fmlModContainer, Dist dist) {
    MonoLibNeoForge.modEventBus = modEventBus;
    MonoLib.init();
    if (dist == Dist.CLIENT) {
      new MonoLibClientNeoForge(MonoLibNeoForge.modEventBus, fmlModContainer);
    }

    NeoForge.EVENT_BUS.addListener(this::onRegisterCommands);
    NeoForge.EVENT_BUS.addListener(this::onServerStarting);
    NeoForge.EVENT_BUS.addListener(this::onServerStarted);
  }

  private void onRegisterCommands(RegisterCommandsEvent event) {
    CommandDispatcher<CommandSourceStack> commandDispatcher = event.getDispatcher();
    CommandBuildContext commandBuildContext = event.getBuildContext();
    CommandSelection commandSelection = event.getCommandSelection();
    MonoLibCommands.registerModCommands(commandDispatcher, commandBuildContext, commandSelection);
  }

  private void onServerStarting(ServerStartingEvent event) {
    MonoLibServer.init();
  }

  private void onServerStarted(ServerStartedEvent event) {
    SailingServerHelper.onServerStarted(event.getServer());
  }
}