package com.cursee.monolib.impl.common.sailing;

import com.cursee.monolib.Constants;
import com.cursee.monolib.MonoLib;
import com.cursee.monolib.api.common.sailing.SailingApi;
import com.cursee.monolib.impl.common.sailing.warden.SailingWarden;
import com.cursee.monolib.platform.Services;
import java.io.File;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import org.apache.commons.lang3.ArrayUtils;

public class SailingClientHelper {

  public static void onPlayerJoinClientLevel(LocalPlayer player, ClientLevel level) {

    // if the player is not the local player (LAN safeguard?)
    if (player != Minecraft.getInstance().player) {
      return;
    }

    final boolean ENTITY_CHECKED = player.getTags().contains(SailingApi.CHECKED_TAG);

    // return early if we shouldn't verify, or have already verified/checked the player
    if (!MonoLib.shouldVerifyMods || SailingImpl.verified || ENTITY_CHECKED) {
      return;
    }

    // ??? duplicate? SailingWarden#process already called
//    if (!SailingWarden.UNSAFE_PATH_TO_UNSAFE_HOST_MAP.isEmpty()) {
//      SailingMessage.sendMessage(player, "Unsafe download(s):", ChatFormatting.WHITE);
//      Constants.LOG.info("Unsafe download(s):");
//      for (String key : SailingWarden.UNSAFE_PATH_TO_UNSAFE_HOST_MAP.keySet()) {
//        SailingMessage.sendMessage(player,
//            "- " + key + " from " + SailingWarden.UNSAFE_PATH_TO_UNSAFE_HOST_MAP.get(key),
//            ChatFormatting.WHITE);
//        Constants.LOG.info("- {} from {}", key,
//            SailingWarden.UNSAFE_PATH_TO_UNSAFE_HOST_MAP.get(key));
//      }
//    }

    verifyModFiles(player);
    player.addTag(SailingApi.CHECKED_TAG);
  }

  private static void verifyModFiles(LocalPlayer player) {

    List<String> MOD_NAMES_MISSING_JAR_FILE = SailingImpl.getModNamesMissingJarFile();

    if (!MOD_NAMES_MISSING_JAR_FILE.isEmpty() && SailingImpl.preVerificationProcess()) {

      SailingMessage.sendMessage(player, "Mod(s) from incorrect sources:", ChatFormatting.RED);
      for (String modName : MOD_NAMES_MISSING_JAR_FILE) {
        SailingEntry entry = SailingImpl.MOD_NAME_TO_ENTRY.get(modName);
        SailingMessage.sendMessage(player,
            modName + " by " + entry.modPublisher() + " (Click Here)", ChatFormatting.YELLOW,
            entry.modURL());
      }

      SailingMessage.sendMessage(player,
          "You a receiving this message because one or more of your mod files has been altered and possibly not downloaded from an original and safe source. Unofficial sources can contain malicious software or host outdated versions of mods, as well as removing ad revenue from mod authors.",
          ChatFormatting.RED);
      SailingMessage.sendMessage(player,
          "Click on the name of the mod above to find it's original posting.",
          ChatFormatting.DARK_GREEN);
      SailingMessage.sendMessage(player,
          "You won't see this message again in this instance. Thank you for reading.",
          ChatFormatting.DARK_GREEN);

      Constants.LOG.info(
          "You a receiving this message because one or more of your mod files has been altered and possibly not downloaded from an original and safe source. Unofficial sources can contain malicious software or host outdated versions of mods, as well as removing ad revenue from mod authors.");
      Constants.LOG.info("Click on the name of the mod above to find it's original posting.");
      Constants.LOG.info(
          "You won't see this message again in this instance. Thank you for reading.");

      SailingImpl.postVerificationProcess();
    }

    SailingImpl.verified = true;
  }
}
