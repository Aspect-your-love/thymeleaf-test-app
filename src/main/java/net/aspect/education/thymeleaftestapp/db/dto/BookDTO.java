package net.aspect.education.thymeleaftestapp.db.dto;

import java.util.List;

public class BookDTO {
    private String name;
    private int year;
    private String filePath;
    private List<String> authorsName;

    public List<String> getAuthorsName() {
        return authorsName;
    }

    public void setAuthorsName(List<String> authorsName) {
        this.authorsName = authorsName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "BookDTO{" +
                "name='" + name + '\'' +
                ", year='" + year + '\'' +
                ", file_path='" + filePath + '\'' +
                ", authorsName=" + authorsName +
                '}';
    }
}
