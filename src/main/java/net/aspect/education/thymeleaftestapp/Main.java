package net.aspect.education.thymeleaftestapp;

import net.aspect.education.thymeleaftestapp.db.entity.Author;
import net.aspect.education.thymeleaftestapp.db.entity.Book;
import net.aspect.education.thymeleaftestapp.db.service.bookservice.BookServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
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
