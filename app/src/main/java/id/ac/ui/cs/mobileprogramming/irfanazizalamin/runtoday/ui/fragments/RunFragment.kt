package id.ac.ui.cs.mobileprogramming.irfanazizalamin.runtoday.ui.fragments

import android.Manifest
import android.app.AlertDialog
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import id.ac.ui.cs.mobileprogramming.irfanazizalamin.runtoday.R
import id.ac.ui.cs.mobileprogramming.irfanazizalamin.runtoday.adapters.RunAdapter
import id.ac.ui.cs.mobileprogramming.irfanazizalamin.runtoday.other.Constants.REQUEST_CODE_LOCATION_PERMISSIONS
import id.ac.ui.cs.mobileprogramming.irfanazizalamin.runtoday.other.SortType
import id.ac.ui.cs.mobileprogramming.irfanazizalamin.runtoday.other.TrackingUtility
import id.ac.ui.cs.mobileprogramming.irfanazizalamin.runtoday.ui.viewmodels.MainViewModel
import kotlinx.android.synthetic.main.fragment_run.*
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions

@AndroidEntryPoint
class RunFragment : Fragment(R.layout.fragment_run), EasyPermissions.PermissionCallbacks {

    private val viewModel: MainViewModel by viewModels()

    private lateinit var runAdapter: RunAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requestPermissions()
        setupRecyclerView()

        when(viewModel.sortType) {
            SortType.DATE -> spFilter.setSelection(0)
            SortType.RUNNING_TIME -> spFilter.setSelection(1)
            SortType.DISTANCE -> spFilter.setSelection(2)
            SortType.AVG_SPEED -> spFilter.setSelection(3)
            SortType.CALORIES_BURNED -> spFilter.setSelection(4)
        }

        spFilter.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                adapterView: AdapterView<*>?,
                view: View?,
                pos: Int,
                id: Long
            ) {
                when(pos) {
                    0 -> viewModel.sortRuns(SortType.DATE)
                    1 -> viewModel.sortRuns(SortType.RUNNING_TIME)
                    2 -> viewModel.sortRuns(SortType.DISTANCE)
                    3 -> viewModel.sortRuns(SortType.AVG_SPEED)
                    4 -> viewModel.sortRuns(SortType.CALORIES_BURNED)

                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        viewModel.runs.observe(viewLifecycleOwner, Observer {
            runAdapter.submitList(it)
        })

        fab.setOnClickListener {
            findNavController().navigate(R.id.action_runFragment_to_trackingFragment)
        }
    }

    private fun setupRecyclerView() = rvRuns.apply {
        runAdapter = RunAdapter()
        adapter = runAdapter
        layoutManager = LinearLayoutManager(requireContext())
    }

    private fun requestPermissions() {
        if (TrackingUtility.hasLocationPermissions(requireContext())) {
            runContent.visibility = View.VISIBLE
            return
        }

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
            EasyPermissions.requestPermissions(
                this,
                resources.getString(R.string.permission_info),
                 REQUEST_CODE_LOCATION_PERMISSIONS,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
            )
        } else {
            EasyPermissions.requestPermissions(
                this,
                resources.getString(R.string.permission_info),
                REQUEST_CODE_LOCATION_PERMISSIONS,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_BACKGROUND_LOCATION,
            )
        }
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            AppSettingsDialog.Builder(this).build().show()
        }
        requestPermissions()
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        runContent.visibility = View.GONE

        val builder = AlertDialog.Builder(context)

        builder.setTitle("Information")
        builder.setMessage("You have to activate your GPS to use this application")
        builder.setIcon(android.R.drawable.ic_dialog_alert)
        builder.setPositiveButton("OK"){dialogInterface, which -> }

        val alertDialog: AlertDialog = builder.create()
        alertDialog.setCancelable(false)
        alertDialog.show()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }
}