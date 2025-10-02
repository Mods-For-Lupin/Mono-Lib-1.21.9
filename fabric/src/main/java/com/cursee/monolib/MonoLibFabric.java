package com.cursee.monolib;

import com.cursee.monolib.impl.common.registry.MonoLibCommands;
import com.cursee.monolib.impl.common.sailing.SailingServerHelper;
import com.mojang.brigadier.CommandDispatcher;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands.CommandSelection;
import net.minecraft.server.MinecraftServer;

public class MonoLibFabric implements ModInitializer {

  @Override
  public void onInitialize() {
    MonoLib.init();

    CommandRegistrationCallback.EVENT.register(this::onRegisterCommands);
    ServerLifecycleEvents.SERVER_STARTED.register(this::onServerStarted);
  }

  private void onRegisterCommands(CommandDispatcher<CommandSourceStack> commandDispatcher,
      CommandBuildContext commandBuildContext, CommandSelection commandSelection) {
    MonoLibCommands.registerModCommands(commandDispatcher, commandBuildContext, commandSelection);
  }

  private void onServerStarted(MinecraftServer server) {
    SailingServerHelper.onServerStarted(server);
  }
}
