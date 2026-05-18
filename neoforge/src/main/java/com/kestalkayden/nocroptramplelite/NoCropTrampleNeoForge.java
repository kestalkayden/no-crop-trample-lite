package com.kestalkayden.nocroptramplelite;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kestalkayden.nocroptramplelite.config.ModConfig;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.loading.FMLPaths;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.level.BlockEvent;

@Mod(NoCropTrampleNeoForge.MOD_ID)
public class NoCropTrampleNeoForge {
    public static final String MOD_ID = "nocroptramplelite";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public NoCropTrampleNeoForge(IEventBus modBus) {
        LOGGER.info("Initializing No Crop Trample (NeoForge)");
        ModConfig.load(FMLPaths.CONFIGDIR.get());
        NeoForge.EVENT_BUS.addListener(NoCropTrampleNeoForge::onFarmlandTrample);
    }

    private static void onFarmlandTrample(BlockEvent.FarmlandTrampleEvent event) {
        if (ModConfig.get().shouldPrevent(event.getEntity())) {
            event.setCanceled(true);
        }
    }
}
