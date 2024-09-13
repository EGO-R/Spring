package org.java4me.spring.http.controller;

import jakarta.validation.groups.Default;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.java4me.spring.database.entity.Role;
import org.java4me.spring.dto.PageResponse;
import org.java4me.spring.dto.UserCreateEditDto;
import org.java4me.spring.dto.UserFilter;
import org.java4me.spring.dto.UserReadDto;
import org.java4me.spring.service.CompanyService;
import org.java4me.spring.service.UserService;
import org.java4me.spring.validation.group.CreateAction;
import org.java4me.spring.validation.group.UpdateAction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Slf4j
@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final CompanyService companyService;

    @GetMapping
    public String findAll(Model model, UserFilter filter, Pageable pageable) {
        var page = userService.findAll(filter, pageable);
        model.addAttribute("page", PageResponse.of(page));
        model.addAttribute("filter", filter);
        return "users/users";
    }

    @GetMapping("/{id}")
//    @PreAuthorize("hasAuthority('ADMIN')")
    public String findById(@PathVariable("id") Long id,
                           Model model,
                           @CurrentSecurityContext SecurityContext securityContext,
                           @AuthenticationPrincipal UserDetails userDetails) {
        return userService.findById(id)
                .map(user -> {
                    model.addAttribute("user", user);
                    model.addAttribute("roles", Role.values());
                    model.addAttribute("companies", companyService.findAll());
                    return "users/user";
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/registration")
    public String registration(Model model, @ModelAttribute("user") UserCreateEditDto user) {
        model.addAttribute("user", user);
        model.addAttribute("roles", Role.values());
        model.addAttribute("companies", companyService.findAll());
        return "users/registration";
    }

    @PostMapping
//    @ResponseStatus(HttpStatus.CREATED)
    public String create(@Validated({Default.class, CreateAction.class}) UserCreateEditDto user,
                         // Only after user param
                         BindingResult bindingResult,
                         RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("user", user);
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
            return "redirect:/users/registration";
        }
        userService.create(user);
        return "redirect:/login";
    }

//    @PutMapping("/{id}")
    @PostMapping("/{id}/update")
    public String update(@Validated({Default.class, UpdateAction.class}) UserCreateEditDto user,
                         @PathVariable("id") Long id) {
        return userService.update(id, user)
                .map(it -> "redirect:/users/{id}")
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

//    @DeleteMapping("/{id}")
    @PostMapping("/{id}/delete")
    public String delete(@PathVariable("id") Long id) {
        if (!userService.delete(id))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        return "redirect:/users";
    }
}
