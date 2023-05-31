package com.xolary.ssuwtmap.data

import androidx.lifecycle.*
import kotlinx.coroutines.launch

class StudentViewModel(private val studentDao: StudentDao) : ViewModel() {
    val allStudents: LiveData<List<Student>> = studentDao.getAllStudents()

    private fun insertStudent(student: Student) {
        viewModelScope.launch {
            studentDao.insert(student)
        }
    }

    private fun getNewStudentEntry(
        login: String,
        password: String,
        name: String,
        lastName: String,
        group: String
    ): Student {
        return Student(
            login = login,
            password = password,
            name = name,
            lastName = lastName,
            group = group
        )
    }

    fun addNewStudent(
        login: String,
        password: String,
        name: String,
        lastName: String,
        group: String
    ) {
        val newStudent = getNewStudentEntry(login, password, name, lastName, group)
        insertStudent(newStudent)
    }

    fun isRegEntryValid(
        login: String,
        password: String,
        name: String,
        lastName: String,
        group: String
    ): Boolean {
        if (login.isBlank() || password.isBlank() || name.isBlank() || lastName.isBlank() || group.isBlank()) {
            return false
        }

        return true
    }

    fun isLoginEntryValid(
        login: String,
        password: String,
    ): Boolean {
        if (login.isBlank() || password.isBlank()) {
            return false
        }

        return true
    }

    fun isStudentExist(login: String): LiveData<Int> {
        return studentDao.isStudentExist(login).asLiveData()
    }

    fun retrieveStudent(login: String): LiveData<Student> {
        return studentDao.getStudent(login).asLiveData()
    }

    class StudentViewModelFactory(private val studentDao: StudentDao) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            /**
             * Проверяем modelClass, если совпадает с классом StudentViewModel,
             * возвращаем его экземпляр. Иначе создаём исключение
             */
            if (modelClass.isAssignableFrom(StudentViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return StudentViewModel(studentDao) as T
            }
            throw IllegalAccessException("Unknown ViewModel class")
        }
    }
}