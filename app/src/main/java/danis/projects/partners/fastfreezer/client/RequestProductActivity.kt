package danis.projects.partners.fastfreezer.client

import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import danis.projects.partners.fastfreezer.data.RunTimeDataManager
import danis.projects.partners.fastfreezer.databinding.ActivityRequestProductBinding

class RequestProductActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRequestProductBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRequestProductBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }

    private fun setDataSources(){
   ArrayAdapter(this,android.R.layout.simple_expandable_list_item_1,RunTimeDataManager.weightPrices).also { binding.spinnerInputWeightProduct.adapter = it }
    }
    private fun isCredentialsValid(): Boolean =
        binding.inputProductName.text.isNotEmpty()
                && binding.inputTemperature.text.isNotEmpty()
                && binding.inputServiceDescription.text.isNotEmpty()
                && binding.spinnerInputWeightProduct.isSelected
    private fun addEventListeners(){
        binding.btnSoliciteService.setOnClickListener {
            if(isCredentialsValid()){

            }
        }
    }
}