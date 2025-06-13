package com.base.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

import com.base.entity.Book;
import com.base.entity.User;
import com.base.service.BookService;
import com.base.service.UserService;

import jakarta.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/book")
public class BookController {

	@Autowired
	private BookService bookService;

	@Autowired
	private UserService userService;

	@GetMapping("/admin")
	public String showDashboard(Model model, Principal principal) {

		return "book/admine-dashboard";
	}

	@GetMapping("/show")
	public String getAllBooks(Model model) {
		model.addAttribute("book", bookService.getAllBooks());
		return "book/book-list"; // templates/books/book-list.html
	}

	@GetMapping("/add")
	public String showAddForm(Model model) {
		model.addAttribute("book", new Book());
		return "book/add-book";
	}

	@PostMapping("/add")
	public String addBook(@ModelAttribute Book book, @RequestParam("imageFile") MultipartFile imageFile,
			@RequestParam("pdfFile") MultipartFile pdfFile) {
		try {
			bookService.saveBookWithFiles(book, imageFile, pdfFile);
		} catch (Exception e) {
			e.printStackTrace();
			// Optionally add error handling and show error message
		}
		return "redirect:/book";
	}

	@GetMapping("/edit/{id}")
	public String showEditForm(@PathVariable Long id, Model model) {
		model.addAttribute("book", bookService.getBookById(id));
		return "book/edit-book";
	}

	@PostMapping("/edit/{id}")
	public String updateBook(@PathVariable Long id, @ModelAttribute Book book) {
		bookService.updateBook(id, book);
		return "redirect:/book"; // ✅ Fixed redirect
	}

	@GetMapping("/delete/{id}")
	public String deleteBook(@PathVariable Long id) {
		bookService.deleteBook(id);
		return "redirect:/book"; // ✅ Fixed redirect
	}

}
