package com.theseuntaylor.minipe.lib_taps.local

import androidx.room.RoomDatabase

abstract class TapsDatabase: RoomDatabase() {
    abstract fun tapsDao(): TapsDao
}