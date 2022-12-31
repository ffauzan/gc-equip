package uk.haku.gcequip.commands;

import emu.grasscutter.command.Command;
import emu.grasscutter.command.CommandHandler;
import emu.grasscutter.data.GameData;
import emu.grasscutter.data.excels.ItemData;
import emu.grasscutter.game.avatar.Avatar;
import emu.grasscutter.game.inventory.GameItem;
import emu.grasscutter.game.inventory.ItemType;
import emu.grasscutter.game.player.Player;

import java.util.List;

@Command(label = "equip", usage = "equip <weaponId>", aliases = {
        "eq" }, permission = "player.equip", permissionTargeted = "player.equip.others")
public final class EquipCommand implements CommandHandler {
    @Override
    public void execute(Player sender, Player targetPlayer, List<String> args) {
        GameItem targetItem;
        int itemId;
        Avatar activeAvatar;

        // Check if args is valid.
        if (args.size() != 1) {
            CommandHandler.sendMessage(sender, "Wrong args size. Usage: equip <weaponId>");
            return;
        }

        // Get item data.
        itemId = Integer.parseInt(args.get(0));
        ItemData itemData = GameData.getItemDataMap().get(itemId);

        // Check if item data is valid.
        if (itemData == null) {
            CommandHandler.sendMessage(sender, "Invalid weaponId");
            return;
        }

        // Check if item is weapon.
        if (itemData.getItemType() != ItemType.ITEM_WEAPON) {
            CommandHandler.sendMessage(sender, "Invalid weaponId");
            return;
        }

        // Set target player.
        Player player = sender;
        if (player == null) {
            CommandHandler.sendMessage(sender, "Invalid player");
            return;
        }

        if (targetPlayer != null) {
            player = targetPlayer;
        }

        // Get weapon from inventory and it is not equiped by other character.
        List<GameItem> items = player.getInventory().getItems().values().stream()
                .filter(item -> item.getItemId() == itemId)
                .filter(item -> !item.isEquipped())
                .toList();

        // Check if weapon is in inventory. Create new weapon item if not.
        if (items.size() == 0) {
            targetItem = new GameItem(itemId);
            targetItem.setRefinement(5);
            targetItem.setLevel(90);
            targetItem.setPromoteLevel(6);
        } else {
            targetItem = items.get(0);
        }

        // Get active avatar.
        activeAvatar = player.getTeamManager().getCurrentAvatarEntity().getAvatar();

        // Equip weapon.
        boolean isSuccess = activeAvatar.equipItem(targetItem, true);

        if (isSuccess == true) {
            CommandHandler.sendMessage(sender, "Succes equiping weapon " + targetItem.getItemId());
        } else {
            CommandHandler.sendMessage(sender, "Failed to equip weapon");
        }
    }
}
