package dev.mqzn.lib.commands.test;

import dev.mqzn.lib.commands.MCommand;
import dev.mqzn.lib.npcs.NPCHandler;
import dev.mqzn.lib.npcs.SkinData;
import org.bukkit.entity.Player;

public class TestCommand extends MCommand {

    public TestCommand() {
        super("test", "");
    }

    @Override
    public void setInfo() {

        this.setDefaultActions(((sender, args) -> {

            if(!(sender instanceof Player)) {
                sender.sendMessage("ONLY PLAYERS CAN DO THIS SHIT NIGGER");
                return;
            }

            Player player = (Player) sender;
            SkinData data = new SkinData("ewogICJ0aW1lc3RhbXAiIDogMTYzOTMyODU2ODI3OCwKICAicHJvZmlsZUlkIiA6ICJkMWY2OTc0YzE2ZmI0ZjdhYjI1NjU4NzExNjM3M2U2NSIsCiAgInByb2ZpbGVOYW1lIiA6ICJGaW9saWVzdGEiLAogICJzaWduYXR1cmVSZXF1aXJlZCIgOiB0cnVlLAogICJ0ZXh0dXJlcyIgOiB7CiAgICAiU0tJTiIgOiB7CiAgICAgICJ1cmwiIDogImh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMmE2MzJiZTgyMzFjMWFiMWE0ZDUxZmQ3NjNmOGExYjUzZTVjNzI3YWEwMGUxODg0OWE4NTNkNDZjMDhjYWNkZSIKICAgIH0KICB9Cn0=", "WfdHweYxGjs4wr5verNf9IE0vY0GdVhMBxnUtJuhCV4Qg7d0elfhuqm8y6SjBuyZ4aVL9Ugq5T/ViNAX6eiFkxOttrVTmpD2SOOXoz8cLMV+IvDjg7NHus5XMLsp/zhgbw0nQIkaYL1ieYDou0UX/SE7o/YopyRjbxYuzvwlWquwWu8oO+SdNnA5XphOAC/WGGPQclcTaFe6Giefz6Gki+/CA4dmurPVPiWZ7hZYZaLJjKIwxc2kTZqluGrt2be6EhyxtoXT0ZE632UiLyl/5PhQqlEae1QcB8aCn6ddiIP+s5bf86mhDPUhECHsCPGIqiH0MJ95nFL9Ab/KIV7r0A/RL0bBdssfyE6Y8mbYLCkIkaVItcB8mbYXacUB1XKND72mIas2/eQvTxwbDDDNdLlwjjSA9JLQmiNi7QHt61AXCZikCZZ6EXYuwZpe5XbsDmRPZ+qEJqiBe1UiIkSAGnXXO657Vh5AYdTAjDzc4E8L0UP5ajzGpfUJEBTlKyqfMM7AQDBX7dAfP7Jmh8JSZqKpNZ33WtJ0+rhmxI+J/ULWD7qTQkjR/N9PdslRaBk/XpXDBrZUfvSnPcpNzQVTdsPxH+B/aLj9yBsnlmHHolFAlcu+he9TKgDaBqeHMgLIBKg/YRQ+ZIBEHdZNcPfFZQqB3HCdSs9gmSg3Ml867No=");


            NPCHandler.INSTANCE.createNPC(player.getLocation(), "&3&lShop", data);
        }));

    }

}
