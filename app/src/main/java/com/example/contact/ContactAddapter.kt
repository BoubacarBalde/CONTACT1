package com.example.contact

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import baseDonne.BaseDonnee
import data.IdentiteContact
import java.io.ByteArrayOutputStream


class ContactAddapter(
    var mContext: Context,
    var ressource: Int,
    var values: ArrayList<IdentiteContact>
    ):ArrayAdapter<IdentiteContact>(mContext,ressource,values){

    lateinit var basedonnee: BaseDonnee

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val positi = values[position]
        val itemView = LayoutInflater.from(mContext).inflate(ressource, parent, false)
        var nomPrenom = itemView.findViewById<TextView>(R.id.idNomPrenom)
        var numero = itemView.findViewById<TextView>(R.id.idNumero)
        var image = itemView.findViewById<ImageView>(R.id.idImage)
        var id = itemView.findViewById<TextView>(R.id.idCon)
        var menuUtilisa = itemView.findViewById<ImageView>(R.id.idMenuUtilis)

        nomPrenom.text = positi.nomPrenom
        numero.text = positi.numero
        id.text = positi.id.toString()
        val bitmap1: Bitmap = getBitmap(positi.image)
        image.setImageBitmap(bitmap1)

         //Obtenez le Drawable de l'ImageView
        val drawable = image.drawable

        // Convertissez le Drawable en Bitmap
        val bitmap2: Bitmap = (drawable as BitmapDrawable).bitmap

        //Convertissez le Bitmap en une chaîne d'octets
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap2.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
        val byteArray = byteArrayOutputStream.toByteArray()

        menuUtilisa.setOnClickListener{
            val popupmenu = PopupMenu(mContext,menuUtilisa)
            popupmenu.menuInflater.inflate(R.menu.option_contact, popupmenu.menu)

            popupmenu.setOnMenuItemClickListener { item ->

                when(item.itemId){
                    R.id.idModifier -> {
                        Intent(mContext,Modification::class.java).also {
                            it.putExtra("nom",nomPrenom.text.toString())
                            it.putExtra("telephone",numero.text.toString())
                            it.putExtra("id",id.text.toString())
                            it.putExtra("img", byteArray)
                            mContext.startActivity(it)
                        }
                    }
                    R.id.idSupprimer ->{
                        val strin = id.text.toString()
                        val int = strin.toInt()
                        basedonnee = BaseDonnee(mContext)
                        basedonnee.deleteConatct(int)

                        values.removeAt(position)
                        notifyDataSetChanged()
                    }
                }
                true
            }
            popupmenu.show()
        }

        return itemView
    }

    //Fonction pour convertire l'image de byte en bitmap pour les fichiers png
    fun getBitmap(byteArray: ByteArray): Bitmap{
        val bitmap: Bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
        return bitmap
    }

//    //Fonction pour convertire l'image de bitmap en byteArray pour les fichiers png
//    fun getByteArray(bitmap: Bitmap): ByteArray{
//        val byteArrayOutputStream = ByteArrayOutputStream()
//        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
//        val byteArray = byteArrayOutputStream.toByteArray()
//
//        return byteArray
//    }
}
