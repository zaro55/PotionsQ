package de.zaro.potionsq;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class PotionsQClient implements ClientModInitializer {

    private boolean wasDown = false;

    @Override
    public void onInitializeClient() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            boolean down = client.options.keyDrop.isDown();

            if (down && !wasDown) {
                tryThrowPotion(client);
            }

            wasDown = down;
        });
    }

    private void tryThrowPotion(Minecraft client) {
        if (!(client.screen instanceof AbstractContainerScreen<?> screen)) {
            return;
        }

        Slot hovered = screen.getSlotUnderMouse();

        if (hovered == null) {
            return;
        }

        ItemStack stack = hovered.getItem();

        if (stack.isEmpty() || !stack.is(Items.POTION)) {
            return;
        }

        client.gameMode.handleInventoryMouseClick(
                screen.getMenu().containerId,
                hovered.index,
                0,
                net.minecraft.world.inventory.ClickType.THROW,
                client.player
        );

        client.setScreen(null);
    }
}
