package net.servokio.q4mc.utils;

import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

import java.util.ArrayList;

public class Methods {
    public ArrayList<String> methodsString;

    public Methods(){
        this.methodsString = new ArrayList<>();
        this.init();
    }

    private void init() {
        System.out.println("Initialization of methods...");

        registerMethod("bigpacket");
        registerMethod("botjoiner");
        registerMethod("doublejoin");
        registerMethod("emptypacket");
        registerMethod("gayspam");
        registerMethod("handshake");
        registerMethod("invaliddata");
        registerMethod("invalidspoof");
        registerMethod("invalidnames");
        registerMethod("spoof");
        registerMethod("join");
        registerMethod("legacyping");
        registerMethod("legitnamejoin");
        registerMethod("localhost");
        registerMethod("pingjoin");
        registerMethod("longhost");
        registerMethod("longnames");
        registerMethod("nullping");
        registerMethod("ping");
        registerMethod("query");
        registerMethod("randompacket");
        registerMethod("bighandshake");
        registerMethod("unexpectedpacket");
        registerMethod("memory");

        System.out.println(methodsString.size()+" methods");
    }

    public OptionData getDiscordData(){
        OptionData data = new OptionData(OptionType.STRING, "method", "Метод атаки", true);
        for(String s : methodsString) data.addChoice(s, s);
        return data;
    }

    private void registerMethod(String method){
        this.methodsString.add(method);
    }
}
