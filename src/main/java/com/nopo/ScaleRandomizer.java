package com.nopo;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class ScaleRandomizer implements ModInitializer {

    public static Config config = new Config(1200, 0.05f, true, false);

    @Override
    public void onInitialize() {
        config = new Config(1200, 0.05f, true, false);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        File configDir = FabricLoader.getInstance().getConfigDir().toFile();
        File configFile = new File(configDir, "scale-randomizer.json");
        String json = gson.toJson(config, Config.class);
        if (!configFile.exists()) {
            try {
                FileWriter writer = new FileWriter(configFile);
                writer.write(json);
                writer.close();
            } catch (IOException ignored) {
            }
        } else {
            try {
                JsonReader reader = new JsonReader(new FileReader(configFile));
                config = gson.fromJson(reader, Config.class);
            } catch (IOException ignored) {
            }
        }
    }
}
