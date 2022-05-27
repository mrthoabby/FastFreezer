package danis.projects.partners.fastfreezer.client

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import danis.projects.partners.fastfreezer.R
import danis.projects.partners.fastfreezer.databinding.ActivityBoardInitBinding

class BoardInitActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBoardInitBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBoardInitBinding.inflate(layoutInflater)
        val view = binding.root.apply {
            setContentView(this)
        }
    }
    private fun setup(){
        addEventsListeners();
    }
    private fun addEventsListeners(){
        binding.btnRequestService.setOnClickListener {
            launchFormService()
        }
    }
    private fun launchFormService(){
        val intent = Intent(this,RequestProductActivity::class.java)
        startActivity(intent)
    }
}