package danis.projects.partners.fastfreezer.data.entidades

import danis.projects.partners.fastfreezer.util.ServiceStatus

data class Service(
    val fkClient: String? = null,
    val fkCarrier: String? = null,
    val status: String? = ServiceStatus.INACTIVE.nameString,
    val price: String? = null,
    val name: String? = null,
    val packageNameService: String? = null,
    val description: String? = null,
    val temperature: String? = null,
    val weight: String? = null
)