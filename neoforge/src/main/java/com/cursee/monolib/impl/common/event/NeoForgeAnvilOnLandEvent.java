package com.cursee.monolib.impl.common.event;

import com.cursee.monolib.MonoLibNeoForge;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.AnvilBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.fml.event.IModBusEvent;
import net.neoforged.neoforge.event.level.BlockEvent;

public class NeoForgeAnvilOnLandEvent extends BlockEvent implements IModBusEvent {

    private final AnvilBlock anvilBlock;
    private final BlockState replaceable;
    private final FallingBlockEntity fallingBlock;

    public NeoForgeAnvilOnLandEvent(AnvilBlock anvilBlock, LevelAccessor level, BlockPos pos, BlockState state, BlockState replaceable, FallingBlockEntity fallingBlock) {
        super(level, pos, state);
        this.anvilBlock = anvilBlock;
        this.replaceable = replaceable;
        this.fallingBlock = fallingBlock;
    }

    public AnvilBlock getAnvilBlock() {

        return anvilBlock;
    }

    public BlockState getReplaceable() {

        return replaceable;
    }

    public FallingBlockEntity getFallingBlock() {

        return fallingBlock;
    }

    public static void onLand(AnvilBlock anvilBlock, Level level, BlockPos pos, BlockState blockState, BlockState replaceable, FallingBlockEntity fallingBlock) {
        MonoLibNeoForge.modEventBus.post(new NeoForgeAnvilOnLandEvent(anvilBlock, level, pos, blockState, replaceable, fallingBlock));
    }
}
