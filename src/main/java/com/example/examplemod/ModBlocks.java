package com.example.examplemod;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod.EventBusSubscriber(modid = "ultimatebow", bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, "ultimatebow");

    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, "ultimatebow");

    public static final RegistryObject<Block> PHOTO_BLOCK = BLOCKS.register("java_block",
            () -> new Block(BlockBehaviour.Properties.of(Material.STONE)
                    .strength(3.0F, 3.0F)
                    .requiresCorrectToolForDrops()
            ));

    public static final RegistryObject<Item> PHOTO_BLOCK_ITEM = ITEMS.register("java_block",
            () -> new BlockItem(PHOTO_BLOCK.get(), new Item.Properties().tab(CreativeModeTab.TAB_MISC)));

    public static void register() {
        BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
        ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }
}
