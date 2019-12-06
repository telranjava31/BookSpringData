package telran.book.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import telran.book.dto.BookDto;
import telran.book.service.BookService;

@RestController
public class BookController {
	
	@Autowired
	BookService bookService;
	
	@PostMapping("/book")
	public boolean addBook(@RequestBody BookDto bookDto) {
		return bookService.addBook(bookDto);
	}

}
