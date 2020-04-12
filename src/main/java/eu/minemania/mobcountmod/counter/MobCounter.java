package eu.minemania.mobcountmod.counter;

import eu.minemania.mobcountmod.config.Configs;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Box;

public class MobCounter
{
    private int radius;
    private Box boundingBox;
    private int hRadius;
    private Box hostileBB;

    public MobCounter(boolean isStaff)
    {
        Configs.Generic.XP5.setBooleanValue(false);
        this.radius = 16;

        if (isStaff)
        {
            this.hRadius = 25;
        }
        else
        {
            this.hRadius = 16;
        }

        this.boundingBox = new Box(0, 0, 0, 0, 0, 0);
        this.hostileBB = new Box(0, 0, 0, 0, 0, 0);
    }

    public Box getPassiveBB()
    {
        return this.boundingBox;
    }

    public Box getHostileBB()
    {
        return this.hostileBB;
    }

    public void updateBB()
    {
        PlayerEntity player = MinecraftClient.getInstance().player;

        int x = (int)player.getX();
        int y = (int)player.getY();
        int z = (int)player.getZ();

        this.boundingBox = new Box(x - this.radius, y - this.radius, z - this.radius, x + this.radius, y + this.radius, z + this.radius);
    }

    public void updateHostileBB()
    {
        if (!Configs.Generic.XP5.getBooleanValue())
        {
            PlayerEntity player = MinecraftClient.getInstance().player;

            int x = (int)player.getX();
            int y = (int)player.getY();
            int z = (int)player.getZ();

            this.hostileBB = new Box(x - this.hRadius, y - this.hRadius, z - this.hRadius, x + this.hRadius, y + this.hRadius, z + this.hRadius);
        }
    }

    public int getRadius()
    {
        return this.radius;
    }

    public void increaseRadius(boolean staff)
    {
        if (staff)
        {
            if (this.radius < 100)
            {
                this.radius++;
            }
        }
        else
        {
            if (this.radius < 16)
            {
                this.radius++;
            }
        }
    }

    public void decreaseRadius()
    {
        if (this.radius > 0)
        {
            this.radius--;
        }
    }

    public int getHRadius()
    {
        return this.hRadius;
    }

    public void increaseHRadius(boolean staff)
    {
        if (staff)
        {
            if (this.hRadius < 100)
            {
                this.hRadius++;
            }
        }
        else
        {
            if (this.hRadius < 16)
            {
                this.hRadius++;
            }
        }
    }

    public void decreaseHRadius()
    {
        if (this.hRadius > 0)
        {
            this.hRadius--;
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
        this.hostileBB = new Box(5229, 5, -4700, 5250, 34, -4692);
    }
}