package danis.projects.partners.fastfreezer.data.entidades

import danis.projects.partners.fastfreezer.util.UserStatus
import java.util.*

data class User(val fullName:String,val isCarrier:Boolean,val email:String,val status:String = UserStatus.OUTSERVICE.nameString) {
}