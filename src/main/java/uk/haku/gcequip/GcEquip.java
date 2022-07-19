package uk.haku.gcequip;

import emu.grasscutter.plugin.Plugin;
import uk.haku.gcequip.commands.*;


/**
 * The Grasscutter plugin template.
 * This is the main class for the plugin.
 */
public final class GcEquip extends Plugin {
    /* Turn the plugin into a singleton. */
    private static GcEquip instance;

    /**
     * Gets the plugin instance.
     * @return A plugin singleton.
     */
    public static GcEquip getInstance() {
        return instance;
    }
    
    /**
     * This method is called immediately after the plugin is first loaded into system memory.
     */
    @Override public void onLoad() {
        // Log a plugin status message.
        this.getLogger().info("The example plugin has been loaded.");
    }

    /**
     * This method is called before the servers are started, or when the plugin enables.
     */
    @Override public void onEnable() {
        // Register commands.
        this.getHandle().registerCommand(new EquipCommand());

        // Log a plugin status message.
        this.getLogger().info("The GcEquip plugin has been enabled.");
    }

    /**
     * This method is called when the plugin is disabled.
     */
    @Override public void onDisable() {
        // Log a plugin status message.
        this.getLogger().info("The GcEquip plugin has been disabled.");
    }
}
