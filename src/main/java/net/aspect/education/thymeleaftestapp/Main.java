package net.aspect.education.thymeleaftestapp;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;

/**
 * Просто класс для теста*/

public class Main {
    public static void main(String[] args) throws Exception{
        InputStream in = Main.class.getResourceAsStream("/static/book-descriptions/21 - Золотой храм - Юкио Мисима.txt");

        byte[] bu = in.readAllBytes();

        String str = new String(bu, StandardCharsets.UTF_8);
        System.out.println(str);

        in.close();
    }
}
