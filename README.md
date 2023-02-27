# Expanded Data
Expanded Block Data Objects for Minecraft `data` commands.

This current version is built for Minecraft pre-release 1.19.4-pre1, but translation is pretty straight forwards between other versions with only minor tweaks required.

This is a server-side mod, and is designed with full compatibility in mind for datapack useage.

## Example Usage:
Getting a block for use in other data commands
```hs
data get block -188 68 985
```
![image](https://user-images.githubusercontent.com/11393734/221482680-96cd41a7-e530-416f-99d3-e2f71099343c.png)
```js
{block_state: {Properties: {east: "true", waterlogged: "false", south: "true", north: "false", west: "false"}, Name: "minecraft:white_stained_glass_pane"}, x: -188, y: 68, z: 985}
```
The `block_state` property is designed to have 1-to-1 compatibility with the new 1.19.4 `block_display` entities. This allows block data to be copied to/from real blocks and `block_displays`.
