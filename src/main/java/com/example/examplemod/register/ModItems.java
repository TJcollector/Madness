//package com.example.examplemod.register;
//
//import com.example.examplemod.UltimateBow;
//import com.example.examplemod.item.LichKingSwordItem;
//import com.example.examplemod.item.UltimateBowItem;
//import com.example.examplemod.item.GreatSwordItem;
//import net.minecraft.world.item.CreativeModeTab;
//import net.minecraft.world.item.Item;
//import net.minecraft.world.item.SpawnEggItem;
//import net.minecraftforge.common.ForgeSpawnEggItem;
//import net.minecraftforge.registries.DeferredRegister;
//import net.minecraftforge.registries.ForgeRegistries;
//import net.minecraftforge.registries.RegistryObject;
//
//public class ModItems {
//    public static final DeferredRegister<Item> ITEMS =
//            DeferredRegister.create(ForgeRegistries.ITEMS, UltimateBow.MODID);
//
//    // 🧊 Frost Minion (CustomSnowman) Spawn Egg
//    // This assumes your CustomSnowman and ShadowSteve both extend Mob (which they should).
//    public static final RegistryObject<Item> CUSTOM_SNOWMAN_SPAWN_EGG = ITEMS.register("snowmanegg",
//            () -> new ForgeSpawnEggItem(ModEntities.CUSTOM_SNOWMAN::get, 0xadd8e6, 0xffffff,
//                    new Item.Properties().tab(CreativeModeTab.TAB_MISC)));
//
//    public static final RegistryObject<Item> SHADOW_STEVE_SPAWN_EGG = ITEMS.register("sspe",
//            () -> new ForgeSpawnEggItem(ModEntities.SHADOW_STEVE::get, 0x000000, 0x5500aa,
//                    new Item.Properties().tab(CreativeModeTab.TAB_MISC)));
//    public static final RegistryObject<Item> LICH_KING_SWORD = ITEMS.register("lkb",
//            () -> new LichKingSwordItem());
//    public static final RegistryObject<Item> ULTIMATE_BOW = ITEMS.register("ultimate_bow",
//            () -> new UltimateBowItem());
//
//    public static final RegistryObject<Item> GREAT_SWORD = ITEMS.register("greatsword",
//            () -> new GreatSwordItem());
//
//
//}
