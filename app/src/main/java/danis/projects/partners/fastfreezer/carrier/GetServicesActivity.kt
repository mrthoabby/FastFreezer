package danis.projects.partners.fastfreezer.carrier

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import danis.projects.partners.fastfreezer.databinding.ActivityGetServicesBinding

class GetServicesActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGetServicesBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGetServicesBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }
}