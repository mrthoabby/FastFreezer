package danis.projects.partners.fastfreezer.mixed

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import danis.projects.partners.fastfreezer.carrier.GetServicesActivity
import danis.projects.partners.fastfreezer.client.BoardInitActivity
import danis.projects.partners.fastfreezer.client.RequestProductActivity
import danis.projects.partners.fastfreezer.databinding.ActivityAuthBinding
import danis.projects.partners.fastfreezer.util.AuthenticationType
import danis.projects.partners.fastfreezer.util.EMAIL_USER
import danis.projects.partners.fastfreezer.util.TYPE_AUTHENTICATION

class AuthActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAuthBinding
    private lateinit var goToIntent: Intent
    private lateinit var authenticationType: AuthenticationType
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthBinding.inflate(layoutInflater)
        val view = binding.root.apply {
            setContentView(this)
        }
    }

    private fun setup() {
        initEventsListeners()
    }

    private fun initEventsListeners() {
        binding.btnLogin.setOnClickListener {
            authenticationType = if(isCarrier()) AuthenticationType.CARRIER_BASIC else AuthenticationType.CLIENT_BASIC
            if(isCredentialsValid()){
                initSession()
            }
            else{
                showToast("Por favor ingresa unas credenciales correctas")
            }

        }
    }
    private fun isCredentialsValid(): Boolean =
        binding.inputUserEmail.text.isNotEmpty() && binding.inputUserPassword.text.isNotEmpty()
    private fun initSession() {
        FirebaseAuth.getInstance().signInWithEmailAndPassword (
            binding.inputUserEmail.text.toString(),
            binding.inputUserPassword.text.toString()
        ).addOnCompleteListener {
            if (it.isSuccessful) {
                launchHome()
            } else {
                showToast("No fue posible iniciar sesión")
            }
        }
    }
    private fun showToast(message: String) =
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    private fun launchHome(){
        goToIntent = if(isCarrier()) Intent(this, GetServicesActivity::class.java)
        else Intent(this, BoardInitActivity::class.java)
        startActivity(goToIntent)
    }
    private fun isCarrier() = binding.isCarrier.isActivated
}