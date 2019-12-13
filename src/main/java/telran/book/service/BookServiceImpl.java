package telran.book.service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import telran.book.dao.AuthorRepository;
import telran.book.dao.BookRepository;
import telran.book.dao.PublisherRepository;
import telran.book.dto.AuthorDto;
import telran.book.dto.BookDto;
import telran.book.model.Author;
import telran.book.model.Book;
import telran.book.model.Publisher;

@Service
public class BookServiceImpl implements BookService {
	
	@Autowired
	BookRepository bookRepository;
	
	@Autowired
	AuthorRepository authorRepository;
	
	@Autowired
	PublisherRepository publisherRepository;

	@Override
	@Transactional
	public boolean addBook(BookDto bookDto) {
		System.out.println(bookDto.getIsbn());
		if (bookRepository.existsById(bookDto.getIsbn())) {
			return false;
		}
		//Publisher
		String publisherName = bookDto.getPublisher();
		Publisher publisher = publisherRepository.findById(publisherName)
				.orElse(publisherRepository.save(new Publisher(publisherName)));
		//Set<Author>
		Set<AuthorDto> authorDtos = bookDto.getAuthors();
		Set<Author> authors = authorDtos.stream()
								.map(a -> authorRepository.findById(a.getName())
										.orElse(authorRepository
												.save(new Author(a.getName(), a.getBirthDate()))))
								.collect(Collectors.toSet());
		Book book = new Book(bookDto.getIsbn(), bookDto.getTitle(), authors, publisher);
		bookRepository.save(book);
		return true;
	}

	@Override
	public BookDto findBookByIsbn(long isbn) {
		Book book = bookRepository.findById(isbn).orElse(null);
		if (book == null) {
			return null;
		}
		return bookToBookDto(book);
	}

	@Override
	@Transactional
	public BookDto removeBook(long isbn) {
		Book book = bookRepository.findById(isbn).orElse(null);
		if (book == null) {
			return null;
		}
		bookRepository.delete(book);
		return bookToBookDto(book);
	}
	
	private BookDto bookToBookDto(Book book) {
		Set<AuthorDto> authors = book.getAuthors().stream()
				.map(this::authorToAuthorDto)
				.collect(Collectors.toSet());
		return new BookDto(book.getIsbn(), book.getTitle(), authors, book.getPublisher().getPublisherName());
	}
	
	private AuthorDto authorToAuthorDto(Author author) {
		return new AuthorDto(author.getName(), author.getBirthDate());
	}

	@Override
	public Iterable<BookDto> findBooksByPublisher(String publisherName) {
//		Publisher publisher = publisherRepository.findById(publisherName).orElse(null);
//		if (publisher == null) {
//			return null;
//		}
//		List<BookDto> books = publisher.getBooks()
		return bookRepository.findByPublisherPublisherName(publisherName)
				.stream()
				.map(this::bookToBookDto)
				.collect(Collectors.toList());
		//return books;
	}

	@Override
	public Iterable<BookDto> findBooksByAuthor(String authorName) {
//		Author author = authorRepository.findById(authorName).orElse(null);
//		if (author == null) {
//			return null;
//		}
//		List<BookDto> books = author.getBooks()
		return bookRepository.findByAuthorsName(authorName)
				.stream()
				.map(this::bookToBookDto)
				.collect(Collectors.toList());
//		return books;
	}

	@Override
	public Iterable<AuthorDto> findBookAuthors(long isbn) {
		Book book = bookRepository.findById(isbn).orElse(null);
		if (book == null) {
			return null;
		}
		List<AuthorDto> authors = book.getAuthors()
				.stream()
				.map(this::authorToAuthorDto)
				.collect(Collectors.toList());
		return authors;
	}

	@Override
	public Iterable<String> findPublishersByAuthor(String authorName) {
//		return publisherRepository.findByBooksAuthorsName(authorName)
//				.stream()
//				.map(Publisher::getPublisherName)
//				.collect(Collectors.toSet());
		return publisherRepository.findByBooksAuthorsName(authorName);
	}

	@Override
	@Transactional
	public AuthorDto removeAuthor(String authorName) {
		Author author = authorRepository.findById(authorName).orElse(null);
		if (author == null) {
			return null;
		}
		bookRepository.findByAuthorsName(authorName).forEach(b -> bookRepository.delete(b));
		authorRepository.deleteById(authorName);
		return authorToAuthorDto(author);
	}

	@Override
	@Transactional
	public BookDto updateBook(long isbn, String title) {
		Book book = bookRepository.findById(isbn).orElse(null);
		if (book == null) {
			return null;
		}
		book.setTitle(title);
//		bookRepository.save(book);
		
		return bookToBookDto(book);
	}

}
