package telran.book.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import telran.book.model.Publisher;

public interface PublisherRepository extends JpaRepository<Publisher, String> {
	@Query("select distinct p.publisherName from Book b join b.publisher p join b.authors a where a.name=?1")
	List<String> findByBooksAuthorsName(String authorName);
}
