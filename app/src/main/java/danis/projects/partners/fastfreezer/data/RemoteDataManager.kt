package danis.projects.partners.fastfreezer.data

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.ktx.database
import danis.projects.partners.fastfreezer.data.entidades.Service
import danis.projects.partners.fastfreezer.util.SERVICE_TABLE
import com.google.firebase.ktx.Firebase as Firebase

object RemoteDataManager  {
    private val database = Firebase.database
    val ref = database.getReference()

    public fun getServices(context: AppCompatActivity){
        val options: FirebaseRecyclerOptions<Service> = FirebaseRecyclerOptions.Builder<Service>()
            .setQuery(RemoteDataManager.ref.child(SERVICE_TABLE), Service::class.java)
            .setLifecycleOwner(context)
            .build()

    }
}