package danis.projects.partners.fastfreezer.mixed

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.net.PlacesClient
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import danis.projects.partners.fastfreezer.R
import danis.projects.partners.fastfreezer.data.RemoteDataManager
import danis.projects.partners.fastfreezer.data.entidades.Coordinates
import danis.projects.partners.fastfreezer.util.*
import danis.projects.partners.fastfreezer.util.Util.fromLatitude
import danis.projects.partners.fastfreezer.util.Util.fromLongitud
import danis.projects.partners.fastfreezer.util.Util.toLatitude
import danis.projects.partners.fastfreezer.util.Util.toLongitu

class MapActivity : AppCompatActivity() ,OnMapReadyCallback{
    private lateinit var map:GoogleMap
private lateinit var placesClient:PlacesClient
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_map)
        createFragment()
        RemoteDataManager.ref.child(SERVICE_TABLE).addListenerForSingleValueEvent(object  :
            ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                snapshot.children.forEach {
                    val service: danis.projects.partners.fastfreezer.data.entidades.Service = it.getValue(
                        danis.projects.partners.fastfreezer.data.entidades.Service::class.java) as danis.projects.partners.fastfreezer.data.entidades.Service
                    if(service.uiidService == Util.uiidService && service.status == ServiceStatus.ENSOLICITUD.toString()){
                        fromLatitude = service.fromLatitude
                        fromLongitud = service.fromLongitud
                        toLatitude = service.toLatitude
                        toLongitu = service.toLatitude
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

    }

    override fun onBackPressed() {
        //super.onBackPressed()
    }




    private fun createFragment(){
        val mapFragment: SupportMapFragment = supportFragmentManager.findFragmentById(R.id.mapRT) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }
    override fun onMapReady(googleMap: GoogleMap)
    {
        map = googleMap
        createFragment()
        map.setMinZoomPreference(15f)
        map.setMaxZoomPreference(20f)
        if (fromLatitude != null){
            val from = LatLng(fromLatitude!!,fromLongitud!!)
            val to = LatLng(toLatitude!!,toLongitu!!)
            map.animateCamera(CameraUpdateFactory.newLatLngZoom(from, 10F))

            setMarkers(Coordinates(dataCoordinates(from.latitude, fromLongitud),
                dataCoordinates(to.latitude,to.longitude)))
        }

    }


    private fun setMarkers(coordenadas:Coordinates){
        when(Util.typeUser){
            AuthenticationType.CLIENT_BASIC ->{

            }
            AuthenticationType.CARRIER_BASIC ->{

            }
        }
        addMarkers(coordenadas.from.latitude!!,coordenadas.from.longitude!!,"Origen")
        addMarkers(coordenadas.to.latitude!!,coordenadas.to.longitude!!,"Destino")
    }

    private fun addMarkers(latitude: Double,longitude:Double,title: String) {
            val markerOptions =  MarkerOptions().position(LatLng(latitude,longitude)).title(title)
        map.addMarker(markerOptions)
    }



}


