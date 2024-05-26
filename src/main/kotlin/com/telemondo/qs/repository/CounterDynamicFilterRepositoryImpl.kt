package com.telemondo.qs.repository

import com.telemondo.qs.entity.Counter
import com.telemondo.qs.entity.CounterType
import com.telemondo.qs.entity.QueueUser
import com.telemondo.qs.web.controller.CounterController.CounterFilter
import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import jakarta.persistence.criteria.CriteriaBuilder
import jakarta.persistence.criteria.CriteriaQuery
import jakarta.persistence.criteria.Join
import jakarta.persistence.criteria.JoinType
import jakarta.persistence.criteria.Predicate
import jakarta.persistence.criteria.Root

class CounterDynamicFilterRepositoryImpl(
    @PersistenceContext private val entityManager: EntityManager
) : CounterDynamicFilterRepository {

    override fun findDynamicCounters(filter: CounterFilter): List<Counter> {
        val cb: CriteriaBuilder = entityManager.criteriaBuilder
        val query: CriteriaQuery<Counter> = cb.createQuery(Counter::class.java)
        val counter: Root<Counter> = query.from(Counter::class.java)
//        Sets up an inner join between the Counter and CounterType entities using the 'counterType' attribute.
//        This join enables retrieving data from both tables simultaneously based on a common condition.
//        Used left join to retrieve ALL records regardless if there are matches unlike inner join which
//        only returns matching records. This does not work for us since we have the properties that need joining
//        are nullable.
        val counterType: Join<Counter, CounterType> = counter.join("counterType", JoinType.LEFT)
        val currentCustomer: Join<Counter, QueueUser> = counter.join("currentCustomer", JoinType.LEFT)

        val predicates: MutableList<Predicate> = ArrayList()

        filter.id?.let {
            predicates.add(cb.equal(counter.get<String>("id"),it))
        }

        filter.status?.let {
            predicates.add(cb.equal(counter.get<Int>("status"),it))
        }

        filter.name?.let {
            predicates.add(cb.equal(counter.get<String>("name"), it))
        }

        filter.currentCustomerId?.let {
            predicates.add(cb.equal(currentCustomer.get<String>("id"), it))
        }


        filter.counterTypeId?.let {
            predicates.add(cb.equal(counterType.get<String>("id"), it))
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
            predicates.add(cb.greaterThanOrEqualTo(counter.get("createdAt"), it))
        }

        filter.updatedAt?.let {
            predicates.add(cb.greaterThanOrEqualTo(counter.get("updatedAt"), it))
        }

        // Apply sorting if specified
        filter.sortField?.let { field ->
            val sortOrder = if (filter.sortDirection == "ASC") {
                cb.asc(counter.get<Any>(field))
            } else {
                cb.desc(counter.get<Any>(field))
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
//            return entityManager.createQuery(query).resultList
            return entityManager.createQuery(query).resultList
        }

        // If currentPage is -1, calculate the index of the last page
        if (filter.currentPage == -1) {
            // Create a count query to get the total number of results
            val countQuery: CriteriaQuery<Long> = cb.createQuery(Long::class.java)
            val countRoot: Root<Counter> = countQuery.from(Counter::class.java)
            countQuery.select(cb.count(countRoot))
            countQuery.where(*predicates.toTypedArray())

            val totalResults = entityManager.createQuery(countQuery).singleResult
            val lastPageIndex = if (totalResults == 0L) 0 else (totalResults - 1) / filter.pageSize
            filter.currentPage = lastPageIndex.toInt()  // Update the currentPage parameter to the last page index
        }

        return entityManager.createQuery(query).setFirstResult(filter.currentPage * filter.pageSize).setMaxResults(filter.pageSize).resultList
    }
}
