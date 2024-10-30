package com.example.customlistview

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.ListView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.io.IOException

class SecondActivity : AppCompatActivity() {

    private val GALARY_REQUEST = 302
    var bitmap: Bitmap? = null
    var products: MutableList<Product> = mutableListOf()

    private lateinit var exitBTN: Button
    private lateinit var listViewLV: ListView
    private lateinit var addBTN: Button
    private lateinit var costET: EditText
    private lateinit var nameET: EditText
    private lateinit var editImageIV: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_second)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        init()

        exitBTN.setOnClickListener {
            finish()
        }

        editImageIV.setOnClickListener {
            val photoPickerIntent = Intent(Intent.ACTION_PICK)
            photoPickerIntent.type = "image/*"
            startActivityForResult(photoPickerIntent, GALARY_REQUEST)
        }

        addBTN.setOnClickListener {
            val product = createProduct()
            products.add(product)

            val listAdapter = ListAdapter(this@SecondActivity, products)
            listViewLV.adapter = listAdapter
            listAdapter.notifyDataSetChanged()
            clearEditFields()
        }

    }

    private fun createProduct(): Product {
        val name = nameET.text.toString()
        val cost = costET.text.toString()
        val image = bitmap
        val product = Product(name, cost, image)
        return product
    }

    private fun clearEditFields() {
        nameET.text.clear()
        costET.text.clear()
        editImageIV.setImageResource(R.drawable.ic_shop)
    }

    private fun init() {
        exitBTN = findViewById(R.id.exitBTN)
        listViewLV = findViewById(R.id.listViewLV)
        addBTN = findViewById(R.id.addBTN)
        costET = findViewById(R.id.costET)
        nameET = findViewById(R.id.nameET)
        editImageIV = findViewById(R.id.editImageIV)
    }

    @Deprecated("This method has been deprecated in favor of using the Activity Result API\n      which brings increased type safety via an {@link ActivityResultContract} and the prebuilt\n      contracts for common intents available in\n      {@link androidx.activity.result.contract.ActivityResultContracts}, provides hooks for\n      testing, and allow receiving results in separate, testable classes independent from your\n      activity. Use\n      {@link #registerForActivityResult(ActivityResultContract, ActivityResultCallback)}\n      with the appropriate {@link ActivityResultContract} and handling the result in the\n      {@link ActivityResultCallback#onActivityResult(Object) callback}.")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        editImageIV = findViewById(R.id.editImageIV)
        when (requestCode) {
            GALARY_REQUEST -> if (resultCode === RESULT_OK) {
                val selectedImage: Uri? = data?.data
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(contentResolver, selectedImage)
                } catch (e: IOException) {
                    e.printStackTrace()
                }
                editImageIV.setImageBitmap(bitmap)
            }
        }
    }
}