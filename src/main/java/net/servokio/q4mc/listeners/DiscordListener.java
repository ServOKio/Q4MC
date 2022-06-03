package net.servokio.q4mc.listeners;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.MessageBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import net.dv8tion.jda.api.events.interaction.command.CommandAutoCompleteInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.Command;
import net.servokio.q4mc.MainDC;
import net.servokio.q4mc.threads.AttackThread;
import net.servokio.q4mc.utils.Resolve;
import net.servokio.q4mc.utils.ServerInfo;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DiscordListener extends ListenerAdapter {

    @Override
    public void onReady(@NotNull ReadyEvent event) {
        MainDC.getInstance().onReady();
    }

    @Override
    public void onSlashCommandInteraction(@Nonnull SlashCommandInteractionEvent event) {
        if(event.getGuild() == null) return;

        if(event.getName().equals("info")){
            event.reply(new MessageBuilder().setEmbeds(MainDC.getInstance().discord.embeds.info()).build()).queue();
        } else if(event.getName().equals("help")){
            event.reply(new MessageBuilder().setEmbeds(MainDC.getInstance().discord.embeds.help()).build()).queue();
        } else if(event.getName().equals("resolve")) {
            if(isXyesos(event.getMember()) && nyxaiBebry()){
                event.reply(new MessageBuilder().setEmbeds(new EmbedBuilder().setImage("https://cdn.discordapp.com/attachments/967462195409608744/982336064846979102/312.gif").setColor(new Color(0xFF8B7F)).build()).build()).queue();
            } else {
                event.reply("Проверяю сервер...").queue(message -> {
                    String addr = event.getOption("address").getAsString();
                    ServerInfo info = Resolve.getServerInfo(addr);
                    if(info != null){
                        if(info.isOnline()){
                            message.editOriginal(new MessageBuilder().setEmbeds(MainDC.getInstance().discord.embeds.serverInfo(info, addr)).build()).queue();
                        } else message.editOriginal(new MessageBuilder().setEmbeds(MainDC.getInstance().discord.embeds.offlineServer(addr)).build()).queue();
                    } else message.editOriginal(new MessageBuilder().setEmbeds(MainDC.getInstance().discord.embeds.offlineServer(addr)).build()).queue();
                });
            }
        } else if(event.getName().equals("attack")) {
            if(isXyesos(event.getMember()) && nyxaiBebry()){
                event.reply(new MessageBuilder().setEmbeds(new EmbedBuilder().setImage("https://cdn.discordapp.com/attachments/967462195409608744/982336064846979102/312.gif").setColor(new Color(0xFF8B7F)).build()).build()).queue();
            } else {
                String addr = event.getOption("address").getAsString();
                int protocol = MainDC.main.protocols.protocolsMap.get(event.getOption("protocol").getAsString());
                String method = event.getOption("method").getAsString();

                event.reply("Проверяю сервер...").queue(message -> {
                    ServerInfo info = Resolve.getServerInfo(addr);
                    if(info == null || !info.isOnline()){
                        message.editOriginal(new MessageBuilder().setEmbeds(MainDC.getInstance().discord.embeds.offlineServer(addr)).build()).queue();
                    } else {
                        int atID = MainDC.getInstance().incAtt();
                        message.editOriginal(new MessageBuilder().setEmbeds(MainDC.getInstance().discord.embeds.startAttack(addr, method, event.getOption("protocol").getAsString(), atID)).build()).queue();
                        Thread th = new AttackThread(atID, addr, protocol, method);
                        th.start();
                        MainDC.getInstance().threadManager.addThread(th);
                    }
                });
            }
        }
    }

    @Override
    public void onCommandAutoCompleteInteraction(CommandAutoCompleteInteractionEvent event) {
        if (event.getName().equals("attack") && event.getFocusedOption().getName().equals("protocol")) {
            List<Command.Choice> options = new ArrayList<>();
            for(String s : MainDC.getInstance().protocols.availableString){
                if(s.startsWith(event.getFocusedOption().getValue())) options.add(new Command.Choice(s, s));
                if(options.size() >= 25) break;
            }
            event.replyChoices(options).queue();
        }
    }

    @Override
    public void onGuildJoin(@Nonnull GuildJoinEvent event){
        MainDC.getInstance().setupGuild(event.getGuild());
    }

    private boolean isXyesos(Member member){
        return member.getId().equals("688376584138522696");
    }

    private boolean nyxaiBebry(){
        Random rand = new Random();
        return rand.nextInt((2) + 1) != 2;
    }
}
