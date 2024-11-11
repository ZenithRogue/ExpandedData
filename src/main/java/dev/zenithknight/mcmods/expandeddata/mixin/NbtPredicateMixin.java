package dev.zenithknight.mcmods.expandeddata.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.RecipeInputInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.screen.PlayerScreenHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(targets = "net.minecraft.predicate.NbtPredicate")
public class NbtPredicateMixin {
    @Inject(method = "entityToNbt", at = @At("RETURN"), cancellable = true)
    private static void entityToNbtMixin(Entity entity, CallbackInfoReturnable<NbtCompound> cir) {
        NbtCompound nbtCompound = cir.getReturnValue();
        if (entity instanceof PlayerEntity) {
            ItemStack itemStack;
            if (!(itemStack = ((PlayerEntity) entity).currentScreenHandler.getCursorStack()).isEmpty()) {
                nbtCompound.put("CursorItem", itemStack.toNbt(entity.getRegistryManager()));
            }
            if (!((PlayerEntity) entity).playerScreenHandler.getCraftingInput().isEmpty()) {
                PlayerScreenHandler screenHandler = ((PlayerEntity) entity).playerScreenHandler;
                RecipeInputInventory craftingInput = screenHandler.getCraftingInput();
                NbtList nbtList = new NbtList();
                for(int i = 0; i < 4; ++i) {
                    ItemStack testStack = craftingInput.getStack(i);
                    if (!testStack.isEmpty()) {
                        NbtCompound stackNbtCompound = (NbtCompound) craftingInput.getStack(i).toNbt(entity.getRegistryManager());
                        stackNbtCompound.putByte("Slot", (byte) i);
                        nbtList.add(stackNbtCompound);
                    }
                }
                nbtCompound.put("CraftingItems", nbtList);
                if (screenHandler.getOutputSlot().hasStack()) {
                    nbtCompound.put("CraftingResult", screenHandler.getOutputSlot().getStack().toNbt(entity.getRegistryManager()));
                }
            }
        }
        cir.setReturnValue(nbtCompound);
    }
}

