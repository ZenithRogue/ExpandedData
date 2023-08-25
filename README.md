# Expanded Data
Expanded Data Objects and NBT Accessors for Minecraft `data` commands.

This current version is built for Minecraft 1.20.1, but translation is pretty straight forwards between other versions with only minor tweaks required.

This is a server-side mod, and is designed with full compatibility in mind for datapack usage.

## Usage:
### Getting a block for use in other data commands
```hs
data get block -188 68 985
```
![image](https://user-images.githubusercontent.com/11393734/221482680-96cd41a7-e530-416f-99d3-e2f71099343c.png)
```js
{block_state: {Properties: {east: "true", waterlogged: "false", south: "true", north: "false", west: "false"}, Name: "minecraft:white_stained_glass_pane"}, x: -188, y: 68, z: 985}
```
The `block_state` property is designed to have 1-to-1 compatibility with the `block_display` entities introduced in Minecraft 1.19.4. This allows block data to be copied to/from real blocks and `block_displays`.

You can also set/modify any data found `block_state`, as confined by the typically available states
```hs
data modify block -188 68 985 block_state.Name set value "minecraft:dirt"
```

### Getting CursorItem and CraftingItems
```hs
data get @s CursorItem
```
```json
{id: "minecraft:acacia_planks", Count: 2b}
```
```hs
data get @s CraftingItems
```

```json
[{Slot: 0b, id: "minecraft:sand", Count: 1b}, {Slot: 1b, id: "minecraft:acacia_planks", Count: 4b}, {Slot: 2b, id: "minecraft:dirt", Count: 1b}, {Slot: 3b, id: "minecraft:air", Count: 0b}]
```
As these are data values tied to players, you are unfortunately unable to modify them.  All things mentioned will also work in predicates, just as you would expect.
