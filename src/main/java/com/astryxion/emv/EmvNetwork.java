package com.astryxion.emv;

import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.world.World;

public final class EmvNetwork {
    public static final SimpleNetworkWrapper CHANNEL = NetworkRegistry.INSTANCE.newSimpleChannel(EnhancedMobVariants.MODID);

    private EmvNetwork() {}

    public static void init() {
        CHANNEL.registerMessage(VariantPacketHandler.class, VariantMessage.class, 0, Side.CLIENT);
    }

    public static void sync(Entity entity) {
        if (entity == null || entity.worldObj.isRemote || !Registration.has(entity)) {
            return;
        }
        CHANNEL.sendToAllAround(
            new VariantMessage(entity.getEntityId(), Registration.get(entity)),
            new NetworkRegistry.TargetPoint(entity.dimension, entity.posX, entity.posY, entity.posZ, 128.0)
        );
    }

    public static void syncTo(Entity entity, EntityPlayerMP player) {
        if (entity == null || player == null || !Registration.has(entity)) {
            return;
        }
        CHANNEL.sendTo(new VariantMessage(entity.getEntityId(), Registration.get(entity)), player);
    }

    public static class VariantMessage implements IMessage {
        private int entityId;
        private int variant;

        public VariantMessage() {}

        public VariantMessage(int entityId, int variant) {
            this.entityId = entityId;
            this.variant = variant;
        }

        @Override
        public void fromBytes(ByteBuf buf) {
            this.entityId = buf.readInt();
            this.variant = buf.readInt();
        }

        @Override
        public void toBytes(ByteBuf buf) {
            buf.writeInt(this.entityId);
            buf.writeInt(this.variant);
        }
    }

    public static class VariantPacketHandler implements IMessageHandler<VariantMessage, IMessage> {
        @Override
        public IMessage onMessage(VariantMessage message, MessageContext ctx) {
            World world = Minecraft.getMinecraft().theWorld;
            if (world == null) {
                return null;
            }
            Entity entity = world.getEntityByID(message.entityId);
            if (entity != null) {
                Registration.applyClient(entity, message.variant);
            }
            return null;
        }
    }
}
