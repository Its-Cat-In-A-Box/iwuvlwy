package net.ber.iwuvlwy.channel;

import net.ber.iwuvlwy.IWuvLwy;
import net.ber.iwuvlwy.channel.packet.ImageC2SPacket;
import net.ber.iwuvlwy.channel.packet.StringC2SPacket;
import net.ber.iwuvlwy.item.custom.InstaCameraFilmItem;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

public class PacketHandler {
    private static int id = 0;
    private static int id(){
        return id++;
    }
    public static SimpleChannel INSTANCE;

    public static void register(){
        SimpleChannel net = NetworkRegistry.ChannelBuilder
                .named(new ResourceLocation(IWuvLwy.MODID, "messages"))
                .networkProtocolVersion(() -> "1")
                .clientAcceptedVersions(s -> true)
                .serverAcceptedVersions(s -> true)
                .simpleChannel();

        INSTANCE = net;

        net.messageBuilder(ImageC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(ImageC2SPacket::new)
                .encoder(ImageC2SPacket::toBytes)
                .consumerMainThread(ImageC2SPacket::handle)
                .add();
    }

    public static <MSG> void sendToServer(MSG message){
        INSTANCE.sendToServer(message);
    }

    public static <MSG> void sendToPlayer(MSG message, ServerPlayer player){
        INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), message);
    }
}
