package com.telemondo.qs.repository

import com.telemondo.qs.entity.Counter
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface CounterRepository: CrudRepository<Counter, String> {
}