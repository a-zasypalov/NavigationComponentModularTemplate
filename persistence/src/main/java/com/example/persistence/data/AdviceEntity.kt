package com.example.persistence.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "advices")
data class AdviceEntity(
        @PrimaryKey
        @ColumnInfo(name = "id")
        val id: Int,
        val advice: String
)