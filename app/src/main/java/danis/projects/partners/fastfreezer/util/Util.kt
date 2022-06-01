package danis.projects.partners.fastfreezer.util

import danis.projects.partners.fastfreezer.data.entidades.Coordinates
import danis.projects.partners.fastfreezer.data.entidades.UserType

object Util {
     var uiidService: String? = null
     var price: String? = null
     var fkClient: String? = null
     var fkCarrier: String? = null
    var status = ServiceStatus.INACTIVE.nameString
     var packageNameService: String? = null
     var description: String? = null
     var temperature: String? = null
     var weight: String? = null
     var coordinates: Coordinates? = null
    var fromLatitude: Double?= null
    var fromLongitud:Double? = null
    var toLatitude: Double? = null
    var toLongitu:Double? = null


     var fromTo: Coordinates? = null
     var typeUser:AuthenticationType? = null
}