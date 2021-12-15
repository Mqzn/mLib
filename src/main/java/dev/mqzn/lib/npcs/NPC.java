package dev.mqzn.lib.npcs;

import lombok.Data;
import lombok.EqualsAndHashCode;
import net.minecraft.server.v1_8_R3.DataWatcher;
import net.minecraft.server.v1_8_R3.EntityPlayer;

@Data
@EqualsAndHashCode(callSuper = false)
public class NPC {

    private static int entityID = 0;
    private int id;
    private final EntityPlayer entity;
    private DataWatcher dataWatcher;
    private final SkinData skinData;

    public NPC(EntityPlayer entityPlayer, SkinData skinData) {
        this.entity = entityPlayer;
        this.setDataWatcher();
        entityID++;
        this.id = entityID;
        this.skinData = skinData;
    }


    private void setDataWatcher() {
        DataWatcher w = entity.getDataWatcher();
        w.watch(10, (byte)127);
        this.dataWatcher = w;
    }



}
