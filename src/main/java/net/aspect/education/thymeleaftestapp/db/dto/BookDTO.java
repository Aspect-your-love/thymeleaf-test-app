package net.aspect.education.thymeleaftestapp.db.dto;

import java.util.List;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof BookDTO bookDTO)) return false;
        return year == bookDTO.year && Objects.equals(name, bookDTO.name) && Objects.equals(filePath, bookDTO.filePath) && Objects.equals(authorsName, bookDTO.authorsName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, year, filePath, authorsName);
    }


}
