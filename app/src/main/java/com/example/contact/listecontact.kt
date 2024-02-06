package com.example.contact

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.*
import android.widget.*
import android.widget.AdapterView.AdapterContextMenuInfo
import baseDonne.BaseDonnee
import data.IdentiteContact
import java.io.ByteArrayOutputStream

class listecontact : AppCompatActivity() {

    lateinit var list: ListView
    lateinit var arraof: ArrayList<IdentiteContact>
    lateinit var adap: ContactAddapter
    lateinit var baseDonnee: BaseDonnee

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listecontact)

        list = findViewById(R.id.listView)
        baseDonnee = BaseDonnee(this)

        arraof = baseDonnee.findContact()
        adap = ContactAddapter(this,R.layout.item_post,arraof)
        list.adapter = adap

        registerForContextMenu(list)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_contact,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.idAjouter ->{
                Intent(this,NouveauContact::class.java).also {
                    startActivity(it)
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }
//
//    override fun onCreateContextMenu(
//        menu: ContextMenu?,
//        v: View?,
//        menuInfo: ContextMenu.ContextMenuInfo?
//    ) {
//        menuInflater.inflate(R.menu.option_contact,menu)
//        super.onCreateContextMenu(menu, v, menuInfo)
//    }

//    override fun onContextItemSelected(item: MenuItem): Boolean {
//        var info: AdapterView.AdapterContextMenuInfo = item.menuInfo as AdapterContextMenuInfo
//        var positio:Int = info.position
//
//        var nomPrenom = findViewById<TextView>(R.id.idNomPrenom)
//        var numero = findViewById<TextView>(R.id.idNumero)
//        var id = findViewById<TextView>(R.id.idCon)
//
//        //Obtenir l'id de l'image
//        var image1 = findViewById<ImageView>(R.id.idImage)
//
//        //Obtenez le Drawable de l'ImageView
//        val drawable = image1.drawable
//
//        // Convertissez le Drawable en Bitmap
//        val bitmap2: Bitmap = (drawable as BitmapDrawable).bitmap
//
//        //Convertissez le Bitmap en une chaîne d'octets
//        val byteArrayOutputStream = ByteArrayOutputStream()
//        bitmap2.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
//        val byteArray = byteArrayOutputStream.toByteArray()
//
//        when(item.itemId){
//            R.id.idModifier -> {
//                Intent(this,Modification::class.java).also {
//                    it.putExtra("nom",nomPrenom.text.toString())
//                    it.putExtra("telephone",numero.text.toString())
//                    it.putExtra("id",id.text.toString())
//                arraof.removeAt(positio)
//                adap.notifyDataSetChanged()
//            }
//        }
//        return super.onContextItemSelected(item)
//    }

//    //Fonction pour convertire l'image de bitmap en byteArray pour les fichiers png
//    fun getByteArray(bitmap: Bitmap): ByteArray{
//        val byteArrayOutputStream = ByteArrayOutputStream()
//        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
//        val byteArray = byteArrayOutputStream.toByteArray()
//
//        return byteArray
//    }
}
////                    it.putExtra("img", byteArray)
//                    startActivity(it)
//                }
//            }
//            R.id.idSupprimer -> {