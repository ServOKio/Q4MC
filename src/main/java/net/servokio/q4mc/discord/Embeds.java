package net.servokio.q4mc.discord;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.servokio.q4mc.Config;
import net.servokio.q4mc.MainDC;
import net.servokio.q4mc.utils.ServerInfo;

import java.awt.*;
import java.time.Instant;

public class Embeds {
    public MessageEmbed info(){
        EmbedBuilder embed = new EmbedBuilder();
        embed.setColor(new Color(0xCE0405));
        embed.setTitle(Config.SERVER_NAME);
        embed.setDescription("Version: 1.0.8");
        return embed.build();
    }

    public MessageEmbed help(){
        EmbedBuilder embed = new EmbedBuilder();
        embed.setColor(new Color(0xCE0405));
        embed.setTitle(Config.SERVER_NAME);
        embed.setDescription("Используйте слеш `/` для выбора команды");
        return embed.build();
    }

    public MessageEmbed startAttack(String addr, String method, String version, int at){
        EmbedBuilder embed = new EmbedBuilder();
        embed.setColor(new Color(0xCE0405));
        embed.setTitle(Config.SERVER_NAME);
        embed.setDescription("Начата атака на адрес `"+addr+"`");
        embed.addField("Метод", method, true);
        embed.addField("Версия", version, true);
        embed.addField("Время", Config.ATTACK_TIME+" секунд", true);
        embed.addField("Мощность", Config.ATTACK_POWER == -1 ? "Max" : String.valueOf(Config.ATTACK_POWER), true);
        embed.addField("№ атаки", String.valueOf(at), true);
        embed.setImage(Config.ATTACK_IMAGE_URL);
        embed.setTimestamp(Instant.now());
        return embed.build();
    }

    public MessageEmbed offlineServer(String addr){
        EmbedBuilder embed = new EmbedBuilder();
        embed.setColor(new Color(0xCE0405));
        embed.setTitle(Config.SERVER_NAME);
        embed.setDescription("Сервер `"+addr+"` недоступен");
        embed.setTimestamp(Instant.now());
        return embed.build();
    }

    public MessageEmbed serverInfo(ServerInfo info, String addr){
        EmbedBuilder embed = new EmbedBuilder();
        embed.setColor(new Color(0xCE0405));
        embed.setTitle(Config.SERVER_NAME);
        embed.setDescription("Сервер `"+addr+"` доступен\n```\n"+info.getMotd()+"\n```");
        embed.addField("Raw IP", info.getRawIp(), true);
        embed.addField("Protocol", info.getProtocol() + "("+ MainDC.getInstance().protocols.protocolsMapOb.get(info.getProtocol()) +")", true);
        embed.addField("Online", info.getNow() + "/" + info.getMaxPlayer(), true);
        embed.addField("Core", info.getInfo(), true);
        String url = info.getURLFavicon();
        if(url != null) embed.setThumbnail(url);
        embed.setTimestamp(Instant.now());
        return embed.build();
    }
}
