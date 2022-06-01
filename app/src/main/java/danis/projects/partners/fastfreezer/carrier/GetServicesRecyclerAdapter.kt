package danis.projects.partners.fastfreezer.carrier

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import danis.projects.partners.fastfreezer.R
import danis.projects.partners.fastfreezer.data.entidades.Coordinates
import danis.projects.partners.fastfreezer.mixed.MapActivity
import danis.projects.partners.fastfreezer.data.entidades.Service
import danis.projects.partners.fastfreezer.util.Util
import danis.projects.partners.fastfreezer.util.dataCoordinates

class GetServicesRecyclerAdapter(private val context: Context, private val listServices:List<Service>
): RecyclerView.Adapter<GetServicesRecyclerAdapter.ViewHolder>()




    //RecyclerView.Adapter<GetServicesRecyclerAdapter.ViewHolder>()
{
    private val layoutInflater = LayoutInflater.from(context)
    inner class  ViewHolder(itemView: View?):RecyclerView.ViewHolder(itemView!!){
        val textNameUserService = itemView?.findViewById<TextView?>(R.id.listNameUserService)
        val textNamePackageService = itemView?.findViewById<TextView?>(R.id.listNamePackageService)
        val textPriceService = itemView?.findViewById<TextView?>( R.id.listPriceService)
        var uiiService:String = ""
        var cordenadas:Coordinates? = null
        private val btnAcceptService = itemView?.findViewById<Button?>(R.id.btnAcceptService)
        init {
            btnAcceptService?.setOnClickListener {
                Util.uiidService = uiiService
                Util.coordinates = cordenadas
                val intent = Intent(context, MapActivity::class.java)
                context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = layoutInflater.inflate(R.layout.item_list_services,parent,false)
        return ViewHolder(itemView)
    }

    override fun getItemCount() = listServices.size
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val service = listServices[position]
        holder.textNameUserService?.text = service.packageNameService
        holder.textPriceService?.text = service.price
        holder.textNamePackageService?.text = service.packageNameService
        holder.uiiService = service.uiidService.toString()
        holder.cordenadas = Coordinates(dataCoordinates(service.fromLatitude,service.fromLongitud),
            dataCoordinates(service.toLatitude,service.toLongitu))

    }

}