package com.theseuntaylor.minipe.lib_taps.data.local

import androidx.room.RoomDatabase

abstract class TapsDatabase: RoomDatabase() {
    abstract fun tapsDao(): TapsDao
}