package com.base.controller;

import com.base.entity.Book;
import com.base.service.BookService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserController {

	@Autowired
	private BookService bookService;

	@GetMapping("/books/pdf/{id}")
	public ResponseEntity<byte[]> viewBookPdf(@PathVariable Long id) {
		Book book = bookService.findById(id);
		if (book == null || book.getPdf() == null) {
			return ResponseEntity.notFound().build();
		}

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_PDF);
		headers.setContentDisposition(ContentDisposition.inline().filename(book.getTitle() + ".pdf").build());

		return new ResponseEntity<>(book.getPdf(), headers, HttpStatus.OK);
	}

}
