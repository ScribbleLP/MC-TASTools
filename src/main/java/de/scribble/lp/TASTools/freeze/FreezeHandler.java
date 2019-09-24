package de.scribble.lp.TASTools.freeze;

import java.util.ArrayList;
import java.util.List;

import cpw.mods.fml.common.FMLCommonHandler;
import net.minecraft.entity.player.EntityPlayerMP;

public class FreezeHandler {
	
	public static ArrayList<EntityDataStuff> entity = new ArrayList<EntityDataStuff>();
	
	public static List<EntityPlayerMP> playerMP;

	/**
	 * Indicates Server side freezing. Mouse Input freezing is indicated by "clientfrozen"
	 */
	private static boolean serverfrozen;
	/**
	 * Client isn't actually frozen, the server handles freezing... this is just to show that the mouse is diabled
	 */
	private static boolean clientfrozen;
	
	public static void startFreezeServer() {
		List<EntityPlayerMP> playerTemp = FMLCommonHandler.instance().getMinecraftServerInstance().getConfigurationManager().playerEntityList;
		serverfrozen=true;
		if (playerTemp.size() > 0) {
			entity=new ArrayList<EntityDataStuff>();
			for (int i = 0; i < (playerTemp.size()); i++) {
				entity.add(i, new EntityDataStuff(playerTemp.get(i).getDisplayName(), playerTemp.get(i).posX, playerTemp.get(i).posY, playerTemp.get(i).posZ,
								playerTemp.get(i).rotationPitch, playerTemp.get(i).rotationYaw,
								playerTemp.get(i).motionX, playerTemp.get(i).motionY, playerTemp.get(i).motionZ, playerTemp.get(i).fallDistance));
				playerTemp.get(i).capabilities.disableDamage=true;
			}
		}
		playerMP = playerTemp;
	}
	public static void startFreezeSetMotionServer(double X, double Y, double Z, float falldistance) {
		serverfrozen=true;
		List<EntityPlayerMP> playerTemp2 = FMLCommonHandler.instance().getMinecraftServerInstance().getConfigurationManager().playerEntityList;

		if (playerTemp2.size() > 0) {
			entity=new ArrayList<EntityDataStuff>();
			for (int i = 0; i < (playerTemp2.size()); i++) {
				entity.add(i, new EntityDataStuff(playerTemp2.get(i).getDisplayName(), playerTemp2.get(i).posX, playerTemp2.get(i).posY, playerTemp2.get(i).posZ,
								playerTemp2.get(i).rotationPitch, playerTemp2.get(i).rotationYaw,
								X, Y, Z, falldistance));
				playerTemp2.get(i).capabilities.disableDamage=true;
			}
		}
		playerMP = playerTemp2;
	}
	
	
	public static void stopFreezeServer() {
		List<EntityPlayerMP> playerMP = FMLCommonHandler.instance().getMinecraftServerInstance().getConfigurationManager().playerEntityList;
		
		if (playerMP.size() > 0) {
			for (int i = 0; i < (playerMP.size()); i++) {
				for (int o = 0; o < entity.size(); o++) {
					if (playerMP.get(i).getDisplayName().equals(entity.get(o).getPlayername())) {
						playerMP.get(i).setPositionAndUpdate(entity.get(o).getPosX(), entity.get(o).getPosY(),
								entity.get(o).getPosZ());
						playerMP.get(i).rotationPitch = entity.get(o).getPitch();
						playerMP.get(i).rotationYaw = entity.get(o).getYaw();
						
						if(!playerMP.get(i).capabilities.isCreativeMode) {
							playerMP.get(i).capabilities.disableDamage=false;
						}
						playerMP.get(i).motionX = entity.get(o).getMotionX();
						playerMP.get(i).motionY = entity.get(o).getMotionY();
						playerMP.get(i).motionZ = entity.get(o).getMotionZ();
						playerMP.get(i).velocityChanged = true;
						playerMP.get(i).fallDistance= entity.get(o).getFalldistance();
						entity.remove(o);
					}
				}
			}
		}
		serverfrozen=false;
	}
	/**
	 * Used if there is no player on the server, and if you want to stop freezing
	 */
	public static void stopFreezeServerNoUpdate() {
		serverfrozen=false;
	}
	
	public static void startFreezeClient() {
		clientfrozen=true;
	}
	public static void stopFreezeClient() {
		clientfrozen=false;
	}
	public static boolean isServerFrozen() {
		return serverfrozen;
	}
	public static boolean isClientFrozen() {
		return clientfrozen;
	}
}

