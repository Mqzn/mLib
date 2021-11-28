package dev.mqzn.lib.utils;

import com.google.common.base.Objects;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;

public final class MessageBuilder {

    private final TextComponent text;

    private MessageBuilder(String msg) {
        text = new TextComponent(Translator.color(msg));
    }

    public static MessageBuilder create(String msg) {
        return new MessageBuilder(msg);
    }

    public MessageBuilder append(String msg) {
        text.addExtra(new TextComponent(Translator.color(msg)));
        return this;
    }

    public  MessageBuilder appendHover(String msg, String hoverMsg) {

        TextComponent txt = new TextComponent(Translator.color(msg));

        txt.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
                new ComponentBuilder(Translator.color(hoverMsg)).create()));

        text.addExtra(txt);

        return this;
    }

    public MessageBuilder appendClickable(String msg, String hoverMsg, ClickEvent.Action action, String actionValue) {

        TextComponent txt = new TextComponent(msg);

        txt.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
                new ComponentBuilder(Translator.color(hoverMsg)).create()));

        txt.setClickEvent(new ClickEvent(action, actionValue));

        text.addExtra(txt);

        return this;
    }


    public TextComponent getText() {
        return text;
    }

    public void send(Player player) {
        player.spigot().sendMessage(text);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MessageBuilder)) return false;
        MessageBuilder that = (MessageBuilder) o;
        return Objects.equal(getText(), that.getText());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getText());
    }


}
