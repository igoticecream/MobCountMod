package eu.minemania.mobcountmod.counter;

import eu.minemania.mobcountmod.config.Configs;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Box;

public class MobCounter
{
    private int radiusH;
    private Box boundingBoxH;
    private int radiusP;
    private Box boundingBoxP;

    public MobCounter()
    {
        Configs.Generic.XP5.setBooleanValue(false);

        this.radiusH = Configs.Generic.RADIUS_HOSTILE.getIntegerValue();
        this.radiusP = Configs.Generic.RADIUS_PASSIVE.getIntegerValue();

        this.boundingBoxH = new Box(0, 0, 0, 0, 0, 0);
        this.boundingBoxP = new Box(0, 0, 0, 0, 0, 0);
    }

    public Box getPassiveBB()
    {
        return this.boundingBoxP;
    }

    public Box getHostileBB()
    {
        return this.boundingBoxH;
    }

    public void updateBBP()
    {
        PlayerEntity player = MinecraftClient.getInstance().player;

        int x = (int) player.getX();
        int y = (int) player.getY();
        int z = (int) player.getZ();

        this.boundingBoxP = new Box(x - this.radiusP, y - this.radiusP, z - this.radiusP, x + this.radiusP, y + this.radiusP, z + this.radiusP);
    }

    public void updateBBH()
    {
        if (!Configs.Generic.XP5.getBooleanValue())
        {
            PlayerEntity player = MinecraftClient.getInstance().player;

            int x = (int) player.getX();
            int y = (int) player.getY();
            int z = (int) player.getZ();

            this.boundingBoxH = new Box(x - this.radiusH, y - this.radiusH, z - this.radiusH, x + this.radiusH, y + this.radiusH, z + this.radiusH);
        }
    }

    public int getRadiusP()
    {
        return this.radiusP;
    }

    public void increaseRadiusP()
    {
        radiusP++;
    }

    public void decreaseRadiusP()
    {
        if (this.radiusP > 0)
        {
            this.radiusP--;
        }
    }

    public int getRadiusH()
    {
        return this.radiusH;
    }

    public void increaseRadiusH()
    {
        this.radiusH++;
    }

    public void decreaseRadiusH()
    {
        if (this.radiusH > 0)
        {
            this.radiusH--;
        }
    }

    public void setRadius(int radius, boolean passive)
    {
        if (passive)
        {
            this.radiusP = radius;
        }
        else
        {
            this.radiusH = radius;
        }
    }

    public void setXP5(boolean isOn)
    {
        if (isOn)
        {
            this.setXP5bounding();
        }
    }

    private void setXP5bounding()
    {
        this.boundingBoxH = new Box(5229, 5, -4700, 5250, 34, -4692);
    }
}