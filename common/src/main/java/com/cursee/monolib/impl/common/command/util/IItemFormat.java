package com.cursee.monolib.impl.common.command.util;

import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;

/**
 * Adapted from Darkhax's <a href="https://github.com/Darkhax-Minecraft/Bookshelf">Bookshelf</a>
 */
public interface IItemFormat {

  Component formatItem(ItemStack stack, ServerLevel level);
}
