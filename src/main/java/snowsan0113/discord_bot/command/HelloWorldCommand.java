package snowsan0113.discord_bot.command;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.channel.unions.MessageChannelUnion;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.utils.FileUpload;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;

public class HelloWorldCommand extends ListenerAdapter {

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        String cmd = event.getName();
        JDA jda = event.getJDA();
        Member member = event.getMember();
        MessageChannelUnion channel = event.getChannel();
        if ("helloworld".equalsIgnoreCase(cmd)) {
            try {
                BufferedImage img = new BufferedImage(650, 250, BufferedImage.TYPE_3BYTE_BGR);
                Graphics2D g = img.createGraphics();
                g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g.setColor(Color.DARK_GRAY);
                g.fillRect(0, 0, 650, 250);
                g.setColor(Color.WHITE);
                g.setFont(new Font("Noto Sans JP Black", Font.PLAIN, 30));
                g.drawString("ユーザーが参加しました", 15, 30);
                g.drawString("名前： " + member.getUser().getName(), 15, 70);
                g.drawString("アカウント参加日時： " + member.getTimeCreated(), 15, 110);
                g.dispose();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ImageIO.write(img, "png", baos);
                baos.flush();
                byte[] byte_data = baos.toByteArray();
                baos.close();
                channel.sendFiles(FileUpload.fromData(byte_data, "join.png")).queue();
            }catch(Exception e) {
                e.printStackTrace();
            }
        }
    }
}
