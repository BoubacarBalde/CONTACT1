package com.example.contact

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import baseDonne.BaseDonnee
import java.io.ByteArrayOutputStream


class Modification : AppCompatActivity() {

    var bitmap: Bitmap? = null
    lateinit var baseDonne: BaseDonnee

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_modification)

        //Initialisation de la base de donnee
        baseDonne = BaseDonnee(this)

        val nom = findViewById<EditText>(R.id.idnom)
        val teleph = findViewById<EditText>(R.id.idtelephone)
        val img1 = findViewById<ImageView>(R.id.idImgg)
        val idC = findViewById<TextView>(R.id.idTxt)
        val btMo = findViewById<Button>(R.id.idbntModifier)

        var recoinom = intent.getStringExtra("nom")
        var recoiteleph = intent.getStringExtra("telephone")
        var recoitId = intent.getStringExtra("id")
        var recoiImage = intent.getByteArrayExtra("img")

        nom.setText(recoinom)
        teleph.setText(recoiteleph)
        idC.setText(recoitId)
        if (recoiImage != null){
            val bitmap = BitmapFactory.decodeByteArray(recoiImage, 0, recoiImage.size)
            img1.setImageBitmap(bitmap)
        }
//        img1.setImageURI(Uri.parse(recoiImage))

        //Recuperation de l'image
        val recuperationImage = registerForActivityResult(ActivityResultContracts.GetContent()){ data ->
            val inputSteam = contentResolver.openInputStream(data!!)
            bitmap = BitmapFactory.decodeStream(inputSteam)
            img1.setImageBitmap(bitmap)
        }
        img1.setOnClickListener{
            recuperationImage.launch("image/*")
        }

        btMo.setOnClickListener{
            if(nom.text.trim().isEmpty() || teleph.text.trim().isEmpty() || img1 == null){
                Toast.makeText(this,"Veuillez remplire les champs",Toast.LENGTH_SHORT).show()
            }else{
                val idd = idC.text.toString()
                val idd11 = idd.toInt()
                val nom1 = nom.text.toString()
                val telephone = teleph.text.toString()
                val image11 = ConverstionEnByte(bitmap!!)

                baseDonne.updateContact(idd11,nom1,telephone,image11)
                Toast.makeText(this,"Compte modifier avec succes",Toast.LENGTH_SHORT).show()
                nom.setText("")
                teleph.setText("")
                idC.setText("")
                img1.setImageResource(R.drawable.vendeur)

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