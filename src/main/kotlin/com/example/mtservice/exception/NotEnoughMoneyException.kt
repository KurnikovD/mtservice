package com.example.mtservice.exception

import lombok.Getter

@Getter
class NotEnoughMoneyException(
    val balanceId: Long,
    val balance: Long,
) : RuntimeException() {
    override var message = "Not enough money"
}
