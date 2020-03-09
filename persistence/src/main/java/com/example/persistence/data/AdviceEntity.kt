package com.example.persistence.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "advices")
data class AdviceEntity(
        @PrimaryKey
        val id: Int,
        val advice: String
)