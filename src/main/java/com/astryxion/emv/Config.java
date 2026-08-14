package com.astryxion.emv;

import net.fabricmc.loader.api.FabricLoader;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Config {
    public enum SpawnMode {
        RANDOM,
        UNIFORM
    }

    public static final Flag CHICKEN = new Flag("chicken", true);
    public static final Flag COW = new Flag("cow", true);
    public static final Flag CAT = new Flag("cat", true);
    public static final Flag PIG = new Flag("pig", true);
    public static final Flag SHEEP = new Flag("sheep", true);
    public static final Flag WOLF = new Flag("wolf", true);
    public static final Flag ZOMBIE = new Flag("zombie", true);
    public static final Flag SKELETON = new Flag("skeleton", true);
    public static final Flag SPIDER = new Flag("spider", true);

    private static final Flag[] CLIENT_FLAGS = {
        CHICKEN, COW, CAT, PIG, SHEEP, WOLF, ZOMBIE, SKELETON, SPIDER
    };

    private static final Pattern ENTRY = Pattern.compile("^\\s*([A-Za-z0-9_]+)\\s*=\\s*(.+?)\\s*$");
    private static SpawnMode spawnMode = SpawnMode.RANDOM;
    private static boolean commonLoaded;
    private static boolean clientLoaded;
    private static long clientMtime = Long.MIN_VALUE;
    private static long commonMtime = Long.MIN_VALUE;

    public static final class Flag {
        private final String key;
        private boolean value;

        private Flag(String key, boolean value) {
            this.key = key;
            this.value = value;
        }

        public boolean get() {
            return value;
        }
    }

    public static void loadCommon() {
        Path toml = configDir().resolve("emv-common.toml");
        Path json = configDir().resolve("emv-common.json");
        Map<String, String> values;
        if (!Files.isRegularFile(toml) && Files.isRegularFile(json)) {
            values = loadLegacyJson(json);
            spawnMode = parseSpawnMode(values.get("spawn_mode"));
            writeCommon(toml, spawnMode);
            deleteQuietly(json);
        } else {
            values = loadToml(toml, "emv-common.toml");
            spawnMode = parseSpawnMode(values.get("spawn_mode"));
            if (!Files.isRegularFile(toml)) {
                writeCommon(toml, spawnMode);
            }
        }
        commonLoaded = true;
        commonMtime = readMtime(toml, commonMtime);
    }

    public static void reloadCommonIfChanged() {
        if (!commonLoaded) {
            return;
        }
        Path toml = configDir().resolve("emv-common.toml");
        long mtime = readMtime(toml, commonMtime);
        if (mtime == commonMtime) {
            return;
        }
        commonMtime = mtime;
        if (mtime < 0) {
            return;
        }
        spawnMode = parseSpawnMode(loadToml(toml, "emv-common.toml").get("spawn_mode"));
        EnhancedMobVariants.LOGGER.info("Reloaded emv-common.toml ({})", spawnMode);
    }

    public static void loadClient() {
        Path toml = configDir().resolve("emv-client.toml");
        Path json = configDir().resolve("emv-client.json");
        Map<String, String> values;
        if (!Files.isRegularFile(toml) && Files.isRegularFile(json)) {
            values = loadLegacyJson(json);
            applyClient(values);
            writeClient(toml);
            deleteQuietly(json);
        } else {
            values = loadToml(toml, "emv-client.toml");
            applyClient(values);
            if (!Files.isRegularFile(toml)) {
                writeClient(toml);
            }
        }
        clientLoaded = true;
        clientMtime = readMtime(toml, clientMtime);
    }

    public static void reloadClientIfChanged() {
        if (!clientLoaded) {
            return;
        }
        Path toml = configDir().resolve("emv-client.toml");
        long mtime = readMtime(toml, clientMtime);
        if (mtime == clientMtime) {
            return;
        }
        clientMtime = mtime;
        if (mtime < 0) {
            return;
        }
        applyClient(loadToml(toml, "emv-client.toml"));
        EnhancedMobVariants.LOGGER.info("Reloaded emv-client.toml");
    }

    public static boolean enabled(Flag flag) {
        return !clientLoaded || flag.get();
    }

    public static SpawnMode spawnMode() {
        return !commonLoaded ? SpawnMode.RANDOM : spawnMode;
    }

    public static boolean isUniform() {
        return spawnMode() == SpawnMode.UNIFORM;
    }

    private static void applyClient(Map<String, String> values) {
        for (Flag flag : CLIENT_FLAGS) {
            String raw = values.get(flag.key);
            if (raw != null) {
                flag.value = Boolean.parseBoolean(raw);
            }
        }
    }

    private static SpawnMode parseSpawnMode(String raw) {
        if (raw == null || raw.isBlank()) {
            return SpawnMode.RANDOM;
        }
        try {
            return SpawnMode.valueOf(unquote(raw).trim().toUpperCase(Locale.ROOT));
        } catch (IllegalArgumentException ignored) {
            return SpawnMode.RANDOM;
        }
    }

    private static Map<String, String> loadToml(Path path, String classpathDefault) {
        String text = "";
        if (Files.isRegularFile(path)) {
            try {
                text = Files.readString(path);
            } catch (IOException e) {
                EnhancedMobVariants.LOGGER.warn("Failed to read {}", path, e);
            }
        } else {
            text = readClasspath(classpathDefault);
        }
        return parseToml(text);
    }

    private static Map<String, String> parseToml(String text) {
        Map<String, String> values = new LinkedHashMap<>();
        for (String line : text.split("\\R")) {
            String trimmed = line.trim();
            if (trimmed.isEmpty() || trimmed.startsWith("#")) {
                continue;
            }
            Matcher matcher = ENTRY.matcher(trimmed);
            if (matcher.matches()) {
                values.put(matcher.group(1), unquote(matcher.group(2)));
            }
        }
        return values;
    }

    private static Map<String, String> loadLegacyJson(Path path) {
        Map<String, String> values = new LinkedHashMap<>();
        try {
            for (String line : Files.readString(path).split("\\R")) {
                Matcher matcher = Pattern.compile("\"([A-Za-z0-9_]+)\"\\s*:\\s*(\"[^\"]+\"|true|false)").matcher(line);
                if (matcher.find()) {
                    values.put(matcher.group(1), unquote(matcher.group(2)));
                }
            }
        } catch (IOException e) {
            EnhancedMobVariants.LOGGER.warn("Failed to read legacy config {}", path, e);
        }
        return values;
    }

    private static void writeClient(Path path) {
        StringBuilder out = new StringBuilder();
        out.append("# Enhanced Mob Variants - client visual toggles\n");
        out.append("# Turn a mob off to hide its custom textures on this client.\n");
        out.append("# Saving this file while the game is running applies immediately.\n\n");
        for (Flag flag : CLIENT_FLAGS) {
            out.append("# Turn custom ").append(flag.key).append(" variants on or off.\n");
            out.append(flag.key).append(" = ").append(flag.value).append("\n\n");
        }
        write(path, out.toString());
    }

    private static void writeCommon(Path path, SpawnMode mode) {
        String text = """
            # Enhanced Mob Variants - spawning
            # This file is used by the server / singleplayer world.

            # RANDOM: each mob can roll any texture. A spawn cluster can mix variants, and breeding can produce any texture.
            # UNIFORM: mobs that spawn in the same group share one texture, and babies inherit that same texture.
            # Saving this file applies to new spawns. Existing mobs keep the texture they already have.
            spawn_mode = "%s"
            """.formatted(mode.name());
        write(path, text);
    }

    private static void write(Path path, String text) {
        try {
            Files.createDirectories(path.getParent());
            Files.writeString(path, text.stripTrailing() + "\n");
        } catch (IOException e) {
            EnhancedMobVariants.LOGGER.warn("Failed to write {}", path, e);
        }
    }

    private static String readClasspath(String name) {
        try (InputStream in = Config.class.getClassLoader().getResourceAsStream(name)) {
            if (in == null) {
                return "";
            }
            return new String(in.readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            return "";
        }
    }

    private static Path configDir() {
        return FabricLoader.getInstance().getConfigDir();
    }

    private static long readMtime(Path path, long fallback) {
        try {
            if (!Files.isRegularFile(path)) {
                return -1;
            }
            return Files.getLastModifiedTime(path).toMillis();
        } catch (IOException e) {
            return fallback;
        }
    }

    private static String unquote(String raw) {
        String value = raw.trim();
        if (value.length() >= 2 && value.startsWith("\"") && value.endsWith("\"")) {
            return value.substring(1, value.length() - 1);
        }
        return value;
    }

    private static void deleteQuietly(Path path) {
        try {
            Files.deleteIfExists(path);
        } catch (IOException ignored) {
        }
    }
}
