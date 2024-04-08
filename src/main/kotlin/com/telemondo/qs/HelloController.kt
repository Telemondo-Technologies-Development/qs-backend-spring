package com.telemondo.qs

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

//data format for displaying data
data class ViewAccount(
    val id: Int,
    val name: String
)

//data format for post req
data class CreateAccount(
    val name: String
)

//for easier routing
@RestController
//makes all route start with "/accounts"
@RequestMapping("/accounts")

//get route
class AccountsController {
    @GetMapping("/")
    fun getAll(): Iterable<ViewAccount> =
        listOf(ViewAccount(id=1, name="First"))

    @PostMapping("/")
    fun create(@RequestBody request: CreateAccount): ViewAccount =
        ViewAccount(id=2, name=request.name)
}