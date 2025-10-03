package com.cursee.monolib.impl.common.sailing;

import com.cursee.monolib.Constants;
import com.cursee.monolib.MonoLib;
import com.cursee.monolib.impl.common.sailing.warden.SailingWarden;
import com.cursee.monolib.platform.Services;
import java.io.File;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import org.apache.commons.lang3.ArrayUtils;

public class SailingServerHelper {

  public static void onServerStarted(MinecraftServer server) {

    // return early if we shouldn't verify, or have already verified/checked the player
    if (!MonoLib.shouldVerifyMods || SailingImpl.verified) {
      return;
    }

    if (!SailingWarden.UNSAFE_PATH_TO_UNSAFE_HOST_MAP.isEmpty()) {
      Constants.LOG.info("Unsafe download(s):");
      for (String key : SailingWarden.UNSAFE_PATH_TO_UNSAFE_HOST_MAP.keySet()) {
        Constants.LOG.info("- {} from {}", key,
            SailingWarden.UNSAFE_PATH_TO_UNSAFE_HOST_MAP.get(key));
      }
    }

    SailingImpl.verified = verifyModFiles();
  }

  private static boolean verifyModFiles() {

    List<String> MOD_NAMES_MISSING_JAR_FILE = SailingImpl.getModNamesMissingJarFile();

    if (!MOD_NAMES_MISSING_JAR_FILE.isEmpty() && SailingImpl.preVerificationProcess()) {

      Constants.LOG.info("Mod(s) from incorrect sources:");
      for (String modName : MOD_NAMES_MISSING_JAR_FILE) {
        SailingEntry entry = SailingImpl.MOD_NAME_TO_ENTRY.get(modName);
        Constants.LOG.info("{} by {} {}", modName, entry.modPublisher(), entry.modURL());
      }

      Constants.LOG.info(
          "You a receiving this message because one or more of your mod files has been altered and possibly not downloaded from an original and safe source. Unofficial sources can contain malicious software or host outdated versions of mods, as well as removing ad revenue from mod authors.");
      Constants.LOG.info("Click on the name of the mod above to find it's original posting.");
      Constants.LOG.info(
          "You won't see this message again in this instance. Thank you for reading.");

      SailingImpl.postVerificationProcess();
    }

    return true;
  }
}
