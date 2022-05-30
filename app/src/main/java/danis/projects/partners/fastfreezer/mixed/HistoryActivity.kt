package danis.projects.partners.fastfreezer.mixed

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import danis.projects.partners.fastfreezer.databinding.ActivityHistoryBinding

class HistoryActivity : AppCompatActivity() {
    lateinit var binding: ActivityHistoryBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        val view = binding.root
        setContentView(view)
    }
}