package com.cursee.monolib;

import com.cursee.moandjiezana.toml.Toml;
import com.cursee.monolib.platform.Services;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class MonoLibClient {

  public static boolean debugModeEnabled = false;

  public static void init() {
    MonoLibClient.createOrLoadConfiguration();
  }

  private static void createOrLoadConfiguration() {

    String configDirString = Services.PLATFORM.getGameDirectory() + File.separator + "config";
    String configFileString = configDirString + File.separator + "monolib-client.toml";

    File configDir = new File(configDirString);
    if (!configDir.isDirectory() && !configDir.mkdirs()) {
      Constants.LOG.info("Unable to create {}, config retaining default values.", configDirString);
    }

    File configFile = new File(configFileString);
    if (configFile.exists()) {
      Toml toml = new Toml().read(configFile);
      debugModeEnabled = toml.getBoolean("debugging", debugModeEnabled);
    } else {

      // loads internal file from JAR and writes to file location
      InputStream ioStream = MonoLib.class.getClassLoader()
          .getResourceAsStream("assets/monolib-client.toml");

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
        Constants.LOG.info("Failed to copy default config file, error: " + e.getMessage());
        Constants.LOG.info("Configuration will retain default values.");
      }

//      TomlWriter writer = new TomlWriter();
//
//      Map<String, Object> defaults = new LinkedHashMap<>();
//      defaults.put("debugModeEnabled", debugModeEnabled);
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
