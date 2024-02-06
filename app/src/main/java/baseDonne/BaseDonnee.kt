package baseDonne

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import data.IdentiteContact

class BaseDonnee(mContext: Context):SQLiteOpenHelper(mContext,DB_NAME,null, DB_VERSION) {
    override fun onCreate(db: SQLiteDatabase?) {
        //Creation de la table contact
        val creationTable = """
            CREATE TABLE $DB_TABLE_NAME(
               $ID_CONATCT integer PRIMARY KEY,
               $NAME varchar(50),
               $NUMERO varchar(50),
               $IMAGE blod
            )
        """.trimIndent()
        db?.execSQL(creationTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, ancienVersion: Int, nouvelVersion: Int) {
        //Suppression et creation des table
        db?.execSQL("DROP TABLE IF EXISTS $DB_TABLE_NAME")
        onCreate(db)

    }

    //Fonction pour Ajouter un Contact
    fun addContact(contact: IdentiteContact):Boolean{
        val db = this.writableDatabase
        val values = ContentValues()

        values.put(NAME, contact.nomPrenom)
        values.put(NUMERO, contact.numero)
        values.put(IMAGE, contact.image)

       val inserer = db.insert(DB_TABLE_NAME, null, values).toInt()
        db.close()
        return inserer != -1
    }

    //Foinction pour l'affichage des contacts
    fun findContact():ArrayList<IdentiteContact>{

        val lsitContact = ArrayList<IdentiteContact>()
        val db = this.readableDatabase
        val selectio = "SELECT * FROM $DB_TABLE_NAME"
        val renvoie = db.rawQuery(selectio, null)

        if (renvoie != null){
            if (renvoie.moveToFirst()){
                do {
                    val id = renvoie.getInt(renvoie.getColumnIndexOrThrow(ID_CONATCT))
                    val nom = renvoie.getString(renvoie.getColumnIndexOrThrow(NAME))
                    val numero = renvoie.getString(renvoie.getColumnIndexOrThrow(NUMERO))
                    val image = renvoie.getBlob(renvoie.getColumnIndexOrThrow(IMAGE))

                    val listContact1 = IdentiteContact(id,nom,numero,image)
                    lsitContact.add(listContact1)

                }while (renvoie.moveToNext())
            }
        }
        db.close()
        return lsitContact
    }

   // Fonction pour la modifiacation
    fun updateContact(id: Int, name: String, phone: String, photo: ByteArray): Boolean {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(NAME, name)
        values.put(NUMERO, phone)
        values.put(IMAGE, photo)
        val success = db.update(DB_TABLE_NAME, values, "$ID_CONATCT=?", arrayOf(id.toString())).toLong()
        db.close()
        return Integer.parseInt("$success") != -1
    }
    //Fonction pour supprimer un contact
    fun deleteConatct(id: Int):Boolean{
        val db = this.writableDatabase
        val renvoiSupress = db.delete(DB_TABLE_NAME,"id=?", arrayOf("$id"))
        db.close()
        return renvoiSupress > 0
    }

    companion object{
        private val DB_NAME = "Contact_utilisateur"
        private val DB_TABLE_NAME = "contacts"
        private val DB_VERSION = 10
        private val ID_CONATCT = "id"
        private val NAME = "nom"
        private val NUMERO = "numero"
        private val IMAGE = "image"
    }
}