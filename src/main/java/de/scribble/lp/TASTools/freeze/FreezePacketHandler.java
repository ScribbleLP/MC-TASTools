package de.scribble.lp.TASTools.freeze;

import de.scribble.lp.TASTools.ModLoader;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

public class FreezePacketHandler implements IMessageHandler<FreezePacket, IMessage> {


	@Override
	public FreezePacket onMessage(FreezePacket msg, MessageContext ctx) {
		if (ctx.side == Side.SERVER) {
			EntityPlayerMP player =ctx.getServerHandler().player;
			ctx.getServerHandler().player.getServerWorld().addScheduledTask(() -> {
				if(player.canUseCommand(2, "freeze")) {
					if (msg.getMode() == 0) {
						if (msg.startstop()) {
							FreezeHandler.startFreezeServer();
							ModLoader.NETWORK.sendToAll(new FreezePacket(true));
						}
	
						else if (!msg.startstop()) {
							FreezeHandler.stopFreezeServer();
							ModLoader.NETWORK.sendToAll(new FreezePacket(false));
						}
					}
					else if(msg.getMode()==1) {
						if(!FreezeHandler.isServerFrozen()) {
							FreezeHandler.startFreezeServer();
							ModLoader.NETWORK.sendToAll(new FreezePacket(true));
						}
						else {
							FreezeHandler.stopFreezeServer();
							ModLoader.NETWORK.sendToAll(new FreezePacket(false));
						}
					}
				}
			});
		} else if (ctx.side == Side.CLIENT) {
			if (msg.startstop()) {
				FreezeHandler.startFreezeClient();
			}

			else if (!msg.startstop()) {
				FreezeHandler.stopFreezeClient();
			}
		}
		
		return null;
	}
}
