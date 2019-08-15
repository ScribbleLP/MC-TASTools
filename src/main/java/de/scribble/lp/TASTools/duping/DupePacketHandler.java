package de.scribble.lp.TASTools.duping;

import java.io.File;

import de.scribble.lp.TASTools.CommonProxy;
import de.scribble.lp.TASTools.ModLoader;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

public class DupePacketHandler implements IMessageHandler<DupePacket, IMessage>{

	@Override
	public IMessage onMessage(DupePacket message, MessageContext ctx) {
		if (ctx.side==Side.SERVER) {
			if(!FMLCommonHandler.instance().getMinecraftServerInstance().isDedicatedServer()) {
				File file= new File(Minecraft.getMinecraft().mcDataDir, "saves" + File.separator +Minecraft.getMinecraft().getIntegratedServer().getFolderName()+File.separator+"latest_dupe.txt");
				EntityPlayerMP player =ctx.getServerHandler().player;
				new DupeEvents().startStopping(player);
				new RefillingDupe().refill(file, player);
			}else {
				CommonProxy.logger.warn("Can't duplicate items on a server!");
			}
		}
		return null;
	}

}
