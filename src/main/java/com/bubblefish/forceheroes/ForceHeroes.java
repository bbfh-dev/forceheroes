package com.bubblefish.forceheroes;

import com.bubblefish.forceheroes.common.extensions.KeyUpdatePacket;
import com.bubblefish.forceheroes.item.TheFlashArmor;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.network.ServerSidePacketRegistry;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

public class ForceHeroes implements ModInitializer {
    public static final String MOD_ID = "forceheroes"; //Creating main project MOD_ID constant
    public static final ItemGroup GROUP = FabricItemGroupBuilder.build(new Identifier(MOD_ID, "group"),
            () -> new ItemStack(TheFlashArmor.THE_FLASH_CHESTPLATE)); //Creating main item group
    public static final Identifier KEY_UPDATE_PACKET_ID = new Identifier(MOD_ID, "key_update");

    @Override
    public void onInitialize() {
        TheFlashArmor.init(); //Registry The Flash Armor

        ServerSidePacketRegistry.INSTANCE.register(KEY_UPDATE_PACKET_ID, KeyUpdatePacket::onRecieve);
    }
}
