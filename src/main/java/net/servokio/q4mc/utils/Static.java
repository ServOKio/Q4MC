package net.servokio.q4mc.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Random;

public class Static {
    public static String randomStr(int length){
        Random r = new Random();
        String characters = "qwertyuiopasdfghjklzxcvbnm";
        char[] text = new char[length];
        for (int i = 0; i < length; i++) text[i] = characters.charAt(r.nextInt(characters.length()));
        return new String(text);
    }

    public static String getRandomFromList(List<String> list){
        return list.get(new Random().nextInt(list.size()));
    }

    public static String addParameter(String URL, String name, String value) {
        int qpos = URL.indexOf('?');
        int hpos = URL.indexOf('#');
        char sep = qpos == -1 ? '?' : '&';
        String seg = sep + encodeUrl(name) + '=' + encodeUrl(value);
        return hpos == -1 ? URL + seg : URL.substring(0, hpos) + seg
                + URL.substring(hpos);
    }

    public static String encodeUrl(String url) { //говно много на сайтах, есть те которые до сих пор ссылки парсить не умеют
        try {
            return URLEncoder.encode(url, "UTF-8");
        } catch (UnsupportedEncodingException uee) {
            throw new IllegalArgumentException(uee);
        }
    }
}
