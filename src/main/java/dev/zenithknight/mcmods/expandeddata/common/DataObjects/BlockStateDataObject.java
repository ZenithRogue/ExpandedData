package dev.zenithknight.mcmods.expandeddata.common.DataObjects;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.command.DataCommandObject;
import net.minecraft.command.argument.NbtPathArgumentType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;

import java.util.Locale;

public class BlockStateDataObject implements DataCommandObject {
    private final BlockPos pos;
    private final BlockState state;
    private final ServerWorld world;
    private BlockEntity entity;

    public BlockStateDataObject(BlockState blockState, BlockPos blockPos, ServerWorld world) {
        this.pos = blockPos;
        this.state = blockState;
        this.world = world;
    }

    public BlockStateDataObject(BlockState blockState, BlockPos blockPos, BlockEntity entity) {
        this.pos = blockPos;
        this.state = blockState;
        this.entity = entity;
        this.world = (ServerWorld) entity.getWorld();
    }

    public void setNbt(NbtCompound nbt) throws CommandSyntaxException {
        BlockState blockState = NbtHelper.toBlockState(this.world.createCommandRegistryWrapper(RegistryKeys.BLOCK),nbt.getCompound("block_state"));
        // We do this to prevent block entities from dropping contents on change
        if (this.entity != null) {
            BlockEntity blockEntity = this.world.getBlockEntity(this.pos);
            blockEntity.read(new NbtCompound(), this.entity.getWorld().getRegistryManager());
            blockEntity.markDirty();
        }
        this.world.setBlockState(this.pos, blockState);
        if (blockState.hasBlockEntity()){
            BlockEntity blockEntity = this.world.getBlockEntity(this.pos);
            blockEntity.read(nbt, this.entity.getWorld().getRegistryManager());
            blockEntity.markDirty();
        }
    }

    public NbtCompound getNbt() throws CommandSyntaxException {
        NbtCompound nbt = new NbtCompound();
        // If this is a block entity, initialize nbt from there
        if (this.entity != null) {
            nbt = this.entity.createNbtWithIdentifyingData(this.entity.getWorld().getRegistryManager());
        } else {
            nbt.putInt("x", this.pos.getX());
            nbt.putInt("y", this.pos.getY());
            nbt.putInt("z", this.pos.getZ());
        }
        NbtCompound blockStateNbt = NbtHelper.fromBlockState(this.state);
        nbt.put("block_state", blockStateNbt);
        return nbt;
    }

    public Text feedbackModify() {
        return Text.translatable("commands.data.block.modified", this.pos.getX(), this.pos.getY(), this.pos.getZ());
    }

    public Text feedbackQuery(NbtElement element) {
        return Text.translatable("commands.data.block.query", this.pos.getX(), this.pos.getY(), this.pos.getZ(), NbtHelper.toPrettyPrintedText(element));
    }

    public Text feedbackGet(NbtPathArgumentType.NbtPath path, double scale, int result) {
        return Text.translatable("commands.data.block.get", path, this.pos.getX(), this.pos.getY(), this.pos.getZ(), String.format(Locale.ROOT, "%.2f", scale), result);
    }
}
