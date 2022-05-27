package danis.projects.partners.fastfreezer.data

import danis.projects.partners.fastfreezer.data.RunTimeDataManager.weightPrices
import danis.projects.partners.fastfreezer.data.entidades.UserType
import danis.projects.partners.fastfreezer.data.entidades.WeightPrice
import danis.projects.partners.fastfreezer.util.AuthenticationType

object RunTimeDataManager {
    val usersTypes = ArrayList<UserType>()
    val weightPrices = ArrayList<WeightPrice>()
    init {
        initializeUserTypes()
        initializeWightPrices()
    }

    private fun initializeWightPrices() {
        weightPrices.add(WeightPrice("<= 1 lb ","$13.000"))
        weightPrices.add(WeightPrice("<= 1 kl","$26.000"))
        weightPrices.add(WeightPrice("<= 2 kl","$44.000"))
        weightPrices.add(WeightPrice("<= 8 kl","$128.000"))
        weightPrices.add(WeightPrice("<= 12 kl","$188.000"))
        weightPrices.add(WeightPrice("<= 20 lb","$358.000"))
    }

    private fun initializeUserTypes() {
        usersTypes.add(UserType("Usuario",AuthenticationType.CLIENT_BASIC))
        usersTypes.add(UserType("Conductor",AuthenticationType.CARRIER_BASIC))
    }
}