package net.ber.iwuvlwy.channel.packet;

import com.google.common.graph.Network;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class StringC2SPacket {
    public StringC2SPacket(){

    }
    public StringC2SPacket(FriendlyByteBuf buf){

    }
    public void toBytes(FriendlyByteBuf buf){

    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier){
        NetworkEvent.Context ctx = supplier.get();
        ctx.enqueueWork(() -> {
        ServerPlayer player = ctx.getSender();
            assert player != null;
            ServerLevel level = (ServerLevel) player.level();
        });
        return true;
    }
}
