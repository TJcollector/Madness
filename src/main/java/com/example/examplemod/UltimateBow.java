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
        MinecraftForge.EVENT_BUS.register(new SnowGolemMinionHandler());
        //apply curse
        MinecraftForge.EVENT_BUS.register(CurseEvents.class);
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(this::onClientSetup);
        ModEffects.register(modEventBus);
        

        ModBlocks.register();
        // In UltimateBow.java constructor
        MinecraftForge.EVENT_BUS.addListener(this::onServerStarting);

        //com.example.examplemod.util.SocketMessenger.startServer();

    }
    private void onClientSetup(final FMLClientSetupEvent event) {
        com.example.examplemod.util.SocketMessenger.startServer();
    }


    
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


            //load bow
            registry.register(new UltimateBowItem().setRegistryName(new ResourceLocation("ultimatebow", "ultimate_bow")));
            //load the sword
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
                    .setRegistryName(new ResourceLocation("ultimatebow", "javapic")));
            
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

        }
        
    }



}
