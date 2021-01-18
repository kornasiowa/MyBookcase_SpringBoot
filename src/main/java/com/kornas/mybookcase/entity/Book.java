package com.kornas.mybookcase.entity;

import javax.persistence.*;
import javax.validation.constraints.*;

@Entity
@Table(name = "book")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bid;

    @NotNull
    @NotEmpty (message = "Podaj poprawne dane autora")
    private String bookAuthor;

    @NotNull
    @NotEmpty (message = "Podaj poprawny tytuł")
    private String bookName;

    @NotNull (message = "Podaj poprawny rok")
    @Min (value = 1800, message = "Podaj poprawny rok")
    @Max (value = 2021, message = "Niedozwolona wartość")
    private Integer bookYear;

    @NotNull (message = "Wybierz ocenę z zakresu 1-5")
    @Min(value = 1, message = "Minimalna ocena wynosi 1")
    @Max(value = 5, message = "Maksymalna ocena wynosi 5")
    private Float bookRate;

    @NotNull(message = "Wybierz jedną z opcji")
    private Integer bookRead;

    private String user;

    public Book() {
    }

    public Book(String bookAuthor, String bookName, Integer bookYear, Float bookRate, Integer isRead, String user) {
        this.bookAuthor = bookAuthor;
        this.bookName = bookName;
        this.bookYear = bookYear;
        this.bookRate = bookRate;
        this.bookRead = isRead;
        this.user = user;
    }

    public Long getBid() {
        return bid;
    }

    public void setBid(Long bid) {
        this.bid = bid;
    }

    public String getBookAuthor() {
        return bookAuthor;
    }

    public void setBookAuthor(String bookAuthor) {
        this.bookAuthor = bookAuthor;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public Integer getBookYear() {
        return bookYear;
    }

    public void setBookYear(Integer bookYear) {
        this.bookYear = bookYear;
    }

    public Float getBookRate() {
        return bookRate;
    }

    public void setBookRate(Float bookRate) {
        this.bookRate = bookRate;
    }

    public Integer getBookRead() {
        return bookRead;
    }

    public void setBookRead(Integer bookRead) {
        this.bookRead = bookRead;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}