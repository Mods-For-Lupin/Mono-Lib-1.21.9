package com.cursee.monolib.impl.common.command.data;

import com.cursee.monolib.Constants;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import java.util.Arrays;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

/**
 * Adapted from Darkhax's <a href="https://github.com/Darkhax-Minecraft/Bookshelf">Bookshelf</a>
 */
public class DataSubCommand {

  private static final int FAILURE = 0;

  public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {

    // defines root "/monolib" command
    dispatcher.register(Commands.literal(Constants.MOD_ID)

        // adds "data" as an argument for our root command
        .then(Commands.literal("data")

            // add slot suggestions prefilled from SlotArgument values
            .then(Commands.argument("slot", StringArgumentType.word())
                .suggests((context, builder) -> {
                  Arrays.stream(SlotArgument.values())
                      .forEach(slot -> builder.suggest(slot.getCommandName()));
                  return builder.buildFuture();
                })

                // add format suggestions prefilled from FormatArgument values
                .then(Commands.argument("format", StringArgumentType.word())
                    .suggests((context, builder) -> {
                      Arrays.stream(FormatArgument.values())
                          .forEach(format -> builder.suggest(format.getCommandName()));
                      return builder.buildFuture();
                    }).executes(DataSubCommand::execute)))));
  }

  /**
   * Executes the full `/monolib data <slot> <format> command`. Fails if the source is not a living
   * entity or an illegal/invalid argument was passed
   */
  private static int execute(CommandContext<CommandSourceStack> context) {

    final CommandSourceStack source = context.getSource();

    // fail early if not from LivingEntity
    if (!(source.getEntity() instanceof LivingEntity living)) {
      return DataSubCommand.FAILURE; // fail
    }

    try {
      // get the supplied arguments as strings
      String slotName = StringArgumentType.getString(context, "slot");
      String formatName = StringArgumentType.getString(context, "format");

      // get the value of each argument from its appropriate enum
      SlotArgument slotArgument = SlotArgument.valueOf(slotName.toUpperCase());
      FormatArgument formatArgument = FormatArgument.valueOf(formatName.toUpperCase());

      ItemStack itemStack = slotArgument.getItemFromEntity(living);

      source.sendSuccess(() -> formatArgument.getFormat().formatItem(itemStack, source.getLevel()),
          false // disallow extra console logging
      );
    } catch (IllegalArgumentException e) {

      // exception may be thrown if illegal argument is passed (i.e. player requests invalid slot)

      if (source.getEntity() instanceof Player player) {
        player.displayClientMessage(Component.literal(
            "Illegal arguments for 'monolib data' command: " + context.getInput()), false);
      }

      return DataSubCommand.FAILURE; // failure
    }

    return Command.SINGLE_SUCCESS;
  }
}
