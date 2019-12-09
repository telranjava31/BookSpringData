package telran.book.service;

import telran.book.dto.AuthorDto;
import telran.book.dto.BookDto;

public interface BookService {
	
	boolean addBook(BookDto bookDto);
	
	BookDto findBookByIsbn(long isbn);
	
	BookDto removeBook(long isbn);
	
	Iterable<BookDto> findBooksByPublisher(String publisherName);
	
	Iterable<BookDto> findBooksByAuthor(String authorName);
	
	Iterable<AuthorDto> findBookAuthors(long isbn);
	
	Iterable<String> findPublishersByAuthor(String authorName);
	
	AuthorDto removeAuthor(String authorName);
	
	BookDto updateBook(long isbn, String title);

}
