package com.cursee.monolib.impl.common.sailing;

import com.cursee.monolib.MonoLib;
import com.cursee.monolib.api.common.sailing.SailingApi;
import com.cursee.monolib.platform.Services;
import java.io.File;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;

public class SailingClientHelper {

  public static void onPlayerJoinClientLevel(LocalPlayer player, ClientLevel level) {

    // if the player is not the local player (LAN safeguard?)
    if (player != Minecraft.getInstance().player) {
      return;
    }

    final boolean ENTITY_CHECKED = player.getTags().contains(SailingApi.CHECKED_TAG);

    // return early if we shouldn't verify, or have already verified/checked the player
    if (!MonoLib.shouldVerifyMods || !SailingImpl.unverified || ENTITY_CHECKED) {
      return;
    }

    // TODO sailing warden impl

    verifyModFiles(player, level);
    player.addTag(SailingApi.CHECKED_TAG);
  }

  private static void verifyModFiles(LocalPlayer player, ClientLevel level) {

    SailingImpl.unverified = false; // mark this instance as verified
  }
}
