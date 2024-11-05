@file:Suppress("NAME_SHADOWING", "UNCHECKED_CAST", "DEPRECATED_IDENTITY_EQUALS")

package com.example.customlistview

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ThirdActivity : AppCompatActivity() {

    private val GALARY_REQUEST = 1
    private var photoUriTwo: Uri? = null

    private lateinit var toolbarTA: Toolbar
    private lateinit var imageViewTAIV: ImageView
    private lateinit var nameTAET: TextView
    private lateinit var costTAET: TextView
    private lateinit var descriptionTAET: TextView
    private lateinit var saveBTN: Button

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

        init()

        setSupportActionBar(toolbarTA)
        title = ""

        val product: Product = intent.extras?.getSerializable("product") as Product
        val products = intent.getSerializableExtra("products")
        val item = intent.extras?.getInt("position")
        var check = intent.extras?.getBoolean("check")

        val name = product.name
        val cost = product.cost
        val description = product.description
        val image: Uri? = Uri.parse(product.image)

        nameTAET.setText(name)
        costTAET.setText(cost)
        descriptionTAET.setText(description)
        imageViewTAIV.setImageURI(image)

        imageViewTAIV.setOnClickListener {
            val photoPickerIntent = Intent(Intent.ACTION_PICK)
            photoPickerIntent.type = "image/*"
            startActivityForResult(photoPickerIntent, GALARY_REQUEST)
        }

        saveBTN.setOnClickListener {
            val product = Product(
                nameTAET.text.toString(),
                costTAET.text.toString(),
                descriptionTAET.text.toString(),
                photoUriTwo.toString()
            )
            val list: MutableList<Product> = products as MutableList<Product>
            if (item != null) {
                swap(item, product, products)
            }
            check = false
            val intent = Intent(this, SecondActivity::class.java)
            intent.putExtra("list", list as ArrayList<Product>)
            intent.putExtra("newCheck", check)
            startActivity(intent)
            finish()
        }
    }

    private fun init() {
        toolbarTA = findViewById(R.id.toolbarTA)
        imageViewTAIV = findViewById(R.id.imageViewTAIV)
        nameTAET = findViewById(R.id.nameTAET)
        costTAET = findViewById(R.id.costTAET)
        descriptionTAET = findViewById(R.id.descriptionTAET)
        saveBTN = findViewById(R.id.saveBTN)
    }

    fun swap(item: Int, product: Product, products: MutableList<Product>) {
        products.add(item + 1, product)
        products.removeAt(item)
    }

    @Deprecated("This method has been deprecated in favor of using the Activity Result API\n      which brings increased type safety via an {@link ActivityResultContract} and the prebuilt\n      contracts for common intents available in\n      {@link androidx.activity.result.contract.ActivityResultContracts}, provides hooks for\n      testing, and allow receiving results in separate, testable classes independent from your\n      activity. Use\n      {@link #registerForActivityResult(ActivityResultContract, ActivityResultCallback)}\n      with the appropriate {@link ActivityResultContract} and handling the result in the\n      {@link ActivityResultCallback#onActivityResult(Object) callback}.")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        imageViewTAIV = findViewById(R.id.imageViewTAIV)
        when (requestCode) {
            GALARY_REQUEST -> if (resultCode === RESULT_OK) {
                photoUriTwo = data?.data
                imageViewTAIV.setImageURI(photoUriTwo)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_ta, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.exitTAMenu -> {
                finishAffinity()
                Toast.makeText(
                    applicationContext,
                    "Программа завершена",
                    Toast.LENGTH_LONG
                ).show()
            }

            R.id.backTAMenu -> {
                val products = intent.getSerializableExtra("products")
                val item = intent.extras?.getInt("position")
                var check = intent.extras?.getBoolean("check")
                val product = Product(
                    nameTAET.text.toString(),
                    costTAET.text.toString(),
                    descriptionTAET.text.toString(),
                    photoUriTwo.toString()
                )
                val list: MutableList<Product> = products as MutableList<Product>
                if (item != null) {
                    swap(item, product, products)
                }
                check = false
                val intent = Intent(this, SecondActivity::class.java)
                intent.putExtra("list", list as ArrayList<Product>)
                intent.putExtra("newCheck", check)
                startActivity(intent)
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}


