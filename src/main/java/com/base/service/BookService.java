package com.base.service;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.base.entity.Book;
import com.base.repository.BookRepository;

@Service
public class BookService {

	@Autowired
	private BookRepository bookRepository;

	public List<Book> getAllBooks() {
		return bookRepository.findAll();
	}

	public Book getBookById(Long id) {
		return bookRepository.findById(id).orElseThrow(() -> new RuntimeException("Book not found"));
	}

	public Book saveBook(Book book) {
		return bookRepository.save(book);
	}

	/**
	 * Save a book with image and PDF file uploads. Store the actual file bytes
	 * inside the Book entity.
	 */
	public Book saveBookWithFiles(Book book, MultipartFile imageFile, MultipartFile pdfFile) {
		try {
			if (imageFile != null && !imageFile.isEmpty()) {
				book.setImage(imageFile.getBytes()); // Store image bytes in DB
			}

			if (pdfFile != null && !pdfFile.isEmpty()) {
				book.setPdf(pdfFile.getBytes()); // Store PDF bytes in DB
			}

			return bookRepository.save(book);
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("Failed to save files");
		}
	}

	public Book updateBook(Long id, Book updatedBook) {
		Book book = getBookById(id);
		book.setTitle(updatedBook.getTitle());
		book.setAuthor(updatedBook.getAuthor());
		book.setIsbn(updatedBook.getIsbn());
		book.setCategory(updatedBook.getCategory());

		book.setDescription(updatedBook.getDescription());
		// Optionally update image and pdf bytes here if needed
		return bookRepository.save(book);
	}

	public void deleteBook(Long id) {
		bookRepository.deleteById(id);
	}

	public Book findById(Long id) {
		return bookRepository.findById(id).orElse(null);
	}

	public List<Book> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	public List<String> getAllCategory() {
		return bookRepository.findDistinctCategory();
	}

	public List<Book> getBooksByCategory(String category) {
		return bookRepository.findByCategoryIgnoreCase(category);
	}
}


