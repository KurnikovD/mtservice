package com.example.mtservice.service.impl

import com.example.mtservice.service.BalanceService
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service
import java.util.*

@Service
class QueueDecorator
    (
    @Qualifier("balanceServiceImpl")
    private val balanceService: BalanceService,
) : BalanceService {

    private var queue: Queue<Pair<Long, Long>> = LinkedList()

    override fun get(id: Long): Long {
        return balanceService.get(id)
    }

    override fun add(id: Long, amount: Long) {
        queue.add(Pair(id, amount))
    }

    init {
        Thread {
            while (true) {
                if (queue.isNotEmpty()) {
                    val (id, amount) = queue.poll()
                    balanceService.add(id, amount)
                }
            }
        }.start()
    }

}
