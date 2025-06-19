package com.base.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.base.entity.ContactAddress;
import com.base.repository.ContactAddressRepository;

@Controller
@RequestMapping("/PremiumUser")
public class PremiumUser {
	
	@Autowired
	private ContactAddressRepository contactAddressRepository;


	@GetMapping("/primeum")
	public String showPrimium() {

		return "preuser/primiumpage";
	}

	@GetMapping("/PaymentUser")
	public String showPayment() {

		return "preuser/payment";
	}

	@PostMapping("/payment")
	public String contactAddress(@ModelAttribute ContactAddress address, Model model) {
		// Save address to database
		// addressService.save(address);
		 contactAddressRepository.save(address);

		model.addAttribute("message", "Address saved successfully!");
		return "preuser/payment";
	}

}
