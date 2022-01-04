package de.guntram.mcmod.antighost;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v1.ClientCommandManager;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.network.protocol.game.ServerboundPlayerActionPacket;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_G;

public class AntiGhost implements ClientModInitializer {
    static KeyMapping requestBlocks;
    
    @Override
    public void onInitializeClient() {
        final String category = "key.categories.antighost";
        requestBlocks = new KeyMapping("key.antighost.reveal", GLFW_KEY_G, category);
        KeyBindingHelper.registerKeyBinding(requestBlocks);
        ClientTickEvents.END_CLIENT_TICK.register(e -> keyPressed());
        ClientCommandManager.DISPATCHER.register(
                ClientCommandManager.literal("ghost").executes(c -> {
                    this.execute();
                    return 1;
                })
        );
    }

    public void keyPressed() {
        LocalPlayer player = Minecraft.getInstance().player;
        if (requestBlocks.consumeClick()) {
            this.execute();
            player.displayClientMessage(new TranslatableComponent("msg.request"), false);
        }
    }
    
    public void execute() {
        Minecraft mc = Minecraft.getInstance();
        ClientPacketListener connection = mc.getConnection();
        if (connection == null) {
            return;
        }

        BlockPos pos = mc.player.getOnPos();
        for (int dx = -4; dx <= 4; dx++) {
            for (int dy = -4; dy <= 4; dy++) {
                for (int dz = -4; dz <= 4; dz++) {
                    ServerboundPlayerActionPacket packet = new ServerboundPlayerActionPacket(
                            ServerboundPlayerActionPacket.Action.ABORT_DESTROY_BLOCK,
                            new BlockPos(pos.getX() + dx, pos.getY() + dy, pos.getZ() + dz),
                            Direction.UP       // with ABORT_DESTROY_BLOCK, this value is unused
                    );
                    connection.send(packet);
                }
            }
        }
    }
}
