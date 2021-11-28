package dev.mqzn.lib.commands.api.parsers;

import com.google.common.base.Objects;
import dev.mqzn.lib.commands.CommandManager;

public class IP {


    private final String ip;

    private IP(String ip) {
        this.ip = ip;
    }

    @Override
    public String toString() {
        return ip;
    }

    public static IP from(String ip) {
        return new IP(ip);
    }

    public static boolean isIP(String ipArg) {
        return CommandManager.getInstance()
                .getArgumentParser(IP.class).matches(ipArg);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof IP)) return false;
        IP ip1 = (IP) o;
        return Objects.equal(this.toString(), ip1.toString());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(ip);
    }
}
