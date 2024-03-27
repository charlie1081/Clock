package com.example.clock.model

data class CheckboxItem(val timeZone: String,var isChecked: Boolean = false, var mode: Mode = Mode.Display)
enum class Mode{
    Display, Edit
}
