package com.cursee.monolib;

import net.minecraft.resources.ResourceLocation;

public class MonoLib {

  public static boolean shouldVerifyMods = true;

  public static void init() {
  }

  public static ResourceLocation identifier(String path) {
    return ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, path);
  }
}