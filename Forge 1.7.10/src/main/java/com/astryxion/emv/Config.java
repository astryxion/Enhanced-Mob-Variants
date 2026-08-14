package com.astryxion.emv;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

import java.io.File;
import java.util.Locale;

public class Config {
    public enum SpawnMode {
        RANDOM,
        UNIFORM
    }

    public static boolean chicken = true;
    public static boolean cow = true;
    public static boolean cat = true;
    public static boolean pig = true;
    public static boolean sheep = true;
    public static boolean wolf = true;
    public static boolean zombie = true;
    public static boolean skeleton = true;
    public static boolean spider = true;
    public static SpawnMode spawnMode = SpawnMode.RANDOM;

    private static Configuration clientCfg;
    private static Configuration commonCfg;
    private static Property chickenProp;
    private static Property cowProp;
    private static Property catProp;
    private static Property pigProp;
    private static Property sheepProp;
    private static Property wolfProp;
    private static Property zombieProp;
    private static Property skeletonProp;
    private static Property spiderProp;
    private static Property spawnModeProp;

    public static void load(File configDir) {
        clientCfg = new Configuration(new File(configDir, "emv-client.cfg"));
        commonCfg = new Configuration(new File(configDir, "emv-common.cfg"));

        chickenProp = toggle(clientCfg, "chicken", "Chicken");
        cowProp = toggle(clientCfg, "cow", "Cow");
        catProp = toggle(clientCfg, "cat", "Cat");
        pigProp = toggle(clientCfg, "pig", "Pig");
        sheepProp = toggle(clientCfg, "sheep", "Sheep");
        wolfProp = toggle(clientCfg, "wolf", "Wolf");
        zombieProp = toggle(clientCfg, "zombie", "Zombie");
        skeletonProp = toggle(clientCfg, "skeleton", "Skeleton");
        spiderProp = toggle(clientCfg, "spider", "Spider");

        spawnModeProp = commonCfg.get(
            "common",
            "spawn_mode",
            SpawnMode.RANDOM.name(),
            "Random: each mob can roll any texture. A spawn cluster can mix variants, and breeding can produce any texture. Uniform: mobs that spawn in the same group share one texture, and babies inherit that same texture."
        );

        chicken = chickenProp.getBoolean(true);
        cow = cowProp.getBoolean(true);
        cat = catProp.getBoolean(true);
        pig = pigProp.getBoolean(true);
        sheep = sheepProp.getBoolean(true);
        wolf = wolfProp.getBoolean(true);
        zombie = zombieProp.getBoolean(true);
        skeleton = skeletonProp.getBoolean(true);
        spider = spiderProp.getBoolean(true);
        spawnMode = parseSpawnMode(spawnModeProp.getString());

        if (clientCfg.hasChanged()) {
            clientCfg.save();
        }
        if (commonCfg.hasChanged()) {
            commonCfg.save();
        }
    }

    private static Property toggle(Configuration cfg, String key, String mob) {
        return cfg.get("client", key, true, "Show custom " + mob.toLowerCase(Locale.ROOT) + " variants.");
    }

    private static SpawnMode parseSpawnMode(String value) {
        try {
            return SpawnMode.valueOf(value.toUpperCase(Locale.ROOT));
        } catch (IllegalArgumentException ignored) {
            return SpawnMode.RANDOM;
        }
    }

    public static boolean isUniform() {
        return spawnMode == SpawnMode.UNIFORM;
    }

    public static void save() {
        if (chickenProp != null) {
            chickenProp.set(chicken);
            cowProp.set(cow);
            catProp.set(cat);
            pigProp.set(pig);
            sheepProp.set(sheep);
            wolfProp.set(wolf);
            zombieProp.set(zombie);
            skeletonProp.set(skeleton);
            spiderProp.set(spider);
            spawnModeProp.set(spawnMode.name());
            clientCfg.save();
            commonCfg.save();
        }
    }
}
