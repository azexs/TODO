package com.example.todo

import android.app.Activity
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.app.TimePickerDialog
import android.widget.*
import java.util.*
import android.content.Intent
import android.content.res.Resources
import kotlinx.android.synthetic.main.task_add.*
import android.widget.ArrayAdapter




class AddTask : AppCompatActivity(),AdapterView.OnItemSelectedListener {
    override fun onNothingSelected(p0: AdapterView<*>?) {

    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

    }

    var image: Int = 0
    var selectedImg: ImageView? = null
    lateinit var title: String
    lateinit var time: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.task_add)

        val calendar: Calendar = Calendar.getInstance()
        var currentHour: Int
        var currentMinute: Int

        var chooseTime = findViewById<View>(R.id._timePicker) as TextView

        chooseTime.setOnClickListener {
            currentHour = calendar.get(Calendar.HOUR_OF_DAY)
            currentMinute = calendar.get(Calendar.MINUTE)

            val timePickerDialog = TimePickerDialog(
                this,
                TimePickerDialog.OnTimeSetListener(function = { _, h, m ->
                    if(m<10){
                        chooseTime.text = "$h:0$m"
                        this.time="$h:0$m"
                    } else{
                        chooseTime.text = "$h:$m"
                        this.time="$h:$m"
                    }

                })
                , currentHour, currentMinute, true)

            timePickerDialog.show()
        }

        for (position in 0..3) {
            var resId = resources.getIdentifier("img" + (position+1), "id", packageName)
            var imgId = resources.getIdentifier("asd" + (position+1),  "drawable",packageName)
            var imgView = findViewById<View>(resId) as ImageView
            imgView.setImageResource(imgId)
            imgView.setOnClickListener {
                if(selectedImg != null) selectedImg!!.setBackgroundColor(0)
                    selectedImg = imgView
                selectedImg!!.setBackgroundColor(resources.getColor(R.color.imgBack))
                this.image = imgId
            }
        }

        val categories = ArrayList<String>()
        categories.add("1")
        categories.add("2")
        categories.add("3")
        val dataAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, categories)
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = dataAdapter
        spinner.onItemSelectedListener = this
    }

    fun add(view: View) {
       this.title=editText.text.toString()
        var priority = spinner.selectedItem.toString().toInt()
        var intent = Intent()
        intent.putExtra("priority",priority)
        intent.putExtra("title",title)
        intent.putExtra("time",time)
        intent.putExtra("image",image)
        setResult(Activity.RESULT_OK, intent)
        finish()
    }

    override fun onBackPressed() {
        setResult(Activity.RESULT_CANCELED, intent)
        finish()
        super.onBackPressed()
    }

}
