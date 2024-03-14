package com.example.artel.EtoBaza

public class User {
    private var id: Int? = null
    private lateinit var name: String
    private lateinit var second_name: String
    private lateinit var user_name: String
    private lateinit var password: String

    fun User(id: Int, name: String, second_name: String, user_name: String, password: String) {
        this.id = id
        this.name = name
        this.second_name = second_name
        this.user_name = user_name
        this.password = password
    }

    private fun getId(): Int? {
        return id;
    }

    private fun getName(): String {
        return name;
    }

    private fun getRealname(): String {
        return second_name;
    }

    private fun getRating(): String {
        return user_name;
    }

    private fun getTeamaffiliation(): String {
        return password;
    }
}
