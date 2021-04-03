package com.example.findworker.models

open class User(var email: String, var password: String) {
    private val role // 1 - for user , 2 - for worker
            = 0

}