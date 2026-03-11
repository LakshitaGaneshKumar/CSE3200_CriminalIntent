package com.example.criminalintent

import androidx.lifecycle.ViewModel
import java.util.Date
import java.util.UUID

class CrimeListViewModel : ViewModel() {
    val crimes = mutableListOf<Crime>()
    init{
        for(num in 0 until 100) {
            val crime = Crime(
                id = UUID.randomUUID(),
                title = "Crime #${num + 1}",
                date = Date(),
                isSolved = num % 2 == 1
            )
            crimes += crime
            //crimes.add(crime)
        }
    }
}