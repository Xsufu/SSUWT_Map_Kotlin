package com.xolary.ssuwtmap

import android.app.Application
import com.xolary.ssuwtmap.data.StudentRoomDatabase

class StudentApplication : Application() {
    val database: StudentRoomDatabase by lazy { StudentRoomDatabase.getDatabase(this) }
}