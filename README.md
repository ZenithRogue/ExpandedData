# Expanded Data
Expanded Data Objects and NBT Accessors for Minecraft `data` commands.

This current version is built for Minecraft 1.21.3, but translation is pretty straight forwards between other versions.

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

### Getting CursorItem, CraftingItems, and CraftingResult 
#### CursorItem
```hs
data get @s CursorItem
```
![image](https://github.com/user-attachments/assets/0a138895-66ff-48fd-8fb4-b18a63404690)
```js
{count: 1, id: "minecraft:diamond_sword"}
```
#### CraftingItems
```hs
data get @s CraftingItems
```
![image](https://github.com/user-attachments/assets/38fecf9c-b501-4bca-8b3c-ef4ed9197766)
```js
[{count: 1, Slot: 0b, id: "minecraft:oak_planks"}, {count: 1, Slot: 1b, id: "minecraft:oak_planks"}, {count: 1, Slot: 2b, id: "minecraft:oak_planks"}, {count: 1, Slot: 3b, id: "minecraft:oak_planks"}]
```
#### CraftingResult
```hs
data get @s CraftingResult
```
![image](https://github.com/user-attachments/assets/57abfc9f-d48b-4983-a401-23e8cb15f76b)
```js
{count: 4, id: "minecraft:oak_planks"}
```

As these are data values tied to players, you are unfortunately unable to modify them.  All things mentioned will also work in predicates, just as you would expect.
