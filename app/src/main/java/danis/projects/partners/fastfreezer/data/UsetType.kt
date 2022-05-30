package danis.projects.partners.fastfreezer.data.entidades

import danis.projects.partners.fastfreezer.util.AuthenticationType

data class UserType (val userTypeString :String, val authenticationType: AuthenticationType) {
    override fun toString(): String {
        return userTypeString
    }
}