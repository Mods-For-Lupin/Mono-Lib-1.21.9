package com.cursee.monolib;

import com.cursee.moandjiezana.toml.Toml;
import com.cursee.monolib.api.common.sailing.SailingApi;
import com.cursee.monolib.impl.common.sailing.warden.SailingWarden;
import com.cursee.monolib.platform.Services;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import net.minecraft.resources.ResourceLocation;

public class MonoLib {

  public static boolean debugModeEnabled = false;
  public static boolean shouldVerifyMods = true;

  public static void init() {
    SailingWarden.process(Services.PLATFORM.getGameDirectoryString() + File.separator + "mods");
    SailingApi.register(Constants.MOD_ID, Constants.MOD_NAME, Constants.MOD_VERSION,
        Constants.MOD_PUBLISHER, Constants.MOD_URL);
    MonoLib.createOrLoadConfiguration();
  }

  public static ResourceLocation identifier(String path) {
    return ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, path);
  }

  private static void createOrLoadConfiguration() {

    String configDirString = Services.PLATFORM.getGameDirectory() + File.separator + "config";
    File configDir = new File(configDirString);
    if (!configDir.isDirectory() && !configDir.mkdirs()) {
      Constants.LOG.info("Unable to create {}, config retaining default values.", configDirString);
    }

    String configFileString = configDirString + File.separator + "monolib-common.toml";
    File configFile = new File(configFileString);
    if (configFile.exists()) {
      Toml toml = new Toml().read(configFile);
      debugModeEnabled = toml.getBoolean("debugging", debugModeEnabled);
      shouldVerifyMods = toml.getBoolean("verify_mod_files", shouldVerifyMods);
    } else {

      // loads internal file from JAR and writes to file location
      InputStream ioStream = MonoLib.class.getClassLoader()
          .getResourceAsStream("assets/monolib-common.toml");

      if (ioStream == null) {
        Constants.LOG.info("Could not load internal file... config will retain default values.");
        return;
      }

      try (InputStream inputStream = ioStream; BufferedInputStream bis = new BufferedInputStream(
          inputStream); BufferedOutputStream bos = new BufferedOutputStream(
          new FileOutputStream(configFile))) {

        byte[] buffer = new byte[8192];
        int bytesRead;
        while ((bytesRead = bis.read(buffer)) != -1) {
          bos.write(buffer, 0, bytesRead);
        }
      } catch (IOException e) {
        System.out.println("Failed to copy default config file, error: " + e.getMessage());
        System.out.println("Configuration will retain default values.");
      }

      // writes new, raw TOML file
//      TomlWriter writer = new TomlWriter();
//
//      Map<String, Object> defaults = new LinkedHashMap<>();
//      defaults.put("debugModeEnabled", debugModeEnabled);
//      defaults.put("shouldVerifyMods", shouldVerifyMods);
//
//      try {
//        writer.write(defaults, configFile);
//      }
//      catch (IOException e) {
//        Constants.LOG.info(e.getMessage());
//      }
    }
  }
}