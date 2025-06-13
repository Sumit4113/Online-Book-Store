package com.base.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.base.entity.Book;

public interface BookRepository extends JpaRepository<Book, Long> {
	// Add custom query methods here if needed

	// Get distinct categories
	@Query("SELECT DISTINCT b.category FROM Book b")
	List<String> findDistinctCategory();

	// Get books by category
	List<Book> findByCategoryIgnoreCase(String category);

	List<Book> findByTitleContainingIgnoreCaseOrAuthorContainingIgnoreCase(String title, String author);

}
