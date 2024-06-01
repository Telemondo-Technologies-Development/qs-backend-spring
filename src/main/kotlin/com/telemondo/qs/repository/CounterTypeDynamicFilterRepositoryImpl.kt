package com.telemondo.qs.repository

import com.telemondo.qs.entity.Counter
import com.telemondo.qs.entity.CounterType
import com.telemondo.qs.entity.QueueUser
import com.telemondo.qs.web.controller.CounterTypeControllers.CounterTypeFilter
import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import jakarta.persistence.criteria.CriteriaBuilder
import jakarta.persistence.criteria.CriteriaQuery
import jakarta.persistence.criteria.Predicate
import jakarta.persistence.criteria.Root
import java.time.Instant

class CounterTypeDynamicFilterRepositoryImpl(
    @PersistenceContext private val entityManager: EntityManager
) : CounterTypeDynamicFilterRepository {

    override fun findDynamicCounterTypes(filter: CounterTypeFilter): List<CounterType> {
        val cb: CriteriaBuilder = entityManager.criteriaBuilder
        val query: CriteriaQuery<CounterType> = cb.createQuery(CounterType::class.java)
        val counterType: Root<CounterType> = query.from(CounterType::class.java)

        val predicates: MutableList<Predicate> = ArrayList()

        filter.id?.let {
            predicates.add(cb.equal(counterType.get<String>("id"),it))
        }

        filter.counterName?.let {
            predicates.add(cb.equal(counterType.get<String>("counterName"),it))
        }

        filter.prefix?.let {
            predicates.add(cb.equal(counterType.get<String>("prefix"), it))
        }


//        the code snippet below doesn't work in cases where createdAt and updatedAt are null because
//        it directly compares the supposed createdAt value to instant.epoch which cannot be if indeed the former is null
//        whereas the other solution does the filtering with a safe call operator. that's why it works

//        if (filter.createdAt != Instant.EPOCH) {
//            println("NOT EQUAL TO EPOCH")
//            println(filter.createdAt.toString())
//            predicates.add(cb.greaterThanOrEqualTo(counterType.get("createdAt"), filter.createdAt))
//        }
//        if (filter.updatedAt != Instant.EPOCH) {
//            println("NOT EQUAL TO EPOCH")
//            predicates.add(cb.greaterThanOrEqualTo(counterType.get("updatedAt"), filter.updatedAt))
//        }

        filter.createdAt?.let {
            predicates.add(cb.greaterThanOrEqualTo(counterType.get("createdAt"), it))
        }

        filter.updatedAt?.let {
            predicates.add(cb.greaterThanOrEqualTo(counterType.get("updatedAt"), it))
        }

        // Apply sorting if specified
        filter.sortField?.let { field ->
            val sortOrder = if (filter.sortDirection == "ASC") {
                cb.asc(counterType.get<Any>(field))
            } else {
                cb.desc(counterType.get<Any>(field))
            }
            query.orderBy(sortOrder)
        }

        query.where(*predicates.toTypedArray())

//        pagination for ALL results is placed below the "query.where" so that the pagination
//        will be applied after all the predicates will apply.
//        this is so that when pageSize = -1, it still takes into account the other
//        filtering properties and not just return EVERYTHING disregarding filter properties
        // If pageSize is -1, return all records
        if (filter.pageSize == -1) {
            return entityManager.createQuery(query).resultList
        }
        // Fetch all results without pagination to get the total count
        val allResults = entityManager.createQuery(query).resultList
        val totalResults = allResults.size.toLong()

        // Calculate the total number of pages
//        we subtract 1 from the totalResults to make all results need an additional page whether
//        it's divisible by the pageSize.
//        this works because of integer division that rounds the quotient down making it imperative
//        for an additional page to make it correct
        val totalPages = if (totalResults == 0L) 1 else ((totalResults - 1) / filter.pageSize) + 1
        val lastPage = totalPages.toInt() - 1

        // If currentPage is -1, calculate the index of the last page
        if (filter.currentPage == -1) {
            filter.currentPage = lastPage
        }
        println("Last Page: $lastPage")
        return entityManager.createQuery(query).setFirstResult(filter.currentPage * filter.pageSize).setMaxResults(filter.pageSize).resultList
    }
}