package com.telemondo.qs.repository

import com.telemondo.qs.entity.Counter
import com.telemondo.qs.web.controller.CounterController.CounterFilter

interface CounterDynamicFilterRepository {
    fun findDynamicCounters(filter: CounterFilter): List<Counter>
}