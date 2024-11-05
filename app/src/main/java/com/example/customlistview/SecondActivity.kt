package com.example.customlistview

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.AdapterView
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.ListView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

@Suppress("DEPRECATED_IDENTITY_EQUALS")
class SecondActivity : AppCompatActivity(), Removable, Updatable {

    private val GALARY_REQUEST = 1
    private var listAdapter: ListAdapter? = null
    private var products: MutableList<Product> = mutableListOf()
    private var photoUri: Uri? = null
    private var item: Int? = null
    private var check = true

    private lateinit var toolbarSA: Toolbar
    private lateinit var listViewLV: ListView
    private lateinit var addBTN: Button
    private lateinit var descriptionET: EditText
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

        setSupportActionBar(toolbarSA)
        title = ""

        editImageIV.setOnClickListener {
            val photoPickerIntent = Intent(Intent.ACTION_PICK)
            photoPickerIntent.type = "image/*"
            startActivityForResult(photoPickerIntent, GALARY_REQUEST)
        }

        addBTN.setOnClickListener {
            createProduct()

            listAdapter = ListAdapter(this@SecondActivity, products)
            listViewLV.adapter = listAdapter
            listAdapter?.notifyDataSetChanged()
            clearEditFields()
        }
        listViewLV.onItemClickListener =
            AdapterView.OnItemClickListener { parent, view, position, id ->
                val product = listAdapter!!.getItem(position)
                item = position
                val dialog = MyAlertDialog()
                val args = Bundle()
                args.putSerializable("product", product)
                dialog.arguments = args
                dialog.show(supportFragmentManager, "custom")
            }
    }

    override fun onResume() {
        super.onResume()
        check = intent.extras?.getBoolean("newCheck") ?: true
        if (!check) {
            products = intent.getSerializableExtra("list") as MutableList<Product>
            listAdapter = ListAdapter(this, products)
            check = true
        }
        listViewLV.adapter = listAdapter
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_sa, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.exitSAMenu -> {
                finish()
                Toast.makeText(
                    applicationContext,
                    "Программа завершена",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun createProduct() {
        val name = nameET.text.toString()
        val cost = costET.text.toString()
        val description = descriptionET.text.toString()
        val image = photoUri.toString()
        val product = Product(name, cost, description, image)
        products.add(product)
        clearEditFields()
        photoUri = null
    }

    private fun clearEditFields() {
        nameET.text.clear()
        costET.text.clear()
        descriptionET.text.clear()
        editImageIV.setImageResource(R.drawable.ic_shop)
    }

    private fun init() {
        toolbarSA = findViewById(R.id.toolbarSA)
        listViewLV = findViewById(R.id.listViewLV)
        addBTN = findViewById(R.id.addBTN)
        descriptionET = findViewById(R.id.descriptionET)
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
                photoUri = data?.data
                editImageIV.setImageURI(photoUri)
            }
        }
    }

    override fun remove(product: Product) {
        listAdapter?.remove(product!!)
    }

    override fun update(product: Product) {
        val intent = Intent(this, ThirdActivity::class.java)
        intent.putExtra("product", product)
        intent.putExtra("products", this.products as ArrayList<Product>)
        intent.putExtra("position", item)
        intent.putExtra("check", check)
        startActivity(intent)
    }
}