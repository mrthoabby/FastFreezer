package danis.projects.partners.fastfreezer.mixed

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import danis.projects.partners.fastfreezer.carrier.GetServicesActivity
import danis.projects.partners.fastfreezer.client.BoardInitActivity
import danis.projects.partners.fastfreezer.databinding.ActivityAuthBinding
import danis.projects.partners.fastfreezer.util.AuthenticationType

class AuthActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAuthBinding
    private lateinit var goToIntent: Intent
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthBinding.inflate(layoutInflater)
        val view = binding.root.apply {
            setContentView(this)
        }
        setup()
    }

    private fun setup() {
        initEventsListeners()
    }

    private fun initEventsListeners() {
        binding.btnLogin.setOnClickListener {
            if (isCredentialsValid()) {
                initSession()
            } else {
                showToast("Por favor ingresa unas credenciales correctas")
            }

        }
        binding.checkSwitchShowPassword.setOnCheckedChangeListener { checkbox, ischecked ->
            switchShowPassword(ischecked)
        }
        binding.btnCreateAccount.setOnClickListener {
            launchRegisterActivity()
        }
    }

    private fun isCredentialsValid(): Boolean =
        binding.inputUserEmail.text.isNotEmpty() && binding.inputUserPassword.text.isNotEmpty()

    private fun initSession() {
        FirebaseAuth.getInstance().signInWithEmailAndPassword(
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

    private fun launchHome() {
        goToIntent = if (isCarrier()) Intent(this, GetServicesActivity::class.java)
        else Intent(this, BoardInitActivity::class.java)
        startActivity(goToIntent)
    }

    private fun isCarrier() = binding.isCarrier.isChecked
    private fun switchShowPassword(isCheck: Boolean) {
        if (isCheck) {
            binding.inputUserPassword.inputType = InputType.TYPE_TEXT_VARIATION_PASSWORD
            binding.inputUserPassword.setSelection(binding.inputUserPassword.length())
        } else {
            binding.inputUserPassword.inputType =
                InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            binding.inputUserPassword.setSelection(binding.inputUserPassword.length())
        }
    }
    private fun launchRegisterActivity(){
        goToIntent = Intent(this,RegisterActivity::class.java)
        startActivity(goToIntent)
    }
}