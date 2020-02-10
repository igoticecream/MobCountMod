package eu.minemania.mobcountmod.counter;

import eu.minemania.mobcountmod.config.Configs;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.mob.AbstractSkeletonEntity;
import net.minecraft.entity.mob.BlazeEntity;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.entity.mob.EndermanEntity;
import net.minecraft.entity.mob.EndermiteEntity;
import net.minecraft.entity.mob.GhastEntity;
import net.minecraft.entity.mob.GuardianEntity;
import net.minecraft.entity.mob.IllagerEntity;
import net.minecraft.entity.mob.PhantomEntity;
import net.minecraft.entity.mob.RavagerEntity;
import net.minecraft.entity.mob.ShulkerEntity;
import net.minecraft.entity.mob.SilverfishEntity;
import net.minecraft.entity.mob.SlimeEntity;
import net.minecraft.entity.mob.SpiderEntity;
import net.minecraft.entity.mob.VexEntity;
import net.minecraft.entity.mob.WitchEntity;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.entity.passive.AbstractTraderEntity;
import net.minecraft.entity.passive.BatEntity;
import net.minecraft.entity.passive.BeeEntity;
import net.minecraft.entity.passive.CatEntity;
import net.minecraft.entity.passive.ChickenEntity;
import net.minecraft.entity.passive.CowEntity;
import net.minecraft.entity.passive.DolphinEntity;
import net.minecraft.entity.passive.FishEntity;
import net.minecraft.entity.passive.FoxEntity;
import net.minecraft.entity.passive.HorseBaseEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.passive.OcelotEntity;
import net.minecraft.entity.passive.PandaEntity;
import net.minecraft.entity.passive.ParrotEntity;
import net.minecraft.entity.passive.PigEntity;
import net.minecraft.entity.passive.PolarBearEntity;
import net.minecraft.entity.passive.RabbitEntity;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.entity.passive.SnowGolemEntity;
import net.minecraft.entity.passive.SquidEntity;
import net.minecraft.entity.passive.TurtleEntity;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Box;

public class MobCounter {
	private int radius;
	private Box boundingBox;
	private int hRadius; //radius for hostiles
	private Box hostileBB;

	public MobCounter(boolean isStaff) {
		Configs.Generic.XP5.setBooleanValue(false);;
		this.radius = 16;
		
		if (isStaff) {
			this.hRadius = 25;
		} else {
			this.hRadius = 16;
		}
		
		this.boundingBox = new Box(0, 0, 0, 0, 0, 0);
		this.hostileBB = new Box(0, 0, 0, 0, 0, 0);
	}

	public int countEntity(int num)	{
		MinecraftClient mc = MinecraftClient.getInstance();
		
		switch (num) {
		case 0: return mc.world.getNonSpectatingEntities(ChickenEntity.class, boundingBox).size();
		case 1: return mc.world.getNonSpectatingEntities(PigEntity.class, boundingBox).size();
		case 2: return mc.world.getNonSpectatingEntities(SheepEntity.class, boundingBox).size();
		case 3: return mc.world.getNonSpectatingEntities(CowEntity.class, boundingBox).size();
		case 4: return mc.world.getNonSpectatingEntities(HorseBaseEntity.class, boundingBox).size();
		case 5: return mc.world.getNonSpectatingEntities(RabbitEntity.class, boundingBox).size();
		case 6: return mc.world.getNonSpectatingEntities(WolfEntity.class, boundingBox).size();
		case 7: return mc.world.getNonSpectatingEntities(OcelotEntity.class, boundingBox).size();
		case 8: return mc.world.getNonSpectatingEntities(ParrotEntity.class, boundingBox).size();
		case 9: return mc.world.getNonSpectatingEntities(BatEntity.class, boundingBox).size();
		case 10: return mc.world.getNonSpectatingEntities(CatEntity.class, boundingBox).size();
		case 11: return mc.world.getNonSpectatingEntities(IronGolemEntity.class, boundingBox).size();
		case 12: return mc.world.getNonSpectatingEntities(SnowGolemEntity.class, boundingBox).size();
		case 13: return mc.world.getNonSpectatingEntities(PlayerEntity.class, boundingBox).size() - 1;
		case 14: return mc.world.getNonSpectatingEntities(FishEntity.class, boundingBox).size();
		case 15: return mc.world.getNonSpectatingEntities(AbstractTraderEntity.class, boundingBox).size();
		case 16: return mc.world.getNonSpectatingEntities(DolphinEntity.class, boundingBox).size();
		case 17: return mc.world.getNonSpectatingEntities(FoxEntity.class, boundingBox).size();
		case 18: return mc.world.getNonSpectatingEntities(PandaEntity.class, boundingBox).size();
		case 19: return mc.world.getNonSpectatingEntities(PolarBearEntity.class, boundingBox).size();
		case 20: return mc.world.getNonSpectatingEntities(SquidEntity.class, boundingBox).size();
		case 21: return mc.world.getNonSpectatingEntities(TurtleEntity.class, boundingBox).size();
		case 39: return mc.world.getNonSpectatingEntities(BeeEntity.class, boundingBox).size();
		
		case 22: return mc.world.getNonSpectatingEntities(ZombieEntity.class, hostileBB).size();
		case 23: return mc.world.getNonSpectatingEntities(BlazeEntity.class, hostileBB).size();
		case 24: return mc.world.getNonSpectatingEntities(EndermanEntity.class, hostileBB).size();
		case 25: return mc.world.getNonSpectatingEntities(SpiderEntity.class, hostileBB).size();
		case 26: return mc.world.getNonSpectatingEntities(CreeperEntity.class, hostileBB).size();
		case 27: return mc.world.getNonSpectatingEntities(WitchEntity.class, hostileBB).size();
		case 28: return mc.world.getNonSpectatingEntities(EndermiteEntity.class, hostileBB).size();
		case 29: return mc.world.getNonSpectatingEntities(SlimeEntity.class, hostileBB).size();
		case 30: return mc.world.getNonSpectatingEntities(GuardianEntity.class, hostileBB).size();
		case 31: return mc.world.getNonSpectatingEntities(IllagerEntity.class, hostileBB).size();
		case 32: return mc.world.getNonSpectatingEntities(GhastEntity.class, hostileBB).size();
		case 33: return mc.world.getNonSpectatingEntities(PhantomEntity.class, hostileBB).size();
		case 34: return mc.world.getNonSpectatingEntities(RavagerEntity.class, hostileBB).size();
		case 35: return mc.world.getNonSpectatingEntities(ShulkerEntity.class, hostileBB).size();
		case 36: return mc.world.getNonSpectatingEntities(SilverfishEntity.class, hostileBB).size();
		case 37: return mc.world.getNonSpectatingEntities(AbstractSkeletonEntity.class, hostileBB).size();
		case 38: return mc.world.getNonSpectatingEntities(VexEntity.class, hostileBB).size();
		}
		return 0;
	}

	public void updateBB() {
		PlayerEntity player = MinecraftClient.getInstance().player;
		
		int x = (int)player.getX();
		int y = (int)player.getY();
		int z = (int)player.getZ();
		
		this.boundingBox = new Box(x - this.radius, y - this.radius, z - this.radius, x + this.radius, y + this.radius, z + this.radius);
	}

	public void updateHostileBB() {
		if (!Configs.Generic.XP5.getBooleanValue()) {
			PlayerEntity player = MinecraftClient.getInstance().player;
			
			int x = (int)player.getX();
			int y = (int)player.getY();
			int z = (int)player.getZ();
			
			this.hostileBB = new Box(x - this.hRadius, y - this.hRadius, z - this.hRadius, x + this.hRadius, y + this.hRadius, z + this.hRadius);
		}
	}

	public int getRadius() {
		return this.radius;
	}

	public void increaseRadius(boolean staff) {
		if (staff) {
			if (this.radius < 100) {
				this.radius++;
			}
		} else {
			if (this.radius < 16) {
				this.radius++;
			}
		}
	}

	public void decreaseRadius() {
		if (this.radius > 0) {
			this.radius--;
		}
	}

	public int getHRadius() {
		return this.hRadius;
	}

	public void increaseHRadius(boolean staff) {
		if (staff) {
			if (this.hRadius < 100) {
				this.hRadius++;
			}
		} else {
			if (this.hRadius < 16) {
				this.hRadius++;
			}
		}
	}

	public void decreaseHRadius() {
		if (this.hRadius > 0) {
			this.hRadius--;
		}
	}

	public void setXP5(boolean isOn) {
		if (isOn) {
			this.setXP5bounding();
		}
	}

	private void setXP5bounding() {
		this.hostileBB = new Box(5229, 5, -4700, 5250, 34, -4692);
	}
}
