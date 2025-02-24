package codes.biscuit.skyblockaddons.mixins;

import codes.biscuit.skyblockaddons.SkyblockAddons;
import codes.biscuit.skyblockaddons.utils.Feature;
import net.minecraft.client.gui.GuiHopper;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.*;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(GuiHopper.class)
public abstract class MixinGuiHopper extends GuiContainer {

    public MixinGuiHopper(Container inventorySlotsIn) {
        super(inventorySlotsIn);
    }

    @Override
    protected void handleMouseClick(Slot slotIn, int slotId, int clickedButton, int clickType) {
        SkyblockAddons main = SkyblockAddons.getInstance();
        out:
        if (slotIn != null && main.getConfigValues().isEnabled(Feature.LOCK_SLOTS) &&
                main.getUtils().isOnSkyblock()) {
            int slotNum = slotIn.slotNumber;
            slotNum += 4; // for hoppers
            if (slotNum < 9) break out;
            if (main.getConfigValues().getLockedSlots().contains(slotNum)) {
                main.getUtils().playSound("note.bass", 0.5);
                return;
            }
        }
        super.handleMouseClick(slotIn, slotId, clickedButton, clickType);
    }
}
