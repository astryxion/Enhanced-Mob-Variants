# Enhanced Mob Variants

Adds unique visual variants to vanilla mobs to improve world diversity.

This is a visual-only mod. Mobs keep vanilla AI, drops, breeding, health, and spawning rules. No extra mods are required.

**Author:** Astryxion  
**Credits:** Astryxion, nyuppo  
**License:** [GNU General Public License v3.0](LICENSE)

Textures are based on [More Mob Variants](https://www.curseforge.com/minecraft/mc-mods/more-mob-variants) by nyuppo. This project is a separate implementation with broader version and loader support.

## Variants

All builds include the same adult variants. Babies get those variants on every version. Minecraft 26.1 added new baby models, so the **26.1** and **26.2** builds also include converted baby texture sheets for chicken, cow, pig, sheep (including wool), cat, wolf, and zombie.

| Mob | Textures |
| --- | --- |
| Chicken | amber, bronzed, duck, gold_crested, midnight, skewbald, stormy |
| Cow | ashen, umbra, cookie, wooly, sunset, pinto, dairy, cream, albino |
| Pig | pink_footed, mottled, sooty, spotted, piebald |
| Sheep | fuzzy, inky, long_nosed, patched, rainbow, rocky |
| Wolf | basenji, french_bulldog, german_shepherd, golden_retriever, husky, jupiter (wild / tame / angry) |
| Cat | anita_hart, doug, gray_tabby, handsome, tortoiseshell |
| Zombie | alex, ari, efe, kai, makena, noor, sunny, zuri |
| Skeleton | dungeons, mossy, sandy, weathered |
| Spider | black_widow, bone, brown, tarantula |

On **1.7.10** and **1.12.2**, cat variants apply to ocelots. Custom sheep wool is drawn untinted. Husks, cave spiders, wither skeletons, and zombie pigmen / zombified piglins stay vanilla.

## Loaders and versions

Use the jar that matches your Minecraft version and loader.

| Minecraft | Forge | Fabric | NeoForge |
| --- | --- | --- | --- |
| 1.7.10 | yes | | |
| 1.12.2 | yes | | |
| 1.16.5 | yes | | |
| 1.20.1 | yes | yes | |
| 1.21.1 | | yes | yes |
| 1.21.11 | | yes | yes |
| 26.1 | | yes (`26.1+`) | yes (`26.1+`) |
| 26.2 | | yes (`26.2+`) | yes (`26.2+`) |

The 26.1 and 26.2 builds declare a minimum version of `26.1+` / `26.2+` so they remain usable on later patches of that release as long as the game stays code-compatible.

## Config

Config is optional. By default every mob type is enabled.

- **Spawn mode** (server / singleplayer): `RANDOM` lets each mob roll any texture. `UNIFORM` makes a spawn group share one texture, and babies inherit it.
- **Client toggles:** turn custom textures off per mob without clearing saved variant data.

Forge and NeoForge expose this in the in-game config screen. Fabric uses `config/emv-client.toml` and `config/emv-common.toml`, which reload while the game is running.

## Modpacks

You may include this mod in public or private modpacks. It works in existing worlds.
