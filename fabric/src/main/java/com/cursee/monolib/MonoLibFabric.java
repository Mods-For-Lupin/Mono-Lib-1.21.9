package com.cursee.monolib;

import com.cursee.monolib.impl.common.registry.MonoLibCommands;
import com.mojang.brigadier.CommandDispatcher;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.Commands.CommandSelection;

public class MonoLibFabric implements ModInitializer {

  @Override
  public void onInitialize() {
    MonoLib.init();

    CommandRegistrationCallback.EVENT.register(this::onRegisterCommands);
  }

  private void onRegisterCommands(CommandDispatcher<CommandSourceStack> commandDispatcher,
      CommandBuildContext commandBuildContext, CommandSelection commandSelection) {
    MonoLibCommands.registerModCommands(commandDispatcher, commandBuildContext, commandSelection);
  }
}
