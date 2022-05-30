package danis.projects.partners.fastfreezer.carrier

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import danis.projects.partners.fastfreezer.data.RemoteDataManager
import danis.projects.partners.fastfreezer.data.entidades.Service
import danis.projects.partners.fastfreezer.databinding.ActivityGetServicesBinding
import danis.projects.partners.fastfreezer.util.SERVICE_TABLE
import danis.projects.partners.fastfreezer.util.ServiceStatus

class GetServicesActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGetServicesBinding
    private lateinit var listContent: List<Service>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGetServicesBinding.inflate(layoutInflater)
        val view = binding.root.apply {
            setContentView(this)
        }
        setup()
    }

    private fun setup(){
        listContent = listOf()
        setAddEventListenners()

    }

    override fun onStart() {
        super.onStart()

    }

    private fun setAddEventListenners(){
        RemoteDataManager.ref.child(SERVICE_TABLE).addValueEventListener (object:ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                listContent = listOf()
               snapshot.children.forEach {
                   val service:Service = it.getValue(Service::class.java) as Service
                   if(service.status == ServiceStatus.ENSOLICITUD.nameString)
                   listContent += service
               }
                setDataSources()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

    private fun setDataSources(){
        binding.listGetServices.layoutManager = LinearLayoutManager(this)
        binding.listGetServices.adapter = GetServicesRecyclerAdapter(this,listContent)

    }
}