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
const val _ID = "_uuid"
const val SERVICE_TABLE = "services"
const val USER_TABLE = "users"
const val API_KEY_MAP = "AIzaSyB5mCDeN8bSyqFn_zyl_EtXVTd9k3DMEhQ"
