package com.example.criminalintent

// the x in androidx means that it belongs to the jetpack compose class
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import com.example.criminalintent.databinding.FragmentCrimeDetailBinding
import java.util.Date
import java.util.UUID

// this is how you make the CrimeDetailFragment class a child of the Fragment class
class CrimeDetailFragment : Fragment() {

    // the binding will try to look for the xlm files to bind to
    // we'll get ActivityMainBinding and FragmentCrimeDetailBinding for the
    // activity_main.xml and the fragment_crime_detail.xml
    private var _binding : FragmentCrimeDetailBinding? = null
    private val binding
        get() = checkNotNull(_binding){"Failed to access binding"}
    private lateinit var crime : Crime

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        crime = Crime(
            id = UUID.randomUUID(),
            title = "",
            date = Date(),
            isSolved = false
        )
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
                crime = crime.copy(title = text.toString())
            }

            crimeDate.apply{
                text = crime.date.toString()
                isEnabled = false
            }

            crimeSolved.setOnCheckedChangeListener { _, isChecked ->
                crime = crime.copy(isSolved = isChecked)
            }
        }
    }
}