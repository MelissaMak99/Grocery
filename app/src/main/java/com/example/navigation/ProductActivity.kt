package com.example.navigation

import android.annotation.SuppressLint
import android.app.*
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_product2.*
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream
import java.text.SimpleDateFormat
import java.util.*


var i: Int = 0
class ProductActivity : AppCompatActivity() {
    lateinit var option: Spinner
    lateinit var result: TextView
    lateinit var alarmManager: AlarmManager


    private lateinit var layoutManager: RecyclerView.LayoutManager

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("ServiceCast", "NewApi")
    override fun onCreate(savedInstanceState: Bundle?) {

        layoutManager = LinearLayoutManager(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product2)
        var edi3 = findViewById<EditText>(R.id.textview3)
        var edi4 = findViewById<EditText>(R.id.textview4)
        var edi5 = findViewById<EditText>(R.id.textview5)
        var edi6 = findViewById<EditText>(R.id.textview6)


        var addate1: Int = 0
        //val addate1 = addate.toInt()


        var edi1 = findViewById<EditText>(R.id.textview)
        var edi2 = findViewById<EditText>(R.id.textview1)
        var ima1 = findViewById<ImageView>(R.id.imageView)
        var fais = findViewById<Button>(R.id.submit)
        var sortie = findViewById<Button>(R.id.cancel)
        val place = intent.getIntExtra("position", 0)
        val itemcheck = intent.getStringExtra("itemcheck")



        //notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        fais.setOnClickListener {
            val name = edi1.text.toString()
            val quantity = edi2.text.toString()
            var expidate = edi3.text.toString()
            val opidate = edi4.text.toString()
            val substract = edi5.text.toString()
            val addate = edi6.text.toString()


            //val intent1 = Intent(applicationContext,MainActivity::class.java)
            val intent = Intent(this, MainActivity::class.java)
            val sharedPreference = getSharedPreferences("PRODUCT_LIST", Context.MODE_PRIVATE)
            /* val image = null
              var editor = sharedPreference.edit()
              editor.clear()
              editor.putString("name", name)
              editor.putString("quantity", quantity)
              editor.putString("drawable", image)*/




            if (ima1.drawable != null) {
                val bitmap = (imageView.drawable as BitmapDrawable).bitmap
                intent.putExtra("photo", bitmap)
                //val drawable = bitmap?.let { saveImageToInternalStorage(it) }
                //editor.putString("drawable", drawable.toString())
            }
            //editor.commit()

            intent.putExtra("name", name)
            intent.putExtra("popo", place)
            intent.putExtra("itemcheck", itemcheck)
            intent.putExtra("Quantity", quantity)
            //intent.putExtra("photo", ima1)


            startActivity(intent)
            if (expidate.isNotEmpty()) {
                val substract1: Int = substract.toInt()
                //val substract = edi6.text.toString().toInt()
                var sdf = SimpleDateFormat("MM/dd/yyyy")
                val c = Calendar.getInstance()
                c.time = sdf.parse(expidate)
                c.add(Calendar.DAY_OF_YEAR, -substract1)
                val date = Date(c.timeInMillis)
                val alarmIntent = Intent(this, MyAlarmReceiver::class.java)
                val pendingIntent =
                    PendingIntent.getBroadcast(this, i, alarmIntent, PendingIntent.FLAG_ONE_SHOT)

                alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
                alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, date.time, pendingIntent)
                i += 1
            }


            if (opidate.isNotEmpty()) {
                val addate1: Int = addate.toInt()
                // val addate: Int = edi5.text.toString().toInt()
                var sdf = SimpleDateFormat("MM/dd/yyyy")
                val c = Calendar.getInstance()
                c.time = sdf.parse(opidate)
                c.add(Calendar.DAY_OF_YEAR, +addate1)
                val date = Date(c.timeInMillis)
                val alarmIntent = Intent(this, MyAlarmReceiver::class.java)
                val pendingIntent =
                    PendingIntent.getBroadcast(this, i, alarmIntent, PendingIntent.FLAG_ONE_SHOT)

                alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
                alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, date.time, pendingIntent)
                i += 1
            }


        }



        sortie.setOnClickListener {
            finish()
        }

        picturebutton.isEnabled = false
        // react to button being pressed
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.CAMERA), 111)
        }
        else {
            picturebutton.isEnabled = true

            picturebutton.setOnClickListener {


                var i = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                startActivityForResult(i, 101)


            }
        }




        option = findViewById<Spinner>(R.id.spinner1)
        result = findViewById<TextView>(R.id.spinneresult)
        val options = arrayOf(
            "Baking Goods",
            "Cans",
            "Diary",
            "Drinks",
            "Fruits and Vegetables",
            "Meats and Seafoods",
            "Oils",
            "Sauces",
            "Snacks",
            "Spices"
        )
        option.adapter =
            ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, options)

        option.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {
                result.text = "Select a category"
            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                result.text = options.get(p2)

            }


        }


    }



    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 111 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            picturebutton.isEnabled = true
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 101){
            var pic = data?.getParcelableExtra<Bitmap>("data")
            imageView.setImageBitmap(pic)
        }
    }

    private fun saveImageToInternalStorage(bitmap:Bitmap): Uri {
        // Get the image from drawable resource as drawable object
        //val drawable = ContextCompat.getDrawable(applicationContext,drawableId)

        // Get the bitmap from drawable object
        //val bitmap = (drawable as BitmapDrawable).bitmap

        // Get the context wrapper instance
        val wrapper = ContextWrapper(applicationContext)

        // Initializing a new file
        // The bellow line return a directory in internal storage
        var file = wrapper.getDir("images", Context.MODE_PRIVATE)

        // Create a file to save the image
        file = File(file, "${UUID.randomUUID()}.jpg")
        try {
            // Get the file output stream
            val stream: OutputStream = FileOutputStream(file)
            // Compress bitmap
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
            // Flush the stream
            stream.flush()
            // Close stream
            stream.close()
        } catch (e: IOException){ // Catch the exception
            e.printStackTrace()
        }
        // Return the saved image uri
        return Uri.parse(file.absolutePath)
    }




}



