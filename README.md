# FoliaLocalTPS

FoliaLocalTPS is a lightweight Folia-only plugin that gives players useful TPS information without exposing server region details, coordinates, or other player positions.

The goal is simple: help players understand whether lag is local to their current Folia region or affecting the whole server, while keeping region layout and location data private.

## Commands

| Command | Description | Access |
| --- | --- | --- |
| `/tps-all` | Shows both local region TPS and global TPS. | Everyone |
| `/tps-local` | Shows only the player's current local region TPS. | Everyone |
| `/tps-global` | Shows only the server's global TPS. | Everyone |
| `/folialocaltps reload` | Reloads the plugin configuration. | `folialocaltps.reload` |

Folia's native `/tps` command is not replaced or overridden.

## Permissions

| Permission | Default | Description |
| --- | --- | --- |
| `folialocaltps.execute` | `true` | Documents access to the public TPS commands. |
| `folialocaltps.reload` | `op` | Allows reloading the plugin configuration. |

The public TPS commands are intentionally available to all players by default.

## Privacy

Player-facing TPS output does not include:

- Coordinates
- Region IDs
- Chunk positions
- Other player locations

When `debug: true` is enabled in `config.yml`, location information may be logged to console for testing and troubleshooting only.
