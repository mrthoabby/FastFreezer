package danis.projects.partners.fastfreezer.mixed

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import danis.projects.partners.fastfreezer.R
import danis.projects.partners.fastfreezer.data.RemoteDataManager
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.net.PlacesClient
import danis.projects.partners.fastfreezer.util.SERVICE_TABLE

class MapActivity : AppCompatActivity() ,OnMapReadyCallback{
    private lateinit var map:GoogleMap
private lateinit var placesClient:PlacesClient
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_map)
        setup()

    }

    private fun setup(){
        createFragment()
    }
    private fun initialicePlaces(){
        Places.initialize(this,resources.getString(R.string.google_maps_key))
        placesClient = Places.createClient(this)
    }

    private fun lasTsetEventListeners(){
        RemoteDataManager.ref.child(SERVICE_TABLE).addChildEventListener(object :ChildEventListener{
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                if(snapshot.exists()){

                }
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                TODO("Not yet implemented")
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
                TODO("Not yet implemented")
            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                TODO("Not yet implemented")
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

    private fun createFragment(){
        val mapFragment: SupportMapFragment = supportFragmentManager.findFragmentById(R.id.mapRT) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }
    override fun onMapReady(googleMap: GoogleMap){
        map = googleMap
    }
}