package com.kornas.mybookcase.repository;

import com.kornas.mybookcase.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {

    Book findByBid(Long bid);

    List<Book> findByUser(String user);

}

