package telran.book.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import telran.book.dto.AuthorDto;
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

	@DeleteMapping("/book/{isbn}")
	public BookDto removeBook(@PathVariable long isbn) {
		return bookService.removeBook(isbn);
	}

	@GetMapping("/book/{isbn}")
	public BookDto getBook(@PathVariable long isbn) {
		return bookService.findBookByIsbn(isbn);
	}

	@PutMapping("/book/{isbn}/{title}")
	public BookDto updateBook(@PathVariable long isbn, @PathVariable String title) {
		return bookService.updateBook(isbn, title);
	}

	@DeleteMapping("/author/{author}")
	public AuthorDto removeAuthor(@PathVariable String author) {
		return bookService.removeAuthor(author);
	}

	@GetMapping("/books/author/{author}")
	public Iterable<BookDto> findBooksByAuthor(@PathVariable String author) {
		return bookService.findBooksByAuthor(author);

	}

	@GetMapping("/books/publisher/{publisher}")
	public Iterable<BookDto> findBooksByPublisher(@PathVariable String publisher) {
		return bookService.findBooksByPublisher(publisher);

	}

	@GetMapping("/book/{isbn}/authors")
	public Iterable<AuthorDto> findBookAuthors(@PathVariable long isbn) {
		return bookService.findBookAuthors(isbn);

	}

	@GetMapping("/publishers/author/{author}")
	public Iterable<String> findPublishersByAuthor(@PathVariable String author) {
		return bookService.findPublishersByAuthor(author);
	}

}
