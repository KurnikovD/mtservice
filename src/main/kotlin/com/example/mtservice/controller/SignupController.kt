package com.example.mtservice.controller

import com.example.mtservice.dto.UserDto
import com.example.mtservice.security.details.CustomUserDetails
import com.example.mtservice.service.UserService
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/signup")
class SignupController(
    private val userService: UserService
) {

    @GetMapping
    fun signup(@AuthenticationPrincipal userDetails: CustomUserDetails?, model: Model): String {
        if (userDetails != null) {
            return "redirect:/"
        }
        model.addAttribute("user", UserDto())
        return "signup"
    }

    @PostMapping
    fun signup(userDto: UserDto, bindingResult: BindingResult, model: Model): String {
        if (bindingResult.hasErrors()) {
            model.addAttribute("user", userDto)
            return "signup"
        }
        userService.signup(userDto)

        return "redirect:/signin"
    }
}
