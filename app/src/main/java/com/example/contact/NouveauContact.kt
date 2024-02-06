package com.example.contact

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import baseDonne.BaseDonnee
import data.IdentiteContact
import java.io.ByteArrayOutputStream
//import com.bumptech.glide.Glide

class NouveauContact : AppCompatActivity() {

    lateinit var image: ImageView
    lateinit var nomprenon: TextView
    lateinit var numero: TextView
    lateinit var btnAjouter: Button
    lateinit var basedonne: BaseDonnee

    var bitmap: Bitmap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nouveau_contact)

        image = findViewById(R.id.idimageView)
        nomprenon = findViewById(R.id.idnomprenom)
        numero = findViewById(R.id.idnumero)
        btnAjouter = findViewById(R.id.idbtnAjouter)
        basedonne = BaseDonnee(this)

        //Recuperation de l'image
        val recuperationImage = registerForActivityResult(ActivityResultContracts.GetContent()){ data ->
            val inputSteam = contentResolver.openInputStream(data!!)
            bitmap = BitmapFactory.decodeStream(inputSteam)
            image.setImageBitmap(bitmap)
        }
        image.setOnClickListener{
            recuperationImage.launch("image/*")
        }

        btnAjouter.setOnClickListener{
            val nom1 = nomprenon.text.toString()
            val numero1 = numero.text.toString()
            val image1: ByteArray = ConverstionEnByte(bitmap!!)

            if(nom1.trim().isEmpty() || numero1.trim().isEmpty()){
                Toast.makeText(this,"Veuillez remplire les champs",Toast.LENGTH_SHORT).show()
            }else{
                val identiteContac  = IdentiteContact(0,nom1,numero1,image1)
                basedonne.addContact(identiteContac)
                Toast.makeText(this,"Compte cree avec succes",Toast.LENGTH_SHORT).show()
                nomprenon.setText("")
                numero.setText("")
                image.setImageResource(R.drawable.vendeur)


            }
        }
    }


   //fonction pour convertir l'image en byte
    private fun ConverstionEnByte(bitmat: Bitmap): ByteArray {
        val stream = ByteArrayOutputStream()
        bitmat.compress(Bitmap.CompressFormat.PNG, 0, stream)
        return stream.toByteArray()
    }
}