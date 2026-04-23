package com.example.criminalintent

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.criminalintent.databinding.FragmentCrimeListBinding
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

private const val TAG = "CrimeListFragment"
class CrimeListFragment : Fragment() {
    private val crimeListViewModel : CrimeListViewModel by viewModels()
    private var job: Job? = null // you will delete this
    private var _binding : FragmentCrimeListBinding? = null
    private val binding
        get() = checkNotNull(_binding){"We got an error in the binding"}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCrimeListBinding.inflate(layoutInflater, container, false)
        binding.crimeRecyclerView.layoutManager = LinearLayoutManager(context)
        return binding.root
    }

//    override fun onStart() {
//        super.onStart()
//        job = viewLifecycleOwner.lifecycleScope.launch {
//            val crimes = crimeListViewModel.loadCrimes()
//            binding.crimeRecyclerView.adapter = CrimeListAdapter(crimes)
//        }
//        Log.d(TAG, "Job launching in onStart")
//    }
//
//    override fun onStop() {
//        super.onStop()
//        // cancel the job started in onStart
//        Log.d(TAG, "Job getting canceled in onStop")
//        job?.cancel()
//    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                //val crimes = crimeListViewModel.loadCrimes()
                crimeListViewModel.crimes.collect { crimes ->
                    binding.crimeRecyclerView.adapter = CrimeListAdapter(crimes) { crimeId ->
                        findNavController().navigate(
                            //R.id.show_crime_detail
                            CrimeListFragmentDirections.showCrimeDetail(crimeId)
                        )
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}