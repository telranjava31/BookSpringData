package telran.book.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import telran.book.model.Book;

public interface BookRepository extends JpaRepository<Book, Long> {

	List<Book> findByPublisherPublisherName(String publisherName);
	
	List<Book> findByAuthorsName(String authorName);
	
	
}
