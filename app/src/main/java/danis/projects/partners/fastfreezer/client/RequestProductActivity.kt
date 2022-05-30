package danis.projects.partners.fastfreezer.client

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import danis.projects.partners.fastfreezer.data.RemoteDataManager
import danis.projects.partners.fastfreezer.data.RunTimeDataManager
import danis.projects.partners.fastfreezer.data.entidades.Service
import danis.projects.partners.fastfreezer.data.entidades.WeightPrice
import danis.projects.partners.fastfreezer.databinding.ActivityRequestProductBinding
import danis.projects.partners.fastfreezer.mixed.MapActivity
import danis.projects.partners.fastfreezer.util.*

class RequestProductActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRequestProductBinding
    private lateinit var goToIntent: Intent
    private lateinit var mAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRequestProductBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        setup()
    }
    override fun onStart() {
        super.onStart()
        if (mAuth.currentUser == null) {
            //Salir
        }
    }
private fun setup(){
    mAuth = FirebaseAuth.getInstance()
    setDataSources()
    addEventListeners()
}

    private fun setDataSources() =
   ArrayAdapter(this,
       android.R.layout.simple_expandable_list_item_1,
       RunTimeDataManager.weightPrices)
       .also { binding.spinnerInputWeightProduct.adapter = it }

    private fun isDataValid(): Boolean = binding.inputProductName.text.isNotEmpty()
                && binding.inputTemperature.text.isNotEmpty()
                && binding.inputServiceDescription.text.isNotEmpty()

    private fun addEventListeners(){
        binding.btnSoliciteService.setOnClickListener {
            if(isDataValid()){
                createService()
            }
            else{
                showToast("Datos incompletos")
            }
        }
    }
    private fun showToast(message: String) =
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    private fun createService(){
        val data:WeightPrice = binding.spinnerInputWeightProduct.selectedItem as WeightPrice
        mAuth.currentUser?.let { it ->
            RemoteDataManager.ref.child(SERVICE_TABLE).push().setValue(
                Service(it.uid,
                    null,
                    ServiceStatus.ENSOLICITUD.nameString,
                    data.precio,
                    it.displayName,
                    binding.inputProductName.text.toString(),
                    binding.inputServiceDescription.text.toString(),
                    binding.inputTemperature.text.toString(),
                    data.peso,
                )
            ).addOnCompleteListener {
                if (it.isSuccessful){
                    showToast("Creando servicio")
                    updateStatusUser()
                    launchSplashService()
                }
                else {
                    showToast("No fue posible registrar al usuario")
                }

            }
        }
    }
    private fun updateStatusUser():Boolean{
        var result:Boolean
        mAuth.currentUser?.let { it ->
            RemoteDataManager.ref
                .child(USER_TABLE)
                .child(it.uid)
                .child(UserAtrributes.STATUS.nameString)
                .setValue(UserStatus.AWAITSERVICE.nameString).addOnCompleteListener {
                    if(it.isSuccessful){
                        showToast("Usuario actualizado")
                        result = true
                    }else{
                        showToast("Usuario actualizado")
                        result = false
                    }

                }
        }
        return  true
    }

    private fun launchSplashService() {
        goToIntent = Intent(this, MapActivity::class.java)
        startActivity(goToIntent)
    }
}