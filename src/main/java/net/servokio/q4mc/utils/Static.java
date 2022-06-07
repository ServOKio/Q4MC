package net.servokio.q4mc.utils;

import java.util.Random;

public class Static {
    public static String randomStr(int length){
        Random r = new Random();
        String characters = "qwertyuiopasdfghjklzxcvbnm";
        char[] text = new char[length];
        for (int i = 0; i < length; i++) text[i] = characters.charAt(r.nextInt(characters.length()));
        return new String(text);
    }
}
