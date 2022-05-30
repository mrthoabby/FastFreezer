package danis.projects.partners.fastfreezer.mixed

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import danis.projects.partners.fastfreezer.data.RemoteDataManager
import danis.projects.partners.fastfreezer.data.RunTimeDataManager
import danis.projects.partners.fastfreezer.data.entidades.User
import danis.projects.partners.fastfreezer.data.entidades.UserType
import danis.projects.partners.fastfreezer.databinding.ActivityRegisterBinding
import danis.projects.partners.fastfreezer.util.AuthenticationType
import danis.projects.partners.fastfreezer.util.USER_TABLE

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var goToIntent: Intent
    private lateinit var mAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        val view = binding.root.apply {
            setContentView(this)
        }
        setup()
    }

    override fun onStart() {
        super.onStart()
        if (mAuth.currentUser != null) {
        }
    }

    private fun setup() {
        initEventsListeners()
        setDataSources()
        mAuth = FirebaseAuth.getInstance()
    }

    private fun setDataSources() =
        ArrayAdapter(
            this,
            android.R.layout.simple_expandable_list_item_1,
            RunTimeDataManager.usersTypes
        ).also { binding.spinnerTypeUser.adapter = it }


    private fun initEventsListeners() {
        binding.btnRegister.setOnClickListener {
            if (isCredentialsValid())
                toRegisterUser()
            else {
                showToast("Por favor ingresa información correcta")
            }

        }
        binding.checkSwitchShowPassword.setOnCheckedChangeListener { checkbox, ischecked ->
            switchShowPassword(ischecked)
        }
    }

    private fun isCredentialsValid(): Boolean =
        binding.inputRegisterPersonName.text.isNotEmpty() && binding.inputRegisterUserEmail.text.isNotEmpty() && binding.inputRegisterUserPassword.text.isNotEmpty()

    private fun toRegisterUser() {
        mAuth.createUserWithEmailAndPassword(
            binding.inputRegisterUserEmail.text.toString(),
            binding.inputRegisterUserPassword.text.toString()
        ).addOnCompleteListener {
            if (it.isSuccessful) {
                mAuth.currentUser?.let { it ->
                    RemoteDataManager.ref.child(USER_TABLE).child(
                        it.uid
                    ).setValue(
                        User(
                            binding.inputRegisterPersonName.text.toString(),
                            isCarrier(),
                            binding.inputRegisterUserEmail.text.toString()
                        )
                    ).addOnCompleteListener {
                        if (it.isSuccessful){
                            showToast("Usuario Registrado exitosamente")
                        goToIntent = Intent(this, AuthActivity::class.java)
                        startActivity(goToIntent)
                        }
                        else {
                            showToast("No fue posible registrar al usuario")
                        }

                    }
                }


            } else {
                showToast("No fue posible registrar el usuario")
            }
        }
    }

    private fun showToast(message: String) =
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

    private fun isCarrier(): Boolean {
        val item: UserType = binding.spinnerTypeUser.selectedItem as UserType
        return item.authenticationType == AuthenticationType.CARRIER_BASIC
    }

    private fun switchShowPassword(isCheck: Boolean) {
        if (isCheck) {
            binding.inputRegisterUserPassword .inputType = InputType.TYPE_TEXT_VARIATION_PASSWORD
            binding.inputRegisterUserPassword.setSelection(binding.inputRegisterUserPassword.length())
        } else {
            binding.inputRegisterUserPassword.inputType =
                InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            binding.inputRegisterUserPassword.setSelection(binding.inputRegisterUserPassword.length())
        }
    }
}