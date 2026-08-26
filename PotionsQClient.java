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
        if (client.player == null || client.screen == null) return;
        if (!(client.screen instanceof AbstractContainerScreen<?> screen)) return;

        double mouseX = client.mouseHandler.xpos() * client.getWindow().getGuiScaledWidth()
                / (double) client.getWindow().getScreenWidth();
        double mouseY = client.mouseHandler.ypos() * client.getWindow().getGuiScaledHeight()
                / (double) client.getWindow().getScreenHeight();

        Slot hovered = screen.getSlotUnderMouse();
        if (hovered == null) return;

        ItemStack stack = hovered.getItem();
        if (stack.isEmpty() || !stack.is(Items.POTION)) return;

        // Vanilla container click: pick the potion up, close the inventory,
        // then drop the held stack as the normal Q action would.
        int slotId = hovered.index;
        int containerId = screen.getMenu().containerId;

        client.gameMode.handleInventoryMouseClick(
                containerId, slotId, 0,
                net.minecraft.world.inventory.ClickType.PICKUP,
                client.player
        );

        client.screen.onClose();

        client.gameMode.handleInventoryMouseClick(
                client.player.inventoryMenu.containerId,
                0, 0,
                net.minecraft.world.inventory.ClickType.THROW,
                client.player
        );
    }
}
