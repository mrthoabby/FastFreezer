package danis.projects.partners.fastfreezer.util

enum class AuthenticationType(){
    CLIENT_BASIC,
    CARRIER_BASIC
}

enum class ServiceStatus(val nameString: String ){
    INACTIVE("inactivo"),
    ACTIVE("activo"),
    ENCURSO("en_curso"),
    ENSOLICITUD("solicitando")

}

enum class UserStatus(val nameString: String){
    OUTSERVICE("sin_servicio"),
    INSERVICE("en_servicio"),
    AWAITSERVICE("esperando_servicio")
}

enum class ProductStatus(val nameString: String){
    ENSERVICIO("en_servicio"),
    FINALIZADO("finalizado")
}

enum class UserAtrributes(val nameString: String){
    STATUS("status")
}

data class dataCoordinates(val latitude:Double?,val longitude:Double?)
const val _ID = "_uuid"
const val SERVICE_TABLE = "services"
const val USER_TABLE = "users"
const val AUTOCOMPLETE_REQUEST_CODE = 3
const val FROM_REQUEST_CODE = 2
const val TO_REQUEST_CODE = 1
const val REQUEST_CODE_LOCATION = 4