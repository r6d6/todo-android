package com.example.angelo.todo.models

class Task(name: String) {

    var id: Int = 0

    var name: String? = ""

    init {
        this.name = name
    }

    override fun toString(): String {
        return this.name.toString()
    }
}
