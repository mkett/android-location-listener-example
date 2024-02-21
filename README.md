## Example for lacation tracking on Android

This example will show how track location position on Android. First we need to define the permission on [manifest.xml](https://github.com/mkett/android-location-listener-example/blob/main/app/src/main/AndroidManifest.xml)

```
<uses-permission android:name="android.permission.ACCESS_FINE_LOCATION"/>
<uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION"/>
```

Now we have to [check if the permission is granted or otherwise let the user grant it](https://github.com/mkett/android-location-listener-example/blob/main/app/src/main/java/com/example/location/tracking/MainActivity.kt).

```
val dangPermToRequest: List<String> = checkMissingPermissions()
if (dangPermToRequest.isNotEmpty()) {
    requestPermissions(dangPermToRequest)
    return
}
```

To use the location service, we need to get the location manager through *getSystemService*

```
locationManager = this.getSystemService(LOCATION_SERVICE) as LocationManager
```

Now we [define the location listener and register it through our location manager](https://github.com/mkett/android-location-listener-example/blob/main/app/src/main/java/com/example/location/tracking/MainActivity.kt).

```
private val locationListener = object : LocationListener {
    override fun onLocationChanged(location: Location) {
        ...
    }
}

private fun registerToLocationListener() {
    ...
    locationManager.requestLocationUpdates(
        LocationManager.GPS_PROVIDER, 9000, 0f, locationListener
    )
}

```

If you use an Emulator, you have to define a location or better a route. 
1) Go to Emulator settings
2) Open *Location -> Routes*
3) Search for a location
4) Press *Route* and add a destination
5) Now press *Play Route*

![Android Emulator Route Example](https://github.com/mkett/android-location-listener-example/blob/main/Android-Emulator-Rout.png)
