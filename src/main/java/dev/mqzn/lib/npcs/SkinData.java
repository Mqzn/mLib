package dev.mqzn.lib.npcs;

import lombok.Data;

@Data
public class SkinData {

    private final String texture;
    private final String signature;

    public SkinData(String texture, String signature) {
        this.texture = texture;
        this.signature = signature;
    }

}
