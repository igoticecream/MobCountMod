package eu.minemania.mobcountmod.counter;

import eu.minemania.mobcountmod.config.Configs;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.monster.AbstractIllager;
import net.minecraft.entity.monster.AbstractSkeleton;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntityEndermite;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.monster.EntityGuardian;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.monster.EntityPhantom;
import net.minecraft.entity.monster.EntityPolarBear;
import net.minecraft.entity.monster.EntityShulker;
import net.minecraft.entity.monster.EntitySilverfish;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.monster.EntitySnowman;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.monster.EntityVex;
import net.minecraft.entity.monster.EntityWitch;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.AbstractFish;
import net.minecraft.entity.passive.AbstractHorse;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntityDolphin;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.entity.passive.EntityParrot;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.passive.EntityRabbit;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.entity.passive.EntityTurtle;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.AxisAlignedBB;

public class MobCounter
{
    private int radius;
    private AxisAlignedBB boundingBox;
    private int hRadius; //radius for hostiles
    private AxisAlignedBB hostileBB;

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

        this.boundingBox = new AxisAlignedBB(0, 0, 0, 0, 0, 0);
        this.hostileBB = new AxisAlignedBB(0, 0, 0, 0, 0, 0);
    }

    public int countEntity(int num)
    {
        Minecraft mc = Minecraft.getInstance();

        switch (num)
        {
        case 0: return mc.world.getEntitiesWithinAABB(EntityChicken.class, boundingBox).size();
        case 1: return mc.world.getEntitiesWithinAABB(EntityPig.class, boundingBox).size();
        case 2: return mc.world.getEntitiesWithinAABB(EntitySheep.class, boundingBox).size();
        case 3: return mc.world.getEntitiesWithinAABB(EntityCow.class, boundingBox).size();
        case 4: return mc.world.getEntitiesWithinAABB(AbstractHorse.class, boundingBox).size();
        case 5: return mc.world.getEntitiesWithinAABB(EntityRabbit.class, boundingBox).size();
        case 6: return mc.world.getEntitiesWithinAABB(EntityWolf.class, boundingBox).size();
        case 7: return mc.world.getEntitiesWithinAABB(EntityOcelot.class, boundingBox).size();
        case 8: return mc.world.getEntitiesWithinAABB(EntityParrot.class, boundingBox).size();
        case 9: return mc.world.getEntitiesWithinAABB(EntityBat.class, boundingBox).size();
        case 10: return mc.world.getEntitiesWithinAABB(EntityIronGolem.class, boundingBox).size();
        case 11: return mc.world.getEntitiesWithinAABB(EntitySnowman.class, boundingBox).size();
        case 12: return mc.world.getEntitiesWithinAABB(EntityPlayer.class, boundingBox).size() - 1;
        case 13: return mc.world.getEntitiesWithinAABB(AbstractFish.class, boundingBox).size();
        case 14: return mc.world.getEntitiesWithinAABB(EntityVillager.class, boundingBox).size();
        case 15: return mc.world.getEntitiesWithinAABB(EntityDolphin.class, boundingBox).size();
        case 16: return mc.world.getEntitiesWithinAABB(EntityPolarBear.class, boundingBox).size();
        case 17: return mc.world.getEntitiesWithinAABB(EntitySquid.class, boundingBox).size();
        case 18: return mc.world.getEntitiesWithinAABB(EntityTurtle.class, boundingBox).size();

        case 19: return mc.world.getEntitiesWithinAABB(EntityZombie.class, hostileBB).size();
        case 20: return mc.world.getEntitiesWithinAABB(EntityBlaze.class, hostileBB).size();
        case 21: return mc.world.getEntitiesWithinAABB(EntityEnderman.class, hostileBB).size();
        case 22: return mc.world.getEntitiesWithinAABB(EntitySpider.class, hostileBB).size();
        case 23: return mc.world.getEntitiesWithinAABB(EntityCreeper.class, hostileBB).size();
        case 24: return mc.world.getEntitiesWithinAABB(EntityWitch.class, hostileBB).size();
        case 25: return mc.world.getEntitiesWithinAABB(EntityEndermite.class, hostileBB).size();
        case 26: return mc.world.getEntitiesWithinAABB(EntitySlime.class, hostileBB).size();
        case 27: return mc.world.getEntitiesWithinAABB(EntityGuardian.class, hostileBB).size();
        case 28: return mc.world.getEntitiesWithinAABB(AbstractIllager.class, hostileBB).size();
        case 29: return mc.world.getEntitiesWithinAABB(EntityGhast.class, hostileBB).size();
        case 30: return mc.world.getEntitiesWithinAABB(EntityPhantom.class, hostileBB).size();
        case 31: return mc.world.getEntitiesWithinAABB(EntityShulker.class, hostileBB).size();
        case 32: return mc.world.getEntitiesWithinAABB(EntitySilverfish.class, hostileBB).size();
        case 33: return mc.world.getEntitiesWithinAABB(AbstractSkeleton.class, hostileBB).size();
        case 34: return mc.world.getEntitiesWithinAABB(EntityVex.class, hostileBB).size();
        }
        return 0;
    }

    public void updateBB()
    {
        EntityPlayer player = Minecraft.getInstance().player;

        int x = (int)player.posX;
        int y = (int)player.posY;
        int z = (int)player.posZ;

        this.boundingBox = new AxisAlignedBB(x - this.radius, y - this.radius, z - this.radius, x + this.radius, y + this.radius, z + this.radius);
    }

    public void updateHostileBB()
    {
        if (!Configs.Generic.XP5.getBooleanValue())
        {
            EntityPlayer player = Minecraft.getInstance().player;

            int x = (int)player.posX;
            int y = (int)player.posY;
            int z = (int)player.posZ;

            this.hostileBB = new AxisAlignedBB(x - this.hRadius, y - this.hRadius, z - this.hRadius, x + this.hRadius, y + this.hRadius, z + this.hRadius);
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
        this.hostileBB = new AxisAlignedBB(5229, 5, -4700, 5250, 34, -4692);
    }
}
