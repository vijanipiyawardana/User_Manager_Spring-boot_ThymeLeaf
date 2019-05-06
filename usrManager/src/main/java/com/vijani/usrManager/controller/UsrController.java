package com.vijani.usrManager.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.vijani.usrManager.entity.Usr;
import com.vijani.usrManager.repository.UsrRepository;

@Controller
public class UsrController {
	
	@Autowired
	UsrRepository usrRepository;

	@GetMapping("/signup")
    public String showSignUpForm(ModelMap m) {
		Usr user = new Usr();
		m.addAttribute("user", user);
        return "add-user";
    }
	
	@PostMapping("/adduser")
    public String addUser(@Valid @ModelAttribute("user") Usr user, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "add-user";
        }
        //REST changes following lines 
        usrRepository.save(user);
        model.addAttribute("users", usrRepository.findAll());
        return "index";
    }
	
	@GetMapping("/edit/{id}")
	public String showUpdateForm(@PathVariable("id") long id, Model model) {
	    Usr user = usrRepository.findById(id)
	      .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
	     
	    model.addAttribute("user", user);
	    return "update-user";
	}
	
	@PostMapping("/update/{id}")
	public String updateUser(@PathVariable("id") long id, @Valid Usr user, 
	  BindingResult result, Model model) {
	    if (result.hasErrors()) {
	        user.setId(id);
	        return "update-user";
	    }
	    //here also update method in rest
	    usrRepository.save(user);
	    model.addAttribute("users", usrRepository.findAll());
	    return "index";
	}
	
	@GetMapping("/delete/{id}")
	public String deleteUser(@PathVariable("id") long id, Model model) {
	    Usr user = usrRepository.findById(id)
	      .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
	    //here also delete method in rest
	    usrRepository.delete(user);
	    model.addAttribute("users", usrRepository.findAll());
	    return "index";
	}
}
