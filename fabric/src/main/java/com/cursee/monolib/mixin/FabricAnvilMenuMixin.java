package com.cursee.monolib.mixin;

import com.cursee.monolib.impl.common.event.FabricAnvilCreateResultEvent;
import com.cursee.monolib.impl.common.event.FabricAnvilOnTakeEvent;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AnvilMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.DataSlot;
import net.minecraft.world.inventory.ItemCombinerMenu;
import net.minecraft.world.inventory.ItemCombinerMenuSlotDefinition;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import oshi.util.tuples.Triplet;

@Mixin(AnvilMenu.class)
public abstract class FabricAnvilMenuMixin extends ItemCombinerMenu {

    @Shadow private String itemName;
    @Shadow private int repairItemCountCost;
    @Shadow @Final private DataSlot cost;

    /**
     * Used to call the invoker for {@link FabricAnvilCreateResultEvent}. <p></p>
     * This inject differs slightly from Collective, which targets TAIL rather than RETURN.
     */
    @Inject(method = "createResult()V", at = @At(value= "RETURN"))
    public void monolib$createResult(CallbackInfo info) {

        AnvilMenu instance = (AnvilMenu) (Object) this;
        Container injected$inputSlots = this.inputSlots;

        ItemStack injected$slotLeft = injected$inputSlots.getItem(0);
        ItemStack injected$slotRight = injected$inputSlots.getItem(1);
        ItemStack injected$slotOutput = this.resultSlots.getItem(0);

        int injected$baseCost = injected$slotLeft.getOrDefault(DataComponents.REPAIR_COST, 0) + (injected$slotRight.isEmpty() ? 0 : injected$slotRight.getOrDefault(DataComponents.REPAIR_COST, 0));

        Triplet<Integer, Integer, ItemStack> injected$triple = FabricAnvilCreateResultEvent.EVENT.invoker().createResult(instance, injected$slotLeft, injected$slotRight, injected$slotOutput, itemName, injected$baseCost, this.player);

        if (injected$triple == null) return;

        if (injected$triple.getA() >= 0) cost.set(injected$triple.getA());

        if (injected$triple.getB() >= 0) repairItemCountCost = injected$triple.getB();

        if (injected$triple.getC() != null) this.resultSlots.setItem(0, injected$triple.getC());
    }

    @Inject(method = "onTake", at = @At("HEAD"))
    private void monolib$onTakeHEAD(Player player, ItemStack stack, CallbackInfo ci) {
        AnvilMenu instance = (AnvilMenu) (Object) this;
        FabricAnvilOnTakeEvent.EVENT.invoker().onTake(instance, player, stack, this.inputSlots.getItem(0), this.inputSlots.getItem(1));
    }

    // dummy
    public FabricAnvilMenuMixin(MenuType<?> menuType, int containerId, Inventory inventory, ContainerLevelAccess access, ItemCombinerMenuSlotDefinition slotDefinition) {
        super(menuType, containerId, inventory, access, slotDefinition);
    }
}
