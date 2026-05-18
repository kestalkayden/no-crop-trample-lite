# No Crop Trample

Prevents farmland from reverting to dirt when entities land on it. No more destroyed wheat fields from a careless jump or a wandering zombie.

Targets Minecraft 26.1.x on Fabric and NeoForge.

## Requirements

- Minecraft **26.1.x**
- Java **25**
- Fabric Loader **0.18.4+** with **Fabric API**, *or* NeoForge **26.1+**

## Downloads

Coming soon to Modrinth and CurseForge.

## How it works

- **Fabric**: a small mixin wraps the `turnToDirt(...)` call inside `FarmlandBlock.fallOn`. Fall damage still applies — only the farmland-destruction path is skipped.
- **NeoForge**: subscribes to `BlockEvent.FarmlandTrampleEvent` and cancels it.

Both paths route through a single config-aware check, so adding per-entity-type toggles later (player/mob/projectile) is one method body to edit.

## Configuration

Config file at `config/nocroptramplelite.json` (created on first launch):

```json
{
  "preventTrampling": true
}
```

| Key | Default | Notes |
|---|---|---|
| `preventTrampling` | `true` | When true, no entity can trample farmland. Set false to restore vanilla behaviour. |

Edit the file and restart Minecraft (or the dedicated server) for changes to take effect.

## Building

```bash
./gradlew buildAll
```

Produces:
- `fabric/build/libs/nocroptramplelite-fabric-<version>.jar`
- `neoforge/build/libs/nocroptramplelite-neoforge-<version>.jar`

Individual loaders: `./gradlew :fabric:build` or `./gradlew :neoforge:build`.

Dev clients: `./gradlew :fabric:runClient` or `./gradlew :neoforge:runClient`.

## Repo layout

```
shared-resources/   assets, lang — shared between both loaders
fabric/             Fabric loader subproject (includes the mixin)
neoforge/           NeoForge loader subproject (event listener)
```

## License

CC0-1.0.
