package danis.projects.partners.fastfreezer.data.entidades

import danis.projects.partners.fastfreezer.util.ServiceStatus
import java.time.temporal.Temporal
import java.util.*

data class Service(
    val uiidService: String? = null,
    val fkClient: String? = null,
    val fkCarrier: String? = null,
    val status: String? = ServiceStatus.INACTIVE.nameString,
    val price: String? = null,
    val packageNameService: String? = null,
    val description: String? = null,
    val temperature: String? = null,
    val weight: String? = null,
    val fromLatitude: Double?= null,
    val fromLongitud:Double? = null,
    val toLatitude: Double? = null,
    val toLongitu:Double? = null,
)