package com.cursee.monolib;


import com.cursee.monolib.impl.common.registry.MonoLibCommands;
import com.mojang.brigadier.CommandDispatcher;
import java.util.function.Consumer;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands.CommandSelection;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.Event;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.javafmlmod.FMLModContainer;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.RegisterCommandsEvent;

@Mod(Constants.MOD_ID)
public class MonoLibNeoForge {

  public static IEventBus modEventBus;

  public MonoLibNeoForge(IEventBus modEventBus, FMLModContainer fmlModContainer, Dist dist) {
    MonoLibNeoForge.modEventBus = modEventBus;
    MonoLib.init();
    if (dist == Dist.CLIENT) new MonoLibClientNeoForge(modEventBus, fmlModContainer);

    NeoForge.EVENT_BUS.addListener(this::onRegisterCommands);
  }

  private void onRegisterCommands(RegisterCommandsEvent event) {
    CommandDispatcher<CommandSourceStack> commandDispatcher = event.getDispatcher();
    CommandBuildContext commandBuildContext = event.getBuildContext();
    CommandSelection commandSelection = event.getCommandSelection();
    MonoLibCommands.registerModCommands(commandDispatcher, commandBuildContext, commandSelection);
  }
}