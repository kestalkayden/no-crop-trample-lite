package com.kestalkayden.nocroptramplelite;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kestalkayden.nocroptramplelite.config.ModConfig;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;

public class NoCropTrampleFabric implements ModInitializer {
    public static final String MOD_ID = "nocroptramplelite";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        LOGGER.info("Initializing No Crop Trample (Fabric)");
        ModConfig.load(FabricLoader.getInstance().getConfigDir());
    }
}
