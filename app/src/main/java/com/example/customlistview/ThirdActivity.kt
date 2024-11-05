package com.example.customlistview

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ThirdActivity : AppCompatActivity() {

    private var product: Product? = null

    private lateinit var toolbarTA:Toolbar
    private lateinit var imageViewTAIV:ImageView
    private lateinit var nameTATV:TextView
    private lateinit var costTATV:TextView
    private lateinit var descriptionTATV:TextView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_third)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        toolbarTA=findViewById(R.id.toolbarTA)
        imageViewTAIV= findViewById(R.id.imageViewTAIV)
        nameTATV = findViewById(R.id.nameTATV)
        costTATV = findViewById(R.id.costTATV)
        descriptionTATV = findViewById(R.id.descriptionTATV)

        setSupportActionBar(toolbarTA)
        title = ""

        product = intent.extras?.getSerializable(Product::class.java.simpleName) as Product?

        val name: String = product?.name.toString()
        val cost: String = product?.cost.toString()
        val description: String = product?.description.toString()

        nameTATV.text = "$name"
        costTATV.text = "$cost"
        descriptionTATV.text = "$description"
        imageViewTAIV.setImageURI(Uri.parse(product?.image))
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_ta, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId){
            R.id.exitTAMenu -> finish()
        }
        return super.onOptionsItemSelected(item)
    }
}


