package com.example.criminalintent

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.Date
import java.util.UUID

private const val TAG = "CrimeListViewModel"

class CrimeListViewModel : ViewModel() {

    private val crimeRepository = CrimeRepository.get()
    //var crimes = mutableListOf<Crime>()
    //val crimes = crimeRepository.getCrimes()
    private val _crimes : MutableStateFlow<List<Crime>> = MutableStateFlow(emptyList())
    val crimes : StateFlow<List<Crime>>
        .get() = _crimes.asStateFlow()
    init{
        viewModelScope.launch {
            //crimes += loadCrimes()\
            crimeRepository.getCrimes().collect {
                _crimes.value = it
            }
        }
    }

 //   suspend fun loadCrimes(): List<Crime> { return crimeRepository.getCrimes() }
//        val result = mutableListOf<Crime>()
//        delay(5000)
//        for (num in 0 until 100) {
//            val crime = Crime(
//                id = UUID.randomUUID(),
//                title = "Crime #${num + 1}",
//                date = Date(),
//                isSolved = num % 2 == 1
//            )
//            result += crime
//        }
//        return result
//    }
}