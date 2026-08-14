# LocalTPS for Folia and Canvas

[![Folia Supported](https://img.shields.io/badge/Folia-supported-6DC9E2)](https://papermc.io/software/folia) [![Canvas Supported](https://img.shields.io/badge/Canvas-supported-3B82F6)](https://canvasmc.io/) [![AI assisted](https://img.shields.io/badge/AI-assisted-412991?logo=openai&logoColor=white)](https://openai.com/)

This project builds lightweight Folia and Canvas plugins that give players useful TPS information without exposing server region details, coordinates, or other player positions.

The two platform plugins share their configuration, output formatting, and TPS command implementation. Platform entry points and TPS providers are kept separate so Folia- or Canvas-specific APIs can evolve independently.

## Building

Run `./gradlew platformJars` to build both artifacts and copy them to the top-level `dist` directory:

- `dist/FoliaLocalTPS-1.0.2.jar`
- `dist/CanvasLocalTPS-1.0.2.jar`

## Commands

| Command | Description | Access |
| --- | --- | --- |
| `/tps-all` | Shows both local region TPS and global TPS. | Everyone |
| `/tps-local` | Shows only the player's current local region TPS. | Everyone |
| `/tps-global` | Shows only the server's global TPS. | Everyone |
| `/folialocaltps reload` | Reloads the Folia plugin configuration. | `folialocaltps.reload` |
| `/canvaslocaltps reload` | Reloads the Canvas plugin configuration. | `canvaslocaltps.reload` |

Folia's native `/tps` command is not replaced or overridden.

## Permissions

### Folia Specific

| Permission | Default | Description |
| --- | --- | --- |
| `folialocaltps.execute` | `true` | Documents access to the public TPS commands. |
| `folialocaltps.reload` | `op` | Allows reloading the plugin configuration. |

### Canvas Specific

| Permission | Default | Description |
| --- | --- | --- |
| `canvaslocaltps.execute` | `true` | Documents access to the public TPS commands. |
| `canvaslocaltps.reload` | `op` | Allows reloading the plugin configuration. |

The public TPS commands are intentionally available to all players by default.

## Privacy

Player-facing TPS output does not include:

- Coordinates
- Region IDs
- Chunk positions
- Other player locations

When `debug: true` is enabled in `config.yml`, location information may be logged to console for testing and troubleshooting only.

## AI Assistance

This project uses AI-assisted development and review. All changes remain subject to human review and testing.

## License

Licensed under the [MIT License](LICENSE).
