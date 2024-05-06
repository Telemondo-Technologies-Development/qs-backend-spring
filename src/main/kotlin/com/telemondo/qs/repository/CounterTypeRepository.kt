package com.telemondo.qs.repository

import com.telemondo.qs.entity.CounterType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository


@Repository
interface CounterTypeRepository: CrudRepository<CounterType, String>{



}