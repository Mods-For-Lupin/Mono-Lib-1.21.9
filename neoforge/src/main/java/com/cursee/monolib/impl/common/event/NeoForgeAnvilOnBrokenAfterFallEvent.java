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

public class NeoForgeAnvilOnBrokenAfterFallEvent extends BlockEvent implements IModBusEvent {

    private final AnvilBlock anvilBlock;
    private final FallingBlockEntity fallingBlock;

    public NeoForgeAnvilOnBrokenAfterFallEvent(AnvilBlock anvilBlock, LevelAccessor level, BlockPos pos, BlockState state, FallingBlockEntity fallingBlock) {
        super(level, pos, state);
        this.anvilBlock = anvilBlock;
        this.fallingBlock = fallingBlock;
    }

    public AnvilBlock getAnvilBlock() {

        return anvilBlock;
    }

    public FallingBlockEntity getFallingBlock() {

        return fallingBlock;
    }

    public static void onBrokenAfterFall(AnvilBlock anvilBlock, Level level, BlockPos pos, FallingBlockEntity fallingBlock) {
        MonoLibNeoForge.modEventBus.post(new NeoForgeAnvilOnBrokenAfterFallEvent(anvilBlock, level, pos, level.getBlockState(pos), fallingBlock));
    }
}
