package com.xolary.ssuwtmap.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface StudentDao {

    // Извлечение данных по логину
    @Query("SELECT * from students WHERE login = :login")
    fun getStudent(login: String): Flow<Student>

    // Регистрация
    @Insert
    suspend fun insert(student: Student)

    @Query("SELECT * from students ORDER BY id DESC")
    fun getAllStudents(): LiveData<List<Student>>

    // Проверка существования пользователя
    @Query("SELECT EXISTS(SELECT * FROM students WHERE login = :login)")
    fun isStudentExist(login: String): Flow<Int>
}