package com.telemondo.qs.repository

import com.telemondo.qs.entity.CounterType
import com.telemondo.qs.web.controller.CounterTypeControllers
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.time.Instant


@Repository
interface CounterTypeRepository: JpaRepository<CounterType, String>, CounterTypeDynamicFilterRepository
