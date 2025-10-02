package com.cursee.monolib.impl.common.sailing;

import com.cursee.monolib.platform.Services;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.SharedConstants;

public class SailingImpl {

  /// Maps raw filenames to their underlying mod's display name
  private static final Map<String, String> FILENAME_TO_MOD_NAME = new HashMap<>();
  /// Maps mod display names to their respective {@link SailingEntry} instance
  private static final Map<String, SailingEntry> MOD_NAME_TO_ENTRY = new HashMap<>();
  /// This instance is assumed to be unverified until the Sailing check occurs to determine value
  public static boolean unverified = true;

  public static void register(String modID, String modName, String modVersion, String modPublisher, String modURL) {

    // i.e. monolib-merged-1.21.8-3.0.0.jar
    String expectedFilename = modID + "-merged-" + Services.PLATFORM.getGameVersion() + "-" + modVersion + ".jar";
    FILENAME_TO_MOD_NAME.put(expectedFilename, modName);

    MOD_NAME_TO_ENTRY.put(modName, new SailingEntry(modID, modName, modVersion, modPublisher, modURL));
  }
}
