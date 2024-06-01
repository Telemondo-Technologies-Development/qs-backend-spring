package com.telemondo.qs.repository

import com.telemondo.qs.entity.Counter
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.time.Instant

@Repository
interface CounterRepository: JpaRepository<Counter, String>, CounterDynamicFilterRepository {

    fun countByCounterTypeId(counterTypeId: String): String

    @Query("SELECT counter FROM Counter counter WHERE counter.status NOT IN (-1, -2) ORDER BY counter.name ASC")
    fun findByStatusNotIn(): List<Counter>
}