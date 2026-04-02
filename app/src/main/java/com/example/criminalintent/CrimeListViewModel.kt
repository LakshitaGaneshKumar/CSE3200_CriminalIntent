package com.example.criminalintent

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.Date
import java.util.UUID

private const val TAG = "CrimeListViewModel"

class CrimeListViewModel : ViewModel() {
    var crimes = mutableListOf<Crime>()
    init{
        Log.d(TAG, "About to start a coroutine")
        viewModelScope.launch {
            Log.d(TAG, "Coroutine launched")
            crimes += loadCrimes()
//            delay(5000)
//            for (num in 0 until 100) {
//                val crime = Crime(
//                    id = UUID.randomUUID(),
//                    title = "Crime #${num + 1}",
//                    date = Date(),
//                    isSolved = num % 2 == 1
//                )
//                crimes += crime
//            }
            Log.d(TAG, "Loading crimes finished")
            Log.d(TAG, "Size of crime: ${crimes.size}")
        }
        Log.d(TAG, "Out of VM Scope")
    }

    suspend fun loadCrimes(): List<Crime> {
        val result = mutableListOf<Crime>()
        delay(5000)
        for (num in 0 until 100) {
            val crime = Crime(
                id = UUID.randomUUID(),
                title = "Crime #${num + 1}",
                date = Date(),
                isSolved = num % 2 == 1
            )
            result += crime
        }
        return result
    }
}