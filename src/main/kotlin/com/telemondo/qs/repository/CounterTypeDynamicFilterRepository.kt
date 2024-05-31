package com.telemondo.qs.repository

import com.telemondo.qs.entity.CounterType
import com.telemondo.qs.web.controller.CounterTypeControllers.CounterTypeFilter

interface CounterTypeDynamicFilterRepository {
    fun findDynamicCounterTypes(filter: CounterTypeFilter): List<CounterType>
}