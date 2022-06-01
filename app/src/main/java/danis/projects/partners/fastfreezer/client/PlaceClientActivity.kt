package danis.projects.partners.fastfreezer.client

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.net.PlacesClient
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.AutocompleteActivity
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode
import com.google.firebase.auth.FirebaseAuth
import danis.projects.partners.fastfreezer.R
import danis.projects.partners.fastfreezer.data.RemoteDataManager
import danis.projects.partners.fastfreezer.data.entidades.Coordinates
import danis.projects.partners.fastfreezer.data.entidades.Service
import danis.projects.partners.fastfreezer.databinding.ActivityPlaceClientBinding
import danis.projects.partners.fastfreezer.mixed.MapActivity
import danis.projects.partners.fastfreezer.util.*
import danis.projects.partners.fastfreezer.util.Util.fromLatitude
import danis.projects.partners.fastfreezer.util.Util.fromLongitud
import danis.projects.partners.fastfreezer.util.Util.toLatitude
import danis.projects.partners.fastfreezer.util.Util.toLongitu
import java.util.*

class PlaceClientActivity : AppCompatActivity(), OnMapReadyCallback {

    private var latitudTo:Double? = null
    private var longitudTo:Double? = null
    private var latitudFrom:Double? = null
    private var longitudFrom:Double? = null
    private var map: GoogleMap? = null
    private lateinit var placesClient: PlacesClient
    private val uidService = UUID.randomUUID().toString()
    private lateinit var goToIntent: Intent
    private lateinit var mAuth: FirebaseAuth
    lateinit var binding: ActivityPlaceClientBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlaceClientBinding.inflate(layoutInflater)
        val view = binding.root
        mAuth = FirebaseAuth.getInstance()
        Util.uiidService = uidService
        setContentView(view)
        setAddEventListeners()
        getApiResources()

    }
    override fun onBackPressed() {
        //super.onBackPressed()
    }


    private fun getApiResources(){
        Places.initialize(this,resources.getString(R.string.google_maps_key))
        placesClient = Places.createClient(this)
    }

    private fun setAddEventListeners(){
        binding.inputDataTo.setOnTouchListener (View.OnTouchListener { view, motionEvent ->
            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN)
            autoComplete(TO_REQUEST_CODE)
            return@OnTouchListener false
        })
        binding.inputDataFrom.setOnTouchListener (View.OnTouchListener { view, motionEvent ->
            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN)
            autoComplete(FROM_REQUEST_CODE)
            return@OnTouchListener false
        })
        binding.btnCreateService.setOnClickListener {
            if(latitudTo != null && latitudFrom != null) setServiceOnDataBase()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        valideResultCodes(resultCode,data,if (requestCode == TO_REQUEST_CODE) binding.inputDataTo else binding.inputDataFrom,requestCode)
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun valideResultCodes(resultCode: Int, data: Intent?,editText: EditText,requestCode: Int) {

            when(resultCode){
                Activity.RESULT_OK ->{
                    data?.let {
                        val place = Autocomplete.getPlaceFromIntent(data)
                        when(requestCode){
                            TO_REQUEST_CODE ->{
                                latitudTo = place.latLng.latitude
                                longitudTo = place.latLng.longitude
                            }
                            FROM_REQUEST_CODE -> {
                                latitudFrom = place.latLng.latitude
                                longitudFrom = place.latLng.longitude

                            }

                        }
                        editText.setText(place.address)
                        Log.i(resultCode.toString(),"Place ${place.name}")
                    }
                }
                AutocompleteActivity.RESULT_ERROR ->{
                    data?.let {
                        val status = Autocomplete.getStatusFromIntent(data)
                        Log.i(resultCode.toString(),"Place ${status.statusMessage}")
                    }
                }
            }
    }

    private fun autoComplete(requestCode:Int) {
        val fields = listOf(Place.Field.ID,Place.Field.NAME,Place.Field.ADDRESS,Place.Field.ADDRESS_COMPONENTS,Place.Field.LAT_LNG)
        val intent = Autocomplete.IntentBuilder(AutocompleteActivityMode.FULLSCREEN,fields).build(this)
        startActivityForResult(intent,requestCode)
    }

    private fun setServiceOnDataBase(){
        mAuth.currentUser?.let { it ->
            RemoteDataManager.ref.child(SERVICE_TABLE).push().setValue(
                Service(
                    uidService,
                    it.uid,
                    null,
                    ServiceStatus.ENSOLICITUD.nameString,
                    Util.price,
                    Util.packageNameService,
                    Util.description,
                    Util.temperature,
                    Util.weight,
                    Util.fromLatitude,
                    Util.fromLongitud,
                    Util.toLatitude,
                    Util.toLongitu
                )
            ).addOnCompleteListener {
                if (it.isSuccessful){

                    showToast("Creando servicio")
                    fromLatitude = latitudFrom
                    fromLongitud = longitudFrom
                    toLatitude = latitudTo
                    toLongitu =  longitudTo
                    launchSplashService()
                }
                else {
                    showToast("No fue posible registrar al usuario")
                }

            }
        }
    }

    private fun showToast(message: String) =
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

    private fun launchSplashService() {
        goToIntent = Intent(this, MapActivity::class.java)
        startActivity(goToIntent)
    }
    private fun addMarkers(latitude: Double,longitude:Double,title: String) {
        val markerOptions =  MarkerOptions().position(LatLng(latitude,longitude)).title(title)
        map?.addMarker(markerOptions)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
    }
}