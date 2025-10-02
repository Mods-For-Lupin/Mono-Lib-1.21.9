package com.cursee.monolib.api.common.sailing;

import com.cursee.monolib.Constants;

/**
 * Registers mods to check that they exist in the {@code mods} directory with the expected filename.
 * <br /> <br /> Sailing will warn players upon joining/loading a world that their instance contains
 * modified jar files for registered mods, and include a link with the name of the mod and
 * publisher.
 */
public class SailingApi {

  /**
   * Players tagged with this String will bypass the check made by Sailing
   */
  public static final String CHECKED = Constants.MOD_ID + ".sailing";

  /**
   * @param modID        the namespace of the mod used in the jar filename
   * @param modName      the display name of the mod
   * @param modVersion   the mod version used in the jar filename
   * @param modPublisher the publisher of the mod
   * @param modURL       the URL to give to the player to download the correct mod file
   * @see SailingApi
   */
  public static void register(String modID, String modName, String modVersion, String modPublisher,
      String modURL) {
  }
}
