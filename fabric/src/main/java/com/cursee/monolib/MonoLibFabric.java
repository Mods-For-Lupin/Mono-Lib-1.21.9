package com.cursee.monolib;

import net.fabricmc.api.ModInitializer;

public class MonoLibFabric implements ModInitializer {

  @Override
  public void onInitialize() {
    MonoLib.init();
  }
}
