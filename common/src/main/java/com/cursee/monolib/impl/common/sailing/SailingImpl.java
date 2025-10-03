package com.cursee.monolib.impl.common.sailing;

import com.cursee.monolib.Constants;
import com.cursee.monolib.platform.Services;
import java.io.File;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.ArrayUtils;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.ApiStatus.Internal;

public class SailingImpl {

  /// Maps raw filenames to their underlying mod's display name
  public static final Map<String, String> FILENAME_TO_MOD_NAME = new HashMap<>();
  /// Maps mod display names to their respective {@link SailingEntry} instance
  public static final Map<String, SailingEntry> MOD_NAME_TO_ENTRY = new HashMap<>();
  /// This instance is assumed to be unverified until the Sailing check determines otherwise
  public static boolean verified = true;

  @ApiStatus.Internal
  public static void register(String modID, String modName, String modVersion, String modPublisher,
      String modURL) {

    // i.e. monolib-merged-1.21.8-3.0.0.jar
    String expectedFilename =
        modID + "-merged-" + Services.PLATFORM.getGameVersion() + "-" + modVersion + ".jar";
    FILENAME_TO_MOD_NAME.put(expectedFilename, modName);

    MOD_NAME_TO_ENTRY.put(modName,
        new SailingEntry(modID, modName, modVersion, modPublisher, modURL));
  }

  public static boolean preVerificationProcess() {

    String checkFilePathString =
        Services.PLATFORM.getGameDirectoryString() + File.separator + "config" + File.separator
            + Constants.MOD_ID + File.separator + "checked.txt"; // game_directory/config/monolib
    File checkFile = new File(checkFilePathString);

    if (checkFile.exists()) {
      SailingImpl.verified = true;
    }

    return SailingImpl.verified;
  }

  public static void postVerificationProcess() {

    SailingImpl.verified = true;

    // check root game directory config file
    String alternativePath = Services.PLATFORM.getGameDirectoryString();
    alternativePath = alternativePath + File.separator + "config" + File.separator
        + Constants.MOD_ID; // game_directory/config/monolib
    if (new File(alternativePath + File.separator + "checked.txt").isFile()) {
      return; // already exists
    }

    File alternativeDirectory = new File(alternativePath);
    if (!alternativeDirectory.mkdirs()) {
      return;
    }
    try (PrintWriter writer = new PrintWriter(alternativePath + File.separator + "checked.txt",
        StandardCharsets.UTF_8)) {
      writer.println(
          "# Please check out https://stopmodreposts.org/ for more information on why this feature exists.");
      writer.println("checked=true");
    } catch (Exception ignored) {
    }
  }

  public static List<String> getModNamesMissingJarFile() {

    final List<String> INSTALLED_MOD_FILENAMES = getInstalledModFilenames();
    List<String> MOD_NAMES_MISSING_JAR_FILE = new ArrayList<String>();

    for (String filename : SailingImpl.FILENAME_TO_MOD_NAME.keySet()) {

      final boolean CONTAINS_MERGED = INSTALLED_MOD_FILENAMES.contains(filename);
      final boolean CONTAINS_FABRIC = INSTALLED_MOD_FILENAMES.contains(
          filename.replace("-merged-", "-fabric-"));
      final boolean CONTAINS_FORGE = INSTALLED_MOD_FILENAMES.contains(
          filename.replace("-merged-", "-forge-"));
      final boolean CONTAINS_NEOFORGE = INSTALLED_MOD_FILENAMES.contains(
          filename.replace("-merged-", "-neoforge-"));

      if (!INSTALLED_MOD_FILENAMES.isEmpty() && !(CONTAINS_MERGED || CONTAINS_FABRIC
          || CONTAINS_FORGE || CONTAINS_NEOFORGE) && SailingImpl.FILENAME_TO_MOD_NAME.containsKey(
          filename)) {
        MOD_NAMES_MISSING_JAR_FILE.add(SailingImpl.FILENAME_TO_MOD_NAME.get(filename));
      }
    }

    if (!MOD_NAMES_MISSING_JAR_FILE.isEmpty()) {
      Collections.sort(MOD_NAMES_MISSING_JAR_FILE);
    }

    return MOD_NAMES_MISSING_JAR_FILE;
  }

  private static List<String> getInstalledModFilenames() {

    List<String> INSTALLED_MOD_FILENAMES = new ArrayList<String>();

    File MOD_DIRECTORY = new File(
        Services.PLATFORM.getGameDirectoryString() + File.separator + "mods");
    File[] DISCOVERED_FILES = MOD_DIRECTORY.listFiles();
    File VERSIONED_MOD_DIRECTORY = new File(
        Services.PLATFORM.getGameDirectoryString() + File.separator + "mods" + File.separator
            + Services.PLATFORM.getGameVersion());
    File[] DISCOVERED_VERSIONED_FILES = VERSIONED_MOD_DIRECTORY.listFiles();

    if (DISCOVERED_FILES == null && DISCOVERED_VERSIONED_FILES == null) {
      return new ArrayList<String>();
    }

    for (File file : ArrayUtils.addAll(DISCOVERED_FILES, DISCOVERED_VERSIONED_FILES)) {
      if (file.isFile()) {
        String filename = file.getName().replaceAll(" +\\([0-9]+\\)", "");
        INSTALLED_MOD_FILENAMES.add(filename);
      }
    }

    return INSTALLED_MOD_FILENAMES;
  }
}
