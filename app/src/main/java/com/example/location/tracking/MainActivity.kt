package com.example.location.tracking

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    private val REQUEST_CODE_ASK_PERMISSIONS = 123
    private lateinit var locationManager: LocationManager

    /**
     * define location listener to track locations
     */
    private val locationListener = object : LocationListener {
        override fun onLocationChanged(location: Location) {
            val latitude = location.latitude
            val longitude = location.longitude
            Toast.makeText(
                this@MainActivity,
                "Latitude: $latitude, Longitude: $longitude",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // request location manger
        locationManager = this.getSystemService(LOCATION_SERVICE) as LocationManager
    }

    override fun onResume() {
        super.onResume()
        val dangPermToRequest: List<String> = checkMissingPermissions()
        if (dangPermToRequest.isNotEmpty()) {
            requestPermissions(dangPermToRequest)
            return
        }
        registerLocationListener()
    }

    override fun onPause() {
        super.onPause()
        // unregister location listener
        locationManager.removeUpdates(locationListener)
    }

    @SuppressLint("MissingPermission")
    private fun registerLocationListener() {
        if (!isLocationProviderEnabled()) {
            Toast.makeText(this, "location provider disabled", Toast.LENGTH_SHORT).show()
            return
        }
        //register as location listener at location service through the manager
        locationManager.requestLocationUpdates(
            LocationManager.GPS_PROVIDER, 9000, 0f, locationListener
        )
    }

    private fun isLocationProviderEnabled(): Boolean {
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
    }

    /**
     * result after user denied or granted the permission
     */
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == REQUEST_CODE_ASK_PERMISSIONS) {
            //all permissions have been granted
            if (!grantResults.contains(PackageManager.PERMISSION_DENIED)) {
                registerLocationListener()
                return
            }
        }
    }

    /**
     * verify if user grant already permission
     */
    private fun checkMissingPermissions(): List<String> {
        val permissions: MutableList<String> = ArrayList()
        if (checkSelfPermission(ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            permissions.add(ACCESS_FINE_LOCATION)
        }
        if (checkSelfPermission(ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            permissions.add(ACCESS_COARSE_LOCATION)
        }
        return permissions
    }

    /**
     * request needed permission to the user
     */
    private fun requestPermissions(permissions: List<String>) {
        requestPermissions(permissions.toTypedArray<String>(), REQUEST_CODE_ASK_PERMISSIONS)
    }

}
