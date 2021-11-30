package dev.mqzn.lib.commands.test;

import dev.mqzn.lib.commands.MCommand;
import dev.mqzn.lib.commands.exceptions.IllegalSyntaxProvidedException;
import dev.mqzn.lib.utils.Translator;
import org.bukkit.Bukkit;

public class TestCommand extends MCommand {

    public TestCommand() {
        super("test");
    }

    @Override
    public void setInfo() {

        // /test <hello>
        try {

            this.setDefaultActions(((sender, args) -> sender.sendMessage(Translator.color("&aThis is just a test command !"))));



            this.addAction("hello", ((sender, strings) ->
                    Bukkit.broadcastMessage("HELLO WORLD !")), false);

            this.addAction("hello [value]", ((sender, args) ->
                    Bukkit.broadcastMessage("HELLO " + args.get(1))), false);

            this.addAction("<message...>", (sender, args)-> {
                StringBuilder builder = new StringBuilder();
                for (int i = 0; i <args.size() ; i++) {
                    builder.append(args.get(i));
                    if(i != args.size()-1) {
                        builder.append(" ");
                    }
                }
                Bukkit.broadcastMessage(builder.toString());
            }, true);

        } catch (IllegalSyntaxProvidedException e) {
            e.printStackTrace();
        }

    }

}
