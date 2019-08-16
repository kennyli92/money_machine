package com.example.money_machine.db.transaction

enum class TransactionTag(val label: String) {
  FOOD(label = "Food"),
  HEALTH(label = "Health"),
  HOME(label = "Home"),
  TECH(label = "Tech"),
  VEHICLE(label = "Vehicle"),
  CLOTH(label = "Cloth"),
  ACCOUNT(label = "Account"),
  OTHER(label = "Other")
}