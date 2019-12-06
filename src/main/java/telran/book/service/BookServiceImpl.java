package telran.book.service;

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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BookDto removeBook(long isbn) {
		// TODO Auto-generated method stub
		return null;
	}

}
