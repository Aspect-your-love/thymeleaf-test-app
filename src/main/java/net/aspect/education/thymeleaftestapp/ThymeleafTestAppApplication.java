package net.aspect.education.thymeleaftestapp;

import net.aspect.education.thymeleaftestapp.db.entity.Author;
import net.aspect.education.thymeleaftestapp.db.entity.Book;
import net.aspect.education.thymeleaftestapp.db.service.bookservice.BookServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.nio.file.Path;
import java.nio.file.Paths;

@SpringBootApplication(scanBasePackages = "net.aspect.education.thymeleaftestapp")
public class ThymeleafTestAppApplication {


    public static void main(String[] args) {
        SpringApplication.run(ThymeleafTestAppApplication.class, args);
    }
}
