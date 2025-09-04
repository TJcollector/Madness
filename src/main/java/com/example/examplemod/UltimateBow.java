package com.example.examplemod;
import com.example.examplemod.command.SpawnShadowSteveCommand;
import com.example.examplemod.effect.ModEffects;
import com.example.examplemod.effect.ShadowCursedEffect;
import com.example.examplemod.entity.BroodMother;
import com.example.examplemod.entity.CustomSnowman;
import com.example.examplemod.entity.ShadowSteve;
import com.example.examplemod.entity.SnowGolemMinionHandler;
//import com.example.examplemod.entity.UndeadMinion;
import com.example.examplemod.event.CurseEvents;
import com.example.examplemod.event.ShadowCurseLoginHandler;
import com.example.examplemod.item.*;
//import com.example.examplemod.register.ModItems;
import com.example.examplemod.register.ModEntityAttributes;
import com.mojang.logging.LogUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.IForgeRegistry;
import org.slf4j.Logger;
import com.example.examplemod.register.ModEntities;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.common.ForgeMod;

import java.util.UUID;
import java.util.function.Supplier;
import java.util.stream.Collectors;

// The value here should match an entry in the META-INF/mods.toml file
@Mod("ultimatebow")
public class UltimateBow
{
    public static final String MODID = "ultimatebow";
    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogUtils.getLogger();

    public UltimateBow()
    {
        // Register the setup method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        // Register the enqueueIMC method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::enqueueIMC);
        // Register the processIMC method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::processIMC);

        MinecraftForge.EVENT_BUS.register(ShadowCurseLoginHandler.class);
        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
        ModEntities.ENTITIES.register(FMLJavaModLoadingContext.get().getModEventBus());
//
//        FMLJavaModLoadingContext.get().getModEventBus()
//                .addListener(ModEntityAttributes::onRegisterAttributes);

        MinecraftForge.EVENT_BUS.register(new SnowGolemMinionHandler());
        //apply curse
        MinecraftForge.EVENT_BUS.register(CurseEvents.class);
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(this::onClientSetup);
        ModEffects.register(modEventBus);
//        ModEntities.ENTITIES.register(modEventBus);
        //ModEntities.BROOD_MOTHER.register(modEventBus);
        //ModItems.ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
        // Register entity types (like CustomSnowman, ShadowSteve)
        //ModEntities.ENTITIES.register(FMLJavaModLoadingContext.get().getModEventBus());

        // Register attribute creation listener
//        FMLJavaModLoadingContext.get().getModEventBus()
//                .addListener(ModEntities.ModEntityAttributes::onRegisterAttributes);
//        ModEntities.ENTITIES.register(FMLJavaModLoadingContext.get().getModEventBus());
//
//        // Register entity attributes
//        FMLJavaModLoadingContext.get().getModEventBus().addListener(ModEntities.ModEntityAttributes::onRegisterAttributes);


        ModBlocks.register();
        // In UltimateBow.java constructor
        MinecraftForge.EVENT_BUS.addListener(this::onServerStarting);

        //com.example.examplemod.util.SocketMessenger.startServer();

    }
    private void onClientSetup(final FMLClientSetupEvent event) {
        com.example.examplemod.util.SocketMessenger.startServer();
    }


    //
//    @SubscribeEvent
//    public void onRegisterCommands(RegisterCommandsEvent event) {
//        SpawnShadowSteveCommand.register(event.getDispatcher());
//    }
    private void setup(final FMLCommonSetupEvent event)
    {
        // some preinit code
        LOGGER.info("HELLO FROM PREINIT");
        LOGGER.info("DIRT BLOCK >> {}", Blocks.DIRT.getRegistryName());



    }

    private void enqueueIMC(final InterModEnqueueEvent event)
    {
        // Some example code to dispatch IMC to another mod
        InterModComms.sendTo("examplemod", "helloworld", () -> { LOGGER.info("Hello world from the MDK"); return "Hello world";});
    }

    private void processIMC(final InterModProcessEvent event)
    {
        // Some example code to receive and process InterModComms from other mods
        LOGGER.info("Got IMC {}", event.getIMCStream().
                map(m->m.messageSupplier().get()).
                collect(Collectors.toList()));
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
//    @SubscribeEvent
//    public void onServerStarting(ServerStartingEvent event)
//    {
//        // Do something when the server starts
//        LOGGER.info("HELLO from server starting");
//    }
    private void onServerStarting(final net.minecraftforge.event.server.ServerStartingEvent event) {
        System.out.println("[UltimateBow] ServerStarting event fired!");
        com.example.examplemod.util.SocketMessenger.startServer();
    }


    // You can use EventBusSubscriber to automatically subscribe events on the contained class (this is subscribing to the MOD
    // Event bus for receiving Registry Events)
    @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents
    {
        public static Item LICH_KING_SWORD_ITEM;

        @SubscribeEvent
        public static void onItemsRegistry(final RegistryEvent.Register<Item> event)
        {
            IForgeRegistry<Item> registry = event.getRegistry();

//            registry.register(new UltimateBowItem(new Item.Properties()
//                    .stacksTo(1)
//                    .durability(2048)
//                    .tab(CreativeModeTab.TAB_COMBAT))
//                    .setRegistryName(new ResourceLocation("ultimatebow", "ultimate_bow")));
            //load bow
            registry.register(new UltimateBowItem().setRegistryName(new ResourceLocation("ultimatebow", "ultimate_bow")));
            //load the sword
//
//            LICH_KING_SWORD_ITEM = new LichKingSwordItem()
//                    .setRegistryName(new ResourceLocation("ultimatebow", "lkb"));
//            registry.register(LICH_KING_SWORD_ITEM);

            registry.register(new LichKingSwordItem()
                    .setRegistryName(new ResourceLocation("ultimatebow", "frostmourne")));
            //load the great sword
            registry.register(new GreatSwordItem()
                    .setRegistryName(new ResourceLocation("ultimatebow", "greatsword")));

            registry.register(new BonePickaxe()
                    .setRegistryName( new ResourceLocation("ultimatebow", "bonepickaxe")));
            registry.register(new BoneAxe()
                    .setRegistryName( new ResourceLocation("ultimatebow", "boneaxe")));

            //load picture
            registry.register(new Item(new Item.Properties().tab(CreativeModeTab.TAB_MISC))
                    .setRegistryName(new ResourceLocation("ultimatebow", "godzilla")));
            registry.register(new Item(new Item.Properties().tab(CreativeModeTab.TAB_MISC))
                    .setRegistryName(new ResourceLocation("ultimatebow", "javapic")));
            registry.register(new Item(new Item.Properties().tab(CreativeModeTab.TAB_MISC))
                    .setRegistryName(new ResourceLocation("ultimatebow", "milin")));
            //add block
//            // ✅ Use ModBlocks.PHOTO_BLOCK.get() instead of PHOTO_BLOCK
//            registry.register(new BlockItem(ModBlocks.PHOTO_BLOCK.get(), new Item.Properties().tab(CreativeModeTab.TAB_MISC))
//                    .setRegistryName(new ResourceLocation("ultimatebow", "java_block")));
            // Shadow Steve Spawn Egg
            // Shadow Steve Spawn Egg
//            registry.register(new ForgeSpawnEggItem(ModEntities.SHADOW_STEVE,
//                    0x000000, 0x5500aa,
//                    new Item.Properties().tab(CreativeModeTab.TAB_MISC))
//                    .setRegistryName(new ResourceLocation("ultimatebow", "sspe")));
//
//// Frost Minion (CustomSnowman) Spawn Egg
//            registry.register(new ForgeSpawnEggItem(ModEntities.CUSTOM_SNOWMAN,
//                    0xadd8e6, 0xffffff,
//                    new Item.Properties().tab(CreativeModeTab.TAB_MISC))
//                    .setRegistryName(new ResourceLocation("ultimatebow", "snowmanegg")));
//            registry.register(new ForgeSpawnEggItem(
//                    ModEntities.SHADOW_STEVE, // Make sure this is your EntityType<ShadowSteve>
//                    0x000000, // Primary color (black)
//                    0x5500aa, // Secondary color (purple)
//                    new Item.Properties().tab(CreativeModeTab.TAB_MISC)
//            ).setRegistryName(new ResourceLocation("ultimatebow", "sspe")));

            registry.register(new Item(new Item.Properties().tab(CreativeModeTab.TAB_MISC)) {
                @Override
                public InteractionResult useOn(UseOnContext context) {
                    Level level = context.getLevel();
                    if (!level.isClientSide) {
                        ShadowSteve entity = ModEntities.SHADOW_STEVE.get().create(level);
                        if (entity != null) {
                            entity.moveTo(context.getClickedPos().above(), 0.0F, 0.0F);
                            level.addFreshEntity(entity);
                        }
                    }
                    return InteractionResult.sidedSuccess(level.isClientSide);
                }
            }.setRegistryName(new ResourceLocation("ultimatebow", "sspe")));

            registry.register(new ForgeSpawnEggItem(
                    ModEntities.CUSTOM_SNOWMAN, // Make sure this is your EntityType<ShadowSteve>
                    0x000000, // Primary color (black)
                    0x5500aa, // Secondary color (purple)
                    new Item.Properties().tab(CreativeModeTab.TAB_MISC)
            ).setRegistryName(new ResourceLocation("ultimatebow", "snowmanegg")));
//            registry.register(new ForgeSpawnEggItem(
//                    ModEntities.BROOD_MOTHER, // Make sure this is your EntityType<ShadowSteve>
//                    0x000000, // Primary color (black)
//                    0x5500aa, // Secondary color (purple)
//                    new Item.Properties().tab(CreativeModeTab.TAB_MISC)
//            ).setRegistryName(new ResourceLocation("ultimatebow", "broodmother")));
            registry.register(new Item(new Item.Properties().tab(CreativeModeTab.TAB_MISC)) {
                @Override
                public InteractionResult useOn(UseOnContext context) {
                    Level level = context.getLevel();
                    if (!level.isClientSide) {
                        BroodMother entity = ModEntities.BROOD_MOTHER.get().create(level);
                        if (entity != null) {
                            entity.moveTo(context.getClickedPos().above(), 0.0F, 0.0F);
                            level.addFreshEntity(entity);
                        }
                    }
                    return InteractionResult.sidedSuccess(level.isClientSide);
                }
            }.setRegistryName(new ResourceLocation("ultimatebow", "broodmother")));







            LOGGER.info("Registered Ultimate Bow item");
//            registry.register(new Item(new Item.Properties().tab(CreativeModeTab.TAB_COMBAT))
//                    .setRegistryName(new ResourceLocation("ultimatebow", "frostmourne")));

        }
        //for java block
//        public class ModBlocks {
//            public static final Block PHOTO_BLOCK = new Block(Block.Properties.of(Material.STONE)
//                    .strength(1.0f)
//                    .noOcclusion());
//        }
//
//        @SubscribeEvent
//        public static void onBlocksRegistry(final RegistryEvent.Register<Block> blockRegistryEvent)
//        {
//            // Register a new block here
//            LOGGER.info("HELLO from Register Block");
//            blockRegistryEvent.getRegistry().register(ModBlocks.PHOTO_BLOCK.get().setRegistryName(new ResourceLocation("ultimatebow", "java_block")));
//        }
    }

//    @Mod.EventBusSubscriber(modid = UltimateBow.MODID)
//    public class GlobalEvents {
//
//        @SubscribeEvent
//        public static void onLivingHurt(LivingHurtEvent event) {
//            if (event.getSource().getEntity() instanceof UndeadMinion minion
//                    && event.getEntity() instanceof Player player) {
//                UUID summoner = minion.getSummonerUuid();
//                if (summoner != null && player.getUUID().equals(summoner)) {
//                    event.setCanceled(true); // prevent summoner damage
//                }
//            }
//        }
//    }

}
//package com.example.examplemod;
//
//import com.example.examplemod.item.LichKingSwordItem;
//import com.example.examplemod.item.ModEntities;
//import com.example.examplemod.item.UltimateBowItem;
//import com.example.examplemod.item.UndeadMinionZombie;
//import com.mojang.logging.LogUtils;
//import net.minecraft.resources.ResourceLocation;
//import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
//import net.minecraft.world.entity.ai.attributes.DefaultAttributes;
//import net.minecraft.world.item.BlockItem;
//import net.minecraft.world.item.CreativeModeTab;
//import net.minecraft.world.item.Item;
//import net.minecraft.world.level.block.Blocks;
//import net.minecraftforge.common.MinecraftForge;
//import net.minecraftforge.event.RegistryEvent;
//import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
//import net.minecraftforge.eventbus.api.SubscribeEvent;
//import net.minecraftforge.fml.InterModComms;
//import net.minecraftforge.fml.common.Mod;
//import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
//import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
//import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
//import net.minecraftforge.event.server.ServerStartingEvent;
//import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
//import net.minecraftforge.registries.IForgeRegistry;
//import org.slf4j.Logger;
//
//import java.util.stream.Collectors;
//
//@Mod("ultimatebow")
//public class UltimateBow {
//    private static final Logger LOGGER = LogUtils.getLogger();
//
//    public UltimateBow() {
//        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
//        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::enqueueIMC);
//        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::processIMC);
//
//        MinecraftForge.EVENT_BUS.register(this);
//        ModBlocks.register();
//        ModEntities.ENTITIES.register(FMLJavaModLoadingContext.get().getModEventBus());
//    }
//
//    private void setup(final FMLCommonSetupEvent event) {
//        LOGGER.info("HELLO FROM PREINIT");
//        LOGGER.info("DIRT BLOCK >> {}", Blocks.DIRT.getRegistryName());
//    }
//
//    private void enqueueIMC(final InterModEnqueueEvent event) {
//        InterModComms.sendTo("examplemod", "helloworld", () -> {
//            LOGGER.info("Hello world from the MDK");
//            return "Hello world";
//        });
//    }
//
//    private void processIMC(final InterModProcessEvent event) {
//        LOGGER.info("Got IMC {}", event.getIMCStream()
//                .map(m -> m.messageSupplier().get())
//                .collect(Collectors.toList()));
//    }
//
//    @SubscribeEvent
//    public void onServerStarting(ServerStartingEvent event) {
//        LOGGER.info("HELLO from server starting");
//    }
//
//    // 🔧 Register Items
//    @Mod.EventBusSubscriber(modid = "ultimatebow", bus = Mod.EventBusSubscriber.Bus.MOD)
//    public static class RegistryEvents {
//        @SubscribeEvent
//        public static void onItemsRegistry(final RegistryEvent.Register<Item> event) {
//            IForgeRegistry<Item> registry = event.getRegistry();
//
//            registry.register(new UltimateBowItem()
//                    .setRegistryName(new ResourceLocation("ultimatebow", "ultimate_bow")));
//
//            registry.register(new LichKingSwordItem()
//                    .setRegistryName(new ResourceLocation("ultimatebow", "lkb")));
//
//            registry.register(new Item(new Item.Properties().tab(CreativeModeTab.TAB_MISC))
//                    .setRegistryName(new ResourceLocation("ultimatebow", "godzilla")));
//            registry.register(new Item(new Item.Properties().tab(CreativeModeTab.TAB_MISC))
//                    .setRegistryName(new ResourceLocation("ultimatebow", "javapic")));
//            registry.register(new Item(new Item.Properties().tab(CreativeModeTab.TAB_MISC))
//                    .setRegistryName(new ResourceLocation("ultimatebow", "milin")));
//
//            LOGGER.info("Registered Ultimate Bow items");
//        }
//    }
//
//    // 🧠 Register Entity Attributes
//    @Mod.EventBusSubscriber(modid = "ultimatebow", bus = Mod.EventBusSubscriber.Bus.MOD)
//    public static class AttributeEvents {
//        @SubscribeEvent
//        public static void onAttributeCreate(EntityAttributeCreationEvent event) {
//            AttributeSupplier.Builder attributes = UndeadMinionZombie.createAttributes();
//            event.put(ModEntities.UNDEAD_MINION.get(), attributes.build());
//            LOGGER.info("Registered attributes for Undead Minion");
//        }
//    }
//}
//z=30