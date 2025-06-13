package com.base.controller;



import com.base.entity.Book;
import com.base.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class SearchController {

	@Autowired
	private BookRepository bookRepository;

	@GetMapping("/search")
	public String searchBooks(@RequestParam("query") String query, Model model) {
		List<Book> searchResults = bookRepository.findByTitleContainingIgnoreCaseOrAuthorContainingIgnoreCase(query,
				query);
		model.addAttribute("books", searchResults);
		model.addAttribute("searchQuery", query);
		return "search-results"; // This is your Thymeleaf template
	}
}
