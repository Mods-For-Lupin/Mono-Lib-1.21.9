package com.cursee.monolib.impl.common.sailing.warden;

import com.cursee.monolib.Constants;
import com.cursee.monolib.MonoLib;
import java.io.File;
import java.nio.file.InvalidPathException;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Adapted from Mycelium Mod Network's <a
 * href="https://github.com/Mycelium-Mod-Network/Warden">Warden</a>, to identify potentially unsafe
 * downloads.
 */
public class SailingWarden {

  public static final Map<String, String> UNSAFE_PATH_TO_UNSAFE_HOST_MAP = new HashMap<>();

  public static void process(String modsDirectory) {

      if (!MonoLib.shouldVerifyMods) {
          return;
      }

    final DomainRules domainRules = DomainRules.builtin();

    try {
      if (!checkFileOrDirectory(domainRules, Paths.get(modsDirectory).toFile())) {
        // Constants.LOG.info("No matching files were found.");
        Constants.LOG.info("Warden did not detect any unsafe JAR files in {}", modsDirectory);
        Constants.LOG.info(
            "You can disable this message by setting \"verify_jar_files\" in config/monolib-common.txt to false.");
      }
    } catch (InvalidPathException e) {
      throw new IllegalArgumentException("Invalid path specified. '" + modsDirectory + "'");
    }

      if (SailingWarden.UNSAFE_PATH_TO_UNSAFE_HOST_MAP.isEmpty()) {
          return; // no unsafe downloads found
      }

    Constants.LOG.info("Unsafe download(s):");
    for (String key : SailingWarden.UNSAFE_PATH_TO_UNSAFE_HOST_MAP.keySet()) {
      Constants.LOG.info("- {} from {}", key,
          SailingWarden.UNSAFE_PATH_TO_UNSAFE_HOST_MAP.get(key));
    }
  }

  private static boolean checkFileOrDirectory(DomainRules rules, File target) {

    boolean hasMatch = false;

    if (!target.exists()) {
      throw new IllegalArgumentException(
          "The file does not exist! '" + target.getAbsolutePath() + "'");
    } else if (target.isFile()) {
      final ZoneIdentifier zoneId = ZoneIdentifier.of(target);
      if (zoneId != null && rules.test(zoneId)) {

        // System.out.println("File='" + target + "' host='" + zoneId.getHost() + "' referrer='" + zoneId.getReferrer() + "'.");

        UNSAFE_PATH_TO_UNSAFE_HOST_MAP.put(target.getName(), zoneId.getHost());
        hasMatch = true;
      }
    } else if (target.isDirectory()) {
      for (File subTarget : Objects.requireNonNull(target.listFiles())) {
        hasMatch = hasMatch || checkFileOrDirectory(rules, subTarget);
      }
    }

    return hasMatch;
  }
}
