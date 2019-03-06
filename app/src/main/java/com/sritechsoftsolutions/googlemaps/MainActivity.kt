package com.sritechsoftsolutions.googlemaps

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.FragmentManager
import android.view.View
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
 var ggmap:GoogleMap?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var sFragment:SupportMapFragment= supportFragmentManager.findFragmentById(R.id.frag1) as SupportMapFragment
        sFragment.getMapAsync(object :OnMapReadyCallback{
            override fun onMapReady(gMap: GoogleMap?) {

                ggmap=gMap
            }

        })

        //Lamda Expression of above function
       /* sFragment.getMapAsync {
            ggmap=it
        }*/

        btngetlocation.setOnClickListener {
            getCurrentLoaction()
        }

    }

    @SuppressLint("MissingPermission")
    fun getCurrentLoaction() {
        var lManager:LocationManager=getSystemService(Context.LOCATION_SERVICE)as LocationManager
        lManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,1000L,1f,
            object :LocationListener{
                override fun onLocationChanged(location: Location?) {
                    txtlat.text="Latitude: "+location?.latitude
                    txtlong.text="Longitude: "+location?.longitude
                    //lManager.removeUpdates(this)//this is for stopping continues updates

                    var mOptions=MarkerOptions()
                    mOptions.position(LatLng(location!!.latitude,location!!.longitude))

                    mOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.flag))

                    ggmap?.addMarker(mOptions)
                    ggmap?.animateCamera(CameraUpdateFactory.
                        newLatLngZoom(LatLng(location.latitude,location.longitude),17f))


                }

                override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {

                }

                override fun onProviderEnabled(provider: String?) {

                }

                override fun onProviderDisabled(provider: String?) {

                }

            }
            )
    }
}
