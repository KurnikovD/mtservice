package com.example.mtservice.controller

import com.example.mtservice.exception.NotEnoughMoneyException
import com.example.mtservice.security.details.CustomUserDetails
import com.example.mtservice.service.BalanceService
import com.example.mtservice.service.UserService
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.http.HttpStatus
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException

@Controller
@RequestMapping("/profile/balance")
class BalanceController(
    @Qualifier("cacheDecorator")
    private val balanceService: BalanceService,
    private val userService: UserService,
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

        if (userService.checkUsersBalanceId(userDetails.getUser().id!!, id)) {
            val balance = balanceService.get(id)
            model.addAttribute("balance", balance)
            model.addAttribute("id", id)
            return "balance"
        }

        throw ResponseStatusException(HttpStatus.NOT_FOUND, "Balance not found")
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

        if (userService.checkUsersBalanceId(userDetails.getUser().id!!, id)) {
            balanceService.add(id, amount)
            return "redirect:/profile/balance/$id"
        }

        throw ResponseStatusException(HttpStatus.NOT_FOUND, "Balance not found")
    }

    @ExceptionHandler(ResponseStatusException::class)
    fun handleResponseStatusException(e: ResponseStatusException, model: Model): String {
        model.addAttribute("reason", e.reason)
        return "error"
    }

    @ExceptionHandler(NotEnoughMoneyException::class)
    fun handleNotEnoughMoneyException(e: NotEnoughMoneyException, model: Model): String {
        model.addAttribute("balance", e.balance)
        model.addAttribute("id", e.balanceId)
        model.addAttribute("error_message", e.message)
        return "balance"
    }

}
