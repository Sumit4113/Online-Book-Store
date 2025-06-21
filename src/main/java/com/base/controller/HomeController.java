// controller/AuthController.java
package com.base.controller;

import java.util.LinkedHashSet;
import java.util.List;

import java.util.Set;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.base.entity.Book;
import com.base.entity.User;
import com.base.repository.UserRepository;
import com.base.service.BookService;

import jakarta.servlet.http.HttpServletResponse;

import org.springframework.ui.Model;
import org.springframework.util.StreamUtils;

@Controller
public class HomeController {

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Autowired
	private BookService bookService;

	@GetMapping("/")
	public String homePage(Model model) {
		List<String> category = bookService.getAllCategory();
		model.addAttribute("category", category);
		List<Book> books = bookService.getAllBooks();

		// Extract distinct categories (trimmed and lowercase if needed)
		Set<String> categories = books.stream().map(b -> b.getCategory().trim()).filter(c -> !c.isEmpty())
				.collect(Collectors.toCollection(LinkedHashSet::new));

		model.addAttribute("categories", categories);
		model.addAttribute("books", books);
		return "home";
	}

	// custom login page
	@RequestMapping("/login")
	public String loginPage(Model model, RedirectAttributes redirectAttributes) {

		User user = new User();

		model.addAttribute("user", user);
		redirectAttributes.addFlashAttribute("error", "Invalid credentials");
		return "login";
	}

	@GetMapping("/register")
	public String showRegisterForm(Model model) {
		model.addAttribute("user", new User());
		return "register";
	}

	@PostMapping("/register")
	public String processRegister(@ModelAttribute User user ,RedirectAttributes redirectAttribute) {
		try {
			user.setPassword(passwordEncoder.encode(user.getPassword()));
			user.setRole("ROLE_USER");
			userRepo.save(user);
			redirectAttribute.addFlashAttribute("success", "Registration successful! Please login.");
			return "redirect:/register";

		} catch (Exception e) {
			redirectAttribute.addFlashAttribute("error", "An error occurred: " + e.getMessage());
			return "redirect:/register";
		}
	}

//	@GetMapping("/image/{id}")
//	@ResponseBody
//	public ResponseEntity<byte[]> getBookImage(@PathVariable Long id) {
//		Book book = bookService.getBookById(id);
//		byte[] image = book.getImage(); // your Book entity's image byte[]
//		if (image == null || image.length == 0) {
//			return ResponseEntity.notFound().build();
//		}
//		// You can set MediaType based on your image type if you save it, or default to
//		// JPEG
//		return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(image);
//	}

//	@GetMapping("/pdf/{id}")
//	@ResponseBody
//	public ResponseEntity<byte[]> getBookPdf(@PathVariable Long id) {
//		Book book = bookService.getBookById(id);
//		byte[] pdf = book.getPdf();
//		if (pdf == null || pdf.length == 0) {
//			return ResponseEntity.notFound().build();
//		}
//		return ResponseEntity.ok().contentType(MediaType.APPLICATION_PDF).body(pdf);
//	}

	@GetMapping("/aboutpage")
	public String aboutUs() {

		return "about";
	}

	// Show books by category
	@GetMapping("/category/{category}")
	public String showBooksByCategory(@PathVariable String category, Model model) {
		List<Book> books = bookService.getBooksByCategory(category);
		model.addAttribute("category", category);
		model.addAttribute("books", books);
		return "books-by-category"; // loads books-by-category.html
	}

}
