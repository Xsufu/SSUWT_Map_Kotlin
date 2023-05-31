package com.xolary.ssuwtmap.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "students")
data class Student(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    // Логин
    @ColumnInfo(name = "login")
    val login: String,

    // Пароль
    @ColumnInfo(name = "password")
    val password: String,

    // Имя
    @ColumnInfo(name = "name")
    val name: String,

    // Фамилия
    @ColumnInfo(name = "lastName")
    val lastName: String,

    // Группа
    @ColumnInfo(name = "group")
    val group: String
)
