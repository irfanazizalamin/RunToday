package id.ac.ui.cs.mobileprogramming.irfanazizalamin.runtoday.ui.fragments

import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import id.ac.ui.cs.mobileprogramming.irfanazizalamin.runtoday.R
import id.ac.ui.cs.mobileprogramming.irfanazizalamin.runtoday.other.Constants.KEY_FIRST_TIME_TOGGLE
import id.ac.ui.cs.mobileprogramming.irfanazizalamin.runtoday.other.Constants.KEY_HEIGHT
import id.ac.ui.cs.mobileprogramming.irfanazizalamin.runtoday.other.Constants.KEY_NAME
import id.ac.ui.cs.mobileprogramming.irfanazizalamin.runtoday.other.Constants.KEY_WEIGHT
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_setup.*
import javax.inject.Inject

@AndroidEntryPoint
class SetupFragment : Fragment(R.layout.fragment_setup) {

    @Inject
    lateinit var sharedPref: SharedPreferences

    @set:Inject
    var isFirstAppOpen = true

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (!isFirstAppOpen) {
            val navOptions = NavOptions.Builder()
                .setPopUpTo(R.id.setupFragment, true)
                .build()
            findNavController().navigate(
                R.id.action_setupFragment_to_runFragment,
                savedInstanceState,
                navOptions
            )
        }

        tvContinue.setOnClickListener {
            val success = writePersonalDataToSharedPref()

            if (success) {
                findNavController().navigate(R.id.action_setupFragment_to_runFragment)
            } else {
                Snackbar.make(requireView(), resources.getString(R.string.filling_info), Snackbar.LENGTH_LONG).show()
            }
        }
    }

    private fun writePersonalDataToSharedPref(): Boolean {
        val name = etName.text.toString()
        val weight = etWeight.text.toString()
        val height = etHeight.text.toString()

        if (name.isEmpty() || weight.isEmpty() || height.isEmpty()) {
            return false
        }

        sharedPref.edit()
            .putString(KEY_NAME, name)
            .putFloat(KEY_WEIGHT, weight.toFloat())
            .putInt(KEY_HEIGHT, height.toInt())
            .putBoolean(KEY_FIRST_TIME_TOGGLE, false)
            .apply()

        val letsgoString = resources.getString(R.string.lets_go)
        val toolbarText = "$letsgoString $name !"
        requireActivity().tvToolbarTitle.text = toolbarText
        return true
    }
}