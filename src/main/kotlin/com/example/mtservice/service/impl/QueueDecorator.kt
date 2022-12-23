package com.example.mtservice.service.impl

import com.example.mtservice.service.BalanceService
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service
import java.util.*

@Service
class QueueDecorator
    (
    @Qualifier("balanceServiceImpl")
    balanceService: BalanceService,
) : BalanceService {

    private val target: BalanceService = balanceService

    private var queue: Queue<Pair<Long, Double>> = LinkedList()

    override fun get(id: Long): Double {
        return target.get(id)
    }

    override fun add(id: Long, amount: Double) {
        queue.add(Pair(id, amount))
    }

    init {
        Thread {
            while (true) {
                if (!queue.isEmpty()) {
                    val poll = queue.poll()
                    target.add(poll.first, poll.second)
                }
            }
        }.start()
    }
}
