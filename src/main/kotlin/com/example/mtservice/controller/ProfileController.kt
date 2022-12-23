package com.example.mtservice.controller

import com.example.mtservice.security.details.CustomUserDetails
import com.example.mtservice.service.ProfileService
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/profile")
class ProfileController(private val profileService: ProfileService) {

    @GetMapping
    fun profile(@AuthenticationPrincipal userDetails: CustomUserDetails?, model: Model): String {
        if (userDetails == null) {
            return "redirect:/signin"
        }

        val balances = profileService.getAllBalances(userDetails.getUser().id!!)

        model.addAttribute("username", userDetails.username)
        model.addAttribute("balances", balances)

        return "profile"
    }

    @PostMapping("/balance/new")
    fun newBalance(
        @AuthenticationPrincipal
        userDetails: CustomUserDetails?,
        amount: Long,
        model: Model,
    ): String {
        if (userDetails == null) {
            return "redirect:/signin"
        }

        profileService.addNewBalance(userDetails.getUser().id!!, amount)

        return "redirect:/profile"
    }
}
