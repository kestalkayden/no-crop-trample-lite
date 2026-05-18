package com.kestalkayden.nocroptramplelite.config;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** JSON config at config/nocroptramplelite.json. Single toggle for now;
 *  shouldPrevent(entity) centralizes the decision so v0.2 can add per-entity-type
 *  toggles without touching the mixin or event handler. */
public final class ModConfig {
    private static final Logger LOGGER = LoggerFactory.getLogger("nocroptramplelite");
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final String FILE_NAME = "nocroptramplelite.json";

    private static ModConfig INSTANCE = new ModConfig();

    /** When true, farmland never reverts to dirt from entities landing on it. */
    public boolean preventTrampling = true;

    public static ModConfig get() { return INSTANCE; }

    public static void load(Path configDir) {
        Path file = configDir.resolve(FILE_NAME);
        if (!Files.exists(file)) {
            INSTANCE = new ModConfig();
            save(configDir);
            return;
        }
        try {
            ModConfig loaded = GSON.fromJson(Files.readString(file), ModConfig.class);
            INSTANCE = loaded != null ? loaded : new ModConfig();
        } catch (JsonSyntaxException | IOException e) {
            LOGGER.error("Failed to read {}, using defaults", file, e);
            INSTANCE = new ModConfig();
        }
    }

    private static void save(Path configDir) {
        Path file = configDir.resolve(FILE_NAME);
        try {
            Files.createDirectories(configDir);
            Files.writeString(file, GSON.toJson(INSTANCE));
        } catch (IOException e) {
            LOGGER.error("Failed to write default config to {}", file, e);
        }
    }

    /** Single decision point. v0.2 can extend with per-entity-type toggles
     *  (player/mob/projectile) by inspecting the entity. */
    public boolean shouldPrevent(net.minecraft.world.entity.Entity entity) {
        return preventTrampling;
    }
}
