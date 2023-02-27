package dev.zenithknight.mcmods.expandeddata.mixin;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import dev.zenithknight.mcmods.expandeddata.common.DataObjects.BlockStateDataObject;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.command.DataCommandObject;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(targets = "net.minecraft.command.BlockDataObject$1")
public class BlockDataObjectMixin {
    // Overwrite Normal Blocks:
    @Inject(method = "getObject", at = @At(value = "FIELD", target = "Lnet/minecraft/command/BlockDataObject;INVALID_BLOCK_EXCEPTION:Lcom/mojang/brigadier/exceptions/SimpleCommandExceptionType;"), locals = LocalCapture.CAPTURE_FAILHARD, cancellable = true)
    public void overwriteNonBlockEntity(CommandContext<ServerCommandSource> commandContext, CallbackInfoReturnable<DataCommandObject> cir, BlockPos blockPos, BlockEntity blockEntity) throws CommandSyntaxException {
        BlockState blockState = ((ServerCommandSource)commandContext.getSource()).getWorld().getBlockState(blockPos);
        ServerWorld world = ((ServerCommandSource)commandContext.getSource()).getWorld();
        cir.setReturnValue(new BlockStateDataObject(blockState, blockPos, world));
    }
    // Overwrite Block Entities:
    @Inject(method = "getObject", at = @At(value = "RETURN", target = "Lnet/minecraft/command/BlockDataObject;INVALID_BLOCK_EXCEPTION:Lcom/mojang/brigadier/exceptions/SimpleCommandExceptionType;"), locals = LocalCapture.CAPTURE_FAILHARD, cancellable = true)
    public void overwriteBlockEntity(CommandContext<ServerCommandSource> commandContext, CallbackInfoReturnable<DataCommandObject> cir, BlockPos blockPos, BlockEntity blockEntity) throws CommandSyntaxException {
        BlockState blockState = ((ServerCommandSource)commandContext.getSource()).getWorld().getBlockState(blockPos);
        cir.setReturnValue(new BlockStateDataObject(blockState, blockPos, blockEntity));
    }
}
