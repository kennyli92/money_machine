package com.example.money_machine.data.transaction

import androidx.room.TypeConverter
import java.util.*

class TransactionConverters {
  @TypeConverter
  fun fromTransactionTagToString(tag: TransactionTag): String {
    return tag.name
  }

  @TypeConverter
  fun fromStringToTransactionTag(tag: String): TransactionTag {
    return TransactionTag.valueOf(tag)
  }

  @TypeConverter
  fun fromLongToDate(date: Long): Date {
    return Date(date)
  }

  @TypeConverter
  fun fromDateToLong(date: Date): Long {
    return date.time
  }
}