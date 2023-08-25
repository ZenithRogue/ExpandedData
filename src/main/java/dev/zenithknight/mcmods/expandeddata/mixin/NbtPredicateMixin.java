package dev.zenithknight.mcmods.expandeddata.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.RecipeInputInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
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
                nbtCompound.put("CursorItem", itemStack.writeNbt(new NbtCompound()));
                System.out.print(itemStack);
            }
            if (!((PlayerEntity) entity).playerScreenHandler.getCraftingInput().isEmpty()) {
                RecipeInputInventory craftingInput = ((PlayerEntity) entity).playerScreenHandler.getCraftingInput();
                NbtList nbtList = new NbtList();
                for(int i = 0; i < craftingInput.size(); ++i) {
                    NbtCompound stackNbtCompound = craftingInput.getStack(i).writeNbt(new NbtCompound());
                    stackNbtCompound.putByte("Slot", (byte) i);
                    nbtList.add(stackNbtCompound);
                }
                nbtCompound.put("CraftingItems", nbtList);
            }
        }
        cir.setReturnValue(nbtCompound);
    }
}

