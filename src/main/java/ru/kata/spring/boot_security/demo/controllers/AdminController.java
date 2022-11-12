package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.RoleService;
import ru.kata.spring.boot_security.demo.services.UserService;

import java.security.Principal;


@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;

    private final RoleService roleService;

    @Autowired
    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }


    @GetMapping()
    public String adminPage(Model model, Principal principal) {

        model.addAttribute("principal", userService.findUserByUsername(principal.getName()));
        model.addAttribute("users", userService.getUsers());
        model.addAttribute("allRoles", roleService.getRoles());

        return "/admin/admin-page";
    }

    //  //  //  ////  //  //  ////  //  //  ////  //  //  ////  //  //  ////  //  //  //

    @GetMapping("/newUser")
    public String newUserForm(/*@ModelAttribute("user") User user,*/ Model model, Principal principal ) {
        model.addAttribute("allRoles", roleService.getRoles());
        model.addAttribute("user", new User());
        model.addAttribute("principal", userService.findUserByUsername(principal.getName()));
        return "/admin/user-create";
    }

    @PostMapping("/create")
    public String createUser(@ModelAttribute("user") User user) {
        userService.saveUser(user);
        return "redirect:/admin";
    }

    //  //  //  ////  //  //  ////  //  //  ////  //  //  ////  //  //  ////  //  //  //

    @DeleteMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") long id, Model model) {
        model.addAttribute("user", userService.findById(id));
        userService.deleteById(id);
        return "redirect:/admin";
    }

    //  //  //  ////  //  //  ////  //  //  ////  //  //  ////  //  //  ////  //  //  //

/*    @GetMapping("/updateUser")
    public String updateUserForm(*//*@PathVariable("id") long id,*//* Model model) {
        //model.addAttribute("user", userService.findById(id));
        model.addAttribute("allRoles", roleService.getRoles());
        return "/admin/admin-page";
    }

    @PatchMapping("/update/{id}")
    public String updateUser(@ModelAttribute("user") User user, long id) {
        userService.update(id, user);
        return "redirect:/admin";
    }*/

    @PutMapping("/{id}/update")
    public String updateUser(@ModelAttribute("user") User user, Model model,@PathVariable("id") long id) {
        model.addAttribute("allRoles", roleService.getRoles());
        //getUserRoles(user);
        userService.update(id, user);
        return "redirect:/admin";
    }

}
