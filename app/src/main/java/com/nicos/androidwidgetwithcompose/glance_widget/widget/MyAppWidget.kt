package com.nicos.androidwidgetwithcompose.glance_widget.widget

import android.Manifest
import android.annotation.SuppressLint
import android.app.Service
import android.content.Context
import android.content.pm.PackageManager
import android.location.Criteria
import android.location.LocationManager
import android.os.Build
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.glance.Button
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.provideContent
import androidx.glance.background
import androidx.glance.layout.Alignment
import androidx.glance.layout.Box
import androidx.glance.layout.Column
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.padding
import androidx.glance.text.Text
import androidx.glance.text.TextStyle
import androidx.glance.unit.ColorProvider
import com.nicos.androidwidgetwithcompose.BuildConfig
import com.nicos.androidwidgetwithcompose.R

class MyAppWidget : GlanceAppWidget() {

    companion object {
        private const val minTimeLocationUpdateInMillisecond = 10000L
        private const val minDistanceLocationUpdateInMeter = 1000F
    }

    override suspend fun provideGlance(context: Context, id: GlanceId) {
        provideContent {
            MyContent(context)
        }
    }

    @Composable
    private fun MyContent(context: Context) {
        var locationData by remember {
            mutableStateOf("")
        }
        Box(modifier = GlanceModifier.background(color = Color.Black)) {
            Column(
                modifier = GlanceModifier.fillMaxSize(),
                verticalAlignment = Alignment.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = locationData,
                    modifier = GlanceModifier.padding(12.dp),
                    style = TextStyle(
                        color = ColorProvider(
                            Color.Green
                        )
                    )
                )
                Box {
                    Button(
                        text = context.getString(R.string.find_the_coordination),
                        onClick = {
                            locationProcess(context) { data ->
                                locationData = data
                            }
                        }
                    )
                }
            }
        }
    }

    private fun locationProcess(context: Context, listener: (String) -> Unit) {
        if (!checkIfLocationPermissionIsGrande(context = context)) return

        val locationManager = context.getSystemService(Service.LOCATION_SERVICE) as LocationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            findTheLocationForSdkBiggerThan30(
                locationManager = locationManager,
                context = context,
                listener
            )
        } else {
            findTheLocationForSdkLowerThan31(
                locationManager = locationManager,
                context = context,
                listener
            )
        }
    }

    @SuppressLint("MissingPermission")
    private fun findTheLocationForSdkBiggerThan30(
        locationManager: LocationManager,
        context: Context,
        listener: (String) -> Unit,
    ) {
        locationManager.requestLocationUpdates(
            LocationManager.FUSED_PROVIDER,
            minTimeLocationUpdateInMillisecond,
            minDistanceLocationUpdateInMeter
        ) { location ->
            if (BuildConfig.DEBUG) {
                Log.d(
                    "findTheLocationForSdkBiggerThan30",
                    "${location.latitude} ${context.getString(R.string.and)} ${location.longitude}"
                )
            }
            listener("${location.latitude} ${context.getString(R.string.and)} ${location.longitude}")
        }
    }

    @SuppressLint("MissingPermission")
    private fun findTheLocationForSdkLowerThan31(
        locationManager: LocationManager,
        context: Context,
        listener: (String) -> Unit,
    ) {
        /**
         * this code is deprecated from the SDK 34 but we need it for lower than SDK 34
         * */
        Criteria().apply {
            accuracy = Criteria.ACCURACY_COARSE
            powerRequirement =
                Criteria.POWER_LOW

            val provider = locationManager.getBestProvider(
                this,
                false
            )
            if (provider != null) {
                locationManager.requestLocationUpdates(
                    provider,
                    minTimeLocationUpdateInMillisecond,
                    minDistanceLocationUpdateInMeter
                ) { location ->
                    if (BuildConfig.DEBUG) {
                        Log.d(
                            "findTheLocationForSdkBiggerThan30",
                            "${location.latitude} ${context.getString(R.string.and)} ${location.longitude}"
                        )
                    }
                    listener("${location.latitude} ${context.getString(R.string.and)} ${location.longitude}")
                }
            }
        }
    }

    private fun checkIfLocationPermissionIsGrande(context: Context) =
        ActivityCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

}