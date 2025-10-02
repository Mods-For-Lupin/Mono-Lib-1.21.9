package com.cursee.monolib.platform;

import com.cursee.monolib.platform.services.IPlatformHelper;
import java.nio.file.Path;
import net.neoforged.fml.ModList;
import net.neoforged.fml.loading.FMLLoader;

public class NeoForgePlatformHelper implements IPlatformHelper {

  @Override
  public String getPlatformName() {

    return "NeoForge";
  }

  @Override
  public String getGameVersion() {
    return FMLLoader.getCurrent().getVersionInfo().mcVersion();
  }

  @Override
  public boolean isModLoaded(String modId) {

    return ModList.get().isLoaded(modId);
  }

  @Override
  public boolean isDevelopmentEnvironment() {

    return FMLLoader.getCurrentOrNull() != null && FMLLoader.getCurrent().isProduction();
  }

  @Override
  public Path getGameDirectory() {

    return FMLLoader.getCurrent().getGameDir();
  }

  @Override
  public String getGameDirectoryString() {

    return getGameDirectory().toString();
  }
}