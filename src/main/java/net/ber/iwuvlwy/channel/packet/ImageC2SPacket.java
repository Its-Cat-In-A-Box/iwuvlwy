package net.ber.iwuvlwy.channel.packet;

import com.mojang.blaze3d.platform.NativeImage;
import net.ber.iwuvlwy.IWuvLwy;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;
import org.apache.commons.lang3.ArrayUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Supplier;

public class ImageC2SPacket {
    private byte[] imgBytes;
    private String uuid;


    public ImageC2SPacket(byte[] bytes , String uuid){
        this.imgBytes = bytes;
        this.uuid = uuid;
    }
    public ImageC2SPacket(FriendlyByteBuf buf){

    }
    public void toBytes(FriendlyByteBuf buf){

    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier){
        NetworkEvent.Context ctx = supplier.get();
        ctx.enqueueWork(() -> {
            assert imgBytes != null;
            assert uuid != null;
            BufferedImage img;
            ByteArrayInputStream bais = new ByteArrayInputStream(imgBytes);
            try {
                img = ImageIO.read(bais);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            try {
                ImageIO.write(img, "jpg", new File(
                        String.valueOf(Path.of(
                                Objects.requireNonNull(Objects.requireNonNull(ctx.getSender()).getServer()).getServerDirectory().toString(),
                                IWuvLwy.MODID,
                                "img",
                                uuid + ".jpg"

                        ))
                ));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        return true;
    }
}
