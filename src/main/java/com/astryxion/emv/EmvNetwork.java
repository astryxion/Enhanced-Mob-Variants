package com.astryxion.emv;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public final class EmvNetwork {
    public static final SimpleNetworkWrapper CHANNEL = NetworkRegistry.INSTANCE.newSimpleChannel(EnhancedMobVariants.MODID);

    private EmvNetwork() {}

    public static void init() {
        CHANNEL.registerMessage(VariantPacketHandler.class, VariantMessage.class, 0, Side.CLIENT);
    }

    public static void sync(Entity entity) {
        if (entity == null || entity.world.isRemote || !Registration.has(entity)) {
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
            Minecraft mc = Minecraft.getMinecraft();
            mc.addScheduledTask(() -> {
                if (mc.world == null) {
                    return;
                }
                Entity entity = mc.world.getEntityByID(message.entityId);
                if (entity != null) {
                    Registration.applyClient(entity, message.variant);
                }
            });
            return null;
        }
    }
}
