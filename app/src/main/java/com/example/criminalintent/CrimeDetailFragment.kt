package com.example.criminalintent

// the x in androidx means that it belongs to the jetpack compose class
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.criminalintent.databinding.FragmentCrimeDetailBinding
import kotlinx.coroutines.launch
import java.util.Date
import java.util.UUID

private const val TAG = "crimeDetailFragment"
// this is how you make the CrimeDetailFragment class a child of the Fragment class
class CrimeDetailFragment : Fragment() {

    // the binding will try to look for the xlm files to bind to
    // we'll get ActivityMainBinding and FragmentCrimeDetailBinding for the
    // activity_main.xml and the fragment_crime_detail.xml
    private var _binding : FragmentCrimeDetailBinding? = null
    private val binding
        get() = checkNotNull(_binding){"Failed to access binding"}
    //private lateinit var crime : Crime
    private val args: CrimeDetailFragmentArgs by navArgs()

    private val crimeDetailViewModel : CrimeDetailViewModel by viewModels {
        CrimeDetailViewModelFactory(args.crimeId)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCrimeDetailBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // apply is one of 6 kotlin scoping functions. they all have lambdas that are slightly different from each other
        binding.apply{
            crimeTitle.doOnTextChanged { text, _, _, _ ->
                crimeDetailViewModel.updateCrime { oldCrime ->
                    oldCrime.copy(title = text.toString())
                }
            }

            crimeDate.apply{
                isEnabled = false
            }

            crimeSolved.setOnCheckedChangeListener { _, isChecked ->
                crimeDetailViewModel.updateCrime { oldCrime ->
                    oldCrime.copy(isSolved = isChecked)
                }
            }

            deleteCrime.setOnClickListener {
                viewLifecycleOwner.lifecycleScope.launch {
                    crimeDetailViewModel.deleteCrime(crimeDetailViewModel.crime.value!!)
                }
                findNavController().navigateUp()
            }

            viewLifecycleOwner.lifecycleScope.launch {
                viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    crimeDetailViewModel.crime.collect { crime ->
                        crime?.let { updateUI(it) }
                    }
                }
            }
        }
    }

    private fun updateUI(crime: Crime) {
        binding.apply {
            if (crimeTitle.text.toString() != crime.title) {
                crimeTitle.setText(crime.title)
            }
            crimeDate.text = crime.date.toString()
            crimeSolved.isChecked = crime.isSolved
        }
    }
}