package id.ac.ui.cs.mobileprogramming.irfanazizalamin.runtoday.ui.fragments

import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import id.ac.ui.cs.mobileprogramming.irfanazizalamin.runtoday.R
import id.ac.ui.cs.mobileprogramming.irfanazizalamin.runtoday.other.Constants.KEY_HEIGHT
import id.ac.ui.cs.mobileprogramming.irfanazizalamin.runtoday.other.Constants.KEY_NAME
import id.ac.ui.cs.mobileprogramming.irfanazizalamin.runtoday.other.Constants.KEY_WEIGHT
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.fragment_setup.*
import kotlinx.android.synthetic.main.fragment_setup.etHeight
import kotlinx.android.synthetic.main.fragment_setup.etName
import kotlinx.android.synthetic.main.fragment_setup.etWeight
import javax.inject.Inject

@AndroidEntryPoint
class ProfileFragment : Fragment(R.layout.fragment_profile) {

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    var weight: Float = 0f
    var height: Int = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadFieldsFromSharedPref()

        btnApplyChanges.setOnClickListener {
            val success = applyChangesToSharedPref()
            if (success) {
                Snackbar.make(view, resources.getString(R.string.saved_changes), Snackbar.LENGTH_LONG).show()
            } else {
                Snackbar.make(view, resources.getString(R.string.filling_info), Snackbar.LENGTH_LONG).show()
            }
        }
    }

    private fun loadFieldsFromSharedPref() {
        val name = sharedPreferences.getString(KEY_NAME, "")
        weight = sharedPreferences.getFloat(KEY_WEIGHT, 80f)
        height = sharedPreferences.getInt(KEY_HEIGHT, 165)
        etName.setText(name)
        etWeight.setText(weight.toString())
        etHeight.setText(height.toString())

        showBMI()
    }

    private fun showBMI() {
        val heightInMeter = height / 100f
        val bmiScore = weight / (heightInMeter * heightInMeter)
        var category = ""
        when (bmiScore) {
            in 18.0..25.0 -> category = resources.getString(R.string.normal_category)
            in 25.0..27.0 -> category = resources.getString(R.string.fat_category)
            else ->
                if (bmiScore < 18.0) category = resources.getString(R.string.thin_category)
                else category = resources.getString(R.string.obesity_category)
        }
        tvBmiNumberValue.text = bmiScore.toString()
        tvBmiCategoryValue.text = category
    }

    private fun applyChangesToSharedPref(): Boolean {
        val nameText = etName.text.toString()
        val weightText = etWeight.text.toString()
        val heightText = etHeight.text.toString()
        if (nameText.isEmpty() || weightText.isEmpty() || heightText.isEmpty()) {
            return false
        }
        sharedPreferences.edit()
            .putString(KEY_NAME, nameText)
            .putFloat(KEY_WEIGHT, weightText.toFloat())
            .putInt(KEY_HEIGHT, heightText.toInt())
            .apply()
        val letsgoString = resources.getString(R.string.lets_go)
        val toolbarText = "$letsgoString $nameText !"
        requireActivity().tvToolbarTitle.text = toolbarText
        loadFieldsFromSharedPref()
        return true
    }
}