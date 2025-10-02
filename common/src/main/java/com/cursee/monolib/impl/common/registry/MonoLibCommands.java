package com.cursee.monolib.impl.common.registry;

import com.cursee.monolib.impl.common.command.data.DataSubCommand;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands.CommandSelection;

public class MonoLibCommands {

  public static void registerModCommands(CommandDispatcher<CommandSourceStack> commandDispatcher,
      CommandBuildContext commandBuildContext, CommandSelection commandSelection) {

    // defines and registers "/monolib data"
    DataSubCommand.register(commandDispatcher);
  }
}
