package com.cursee.monolib;

import com.cursee.monolib.api.common.sailing.SailingApi;
import com.cursee.monolib.impl.common.sailing.warden.SailingWarden;
import com.cursee.monolib.platform.Services;
import java.io.File;
import net.minecraft.resources.ResourceLocation;

public class MonoLib {

  public static boolean shouldVerifyMods = true;

  public static void init() {
    SailingWarden.process(Services.PLATFORM.getGameDirectoryString() + File.separator + "mods");
    SailingApi.register(Constants.MOD_ID, Constants.MOD_NAME, Constants.MOD_VERSION, Constants.MOD_PUBLISHER, Constants.MOD_URL);
  }

  public static ResourceLocation identifier(String path) {
    return ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, path);
  }
}