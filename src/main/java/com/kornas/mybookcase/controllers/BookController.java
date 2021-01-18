package com.kornas.mybookcase.controllers;

import com.kornas.mybookcase.entity.Book;
import com.kornas.mybookcase.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(value = {"/web"})
public class BookController {

    private final BookRepository bookRepository;

    @Autowired
    public BookController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @GetMapping("/books")
    public String getBooksList(Model bookModel, Principal principal) {
        List<Book> bookList = bookRepository.findByUser(principal.getName());
        bookModel.addAttribute("book", bookList);

        return "books";
    }

    @GetMapping("/addBook")
    public String addBook(Model bookModel, Book book) {
        bookModel.addAttribute("book", new Book());

        return "addBook";
    }

    @GetMapping("/addBook/{error}")
    public String addBook(Model bookModel, @PathVariable(value = "error") String error, Book book) {
        bookModel.addAttribute("book", new Book());

        String errorMessage = "Posiadasz już książkę o tym samym tytule '" + error + "'";
        bookModel.addAttribute("error", errorMessage);

        return "addBook";
    }

    //ważna kolejność parametrów, BindingResult po walidowanym obiekcie
    @PostMapping("/addBook")
    public String saveBook(@ModelAttribute @Valid Book book, BindingResult bindingResult, Principal principal) {
        boolean flag = true;

        if (bindingResult.hasErrors()) {
            return "addBook";
        }

        //sprawdzenie czy nie istnieje już książka o takim samym autorze i tytule
        for (Book iBook : bookRepository.findByUser(principal.getName())) {
            if (iBook.getBookName().equals(book.getBookName()) && iBook.getBookAuthor().equals(book.getBookAuthor())) {
                flag = false;
                break;
            }
        }

        if (flag) {
            book.setUser(principal.getName());
            bookRepository.save(book);
            return "redirect:/web/books";
        } else return "redirect:/web/addBook/" + book.getBookName();
    }

    @GetMapping("/editBook/{bid}")
    public String editBook(@PathVariable(value = "bid") Long bid, Model bookModel, Book book) {
        Optional<Book> bookFromDB = bookRepository.findById(bid);
        if (bookFromDB.isPresent()) {
            Book editedBook = bookFromDB.get();
            editedBook.setBid(bid);
            bookModel.addAttribute("book", editedBook);
        }
        return "editBook";
    }

    @GetMapping("/editBook/{bid}/{error}")
    public String editBook(@PathVariable(value = "bid") Long bid, Model bookModel, @PathVariable(value = "error") String error, Book book) {
        Optional<Book> bookFromDB = bookRepository.findById(bid);
        if (bookFromDB.isPresent()) {
            Book editedBook = bookFromDB.get();
            editedBook.setBid(bid);

            String errorMessage = "Posiadasz już inną o tym samym tytule '" + error + "'";
            bookModel.addAttribute("error", errorMessage);

            bookModel.addAttribute("book", editedBook);
            bookModel.addAttribute("error", error);
        }
        //w formularzu hidden input dla pola id i user
        return "editBook";
    }

    @PostMapping("/editBook")
    public String updateBook(@ModelAttribute @Valid Book book, BindingResult bindingResult, Principal principal) {
        boolean flag = true;

        if (bindingResult.hasErrors()) {
            return "editBook";
        }

        //sprawdzenie czy nie istnieje już książka o takim samym autorze i tytule ale różnym id
        for (Book iBook : bookRepository.findByUser(principal.getName())) {
            if (iBook.getBookName().equals(book.getBookName()) && iBook.getBookAuthor().equals(book.getBookAuthor())) {
                //jeśli id różne to użytkownik zmienia dane wybranej książki na dane innej książki, która już istnieje
                //jeśli id takie samo edytowana aktualna książka, więc błąd ignorowany
                if (!iBook.getBid().equals(book.getBid())) {
                    flag = false;
                    break;
                }
            }
        }

        //jeśli zalogowany użytkownik niezgodny z polem user książki edycja niedozwolona
        if (principal.getName().equals(book.getUser())) {
            if (flag) {
                Book editedBook = bookRepository.getOne(book.getBid());
                editedBook.setBookAuthor(book.getBookAuthor());
                editedBook.setBookName(book.getBookName());
                editedBook.setBookYear(book.getBookYear());
                editedBook.setBookRate(book.getBookRate());
                editedBook.setBookRead(book.getBookRead());
                bookRepository.save(editedBook);

                return "redirect:/web/books";
            } else return "redirect:/web/editBook/" + book.getBid() + "/" + book.getBookName();
        } else return "redirect:/web/books";
    }

    @GetMapping("/deleteBook/{bid}")
    public String deleteBook(@PathVariable(value = "bid") Long bid, Principal principal) {
        Book book = bookRepository.findByBid(bid);
        if (book != null && principal.getName().equals(book.getUser())) {
            bookRepository.deleteById(bid);
        }
        return "redirect:/web/books";
    }

    @GetMapping
    public String index() {
        return "redirect:/web/books";
    }
}

