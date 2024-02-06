package data

data class IdentiteContact(
    var nomPrenom: String,
    var numero: String,
    var image: ByteArray
){
    var id: Int = -1
    constructor(id: Int, nomPrenom: String,numero: String,image: ByteArray):this(nomPrenom,numero, image){
        this.id = id
    }
}
