package com.example.mtservice.controller

import com.example.mtservice.security.details.CustomUserDetails
import com.example.mtservice.service.BalanceService
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/profile/balance")
class BalanceController(
    @Qualifier("cacheDecorator")
    private val balanceService: BalanceService,
) {

    @GetMapping("/{id}")
    fun balance(
        @AuthenticationPrincipal
        userDetails: CustomUserDetails?,
        @PathVariable
        id: Long,
        model: Model,
    ): String {
        if (userDetails == null) {
            return "redirect:/signin"
        }

        val balance = balanceService.get(id)
        model.addAttribute("balance", balance)
        model.addAttribute("id", id)

        return "balance"
    }

    @PostMapping("{id}/add")
    fun balance(
        @AuthenticationPrincipal
        userDetails: CustomUserDetails?,
        @PathVariable
        id: Long,
        amount: Long,
        model: Model,
    ): String {
        if (userDetails == null) {
            return "redirect:/signin"
        }

        balanceService.add(id, amount)

        return "redirect:/profile/balance/$id"
    }
}
