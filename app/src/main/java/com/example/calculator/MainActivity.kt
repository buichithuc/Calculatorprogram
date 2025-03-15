package com.example.calculator

import android.app.appsearch.AppSearchManager.SearchContext
import android.icu.text.DecimalFormat
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.calculator.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var mainBinding : ActivityMainBinding
    var number : String? = null

    var firstNumber : Double = 0.0
    var lastNumber : Double = 0.0

    var status : String? = null
    var operator : Boolean = false

    val myFormatter = DecimalFormat("#####.#####")

    var history : String = ""
    var currentResult : String = ""

    var dotControl : Boolean = true
    var buttonEqualsControl : Boolean = false



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        val view = mainBinding.root
        setContentView(view)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        mainBinding.textViewResult.text = "0"

        mainBinding.btn0.setOnClickListener {
            onNumberClicked("0")
        }
        mainBinding.btn1.setOnClickListener {
            onNumberClicked("1")
        }
        mainBinding.btn2.setOnClickListener {
            onNumberClicked("2")
        }
        mainBinding.btn3.setOnClickListener {
            onNumberClicked("3")
        }
        mainBinding.btn4.setOnClickListener {
            onNumberClicked("4")
        }
        mainBinding.btn5.setOnClickListener {
            onNumberClicked("5")
        }
        mainBinding.btn6.setOnClickListener {
            onNumberClicked("6")
        }
        mainBinding.btn7.setOnClickListener {
            onNumberClicked("7")
        }
        mainBinding.btn8.setOnClickListener {
            onNumberClicked("8")
        }
        mainBinding.btn9.setOnClickListener {
            onNumberClicked("9")
        }
        mainBinding.btnDivi.setOnClickListener {
            history = mainBinding.textViewHistory.text.toString()
            currentResult = mainBinding.textViewResult.text.toString()
            mainBinding.textViewHistory.text = history.plus(currentResult).plus("/")

            if(operator){
                when(status){
                    "multiplication" -> Multi()
                    "division" -> Division()
                    "subtraction" -> Subtraction()
                    "addition" -> Plus()
                    else -> firstNumber = mainBinding.textViewResult.text.toString().toDouble()
                }
            }
            status = "division"
            operator = false;
            number = null
            dotControl = true;

        }

        mainBinding.btnSub.setOnClickListener {
            history = mainBinding.textViewHistory.text.toString()
            currentResult = mainBinding.textViewResult.text.toString()
            mainBinding.textViewHistory.text = history.plus(currentResult).plus("-")
            if(operator){
                when(status){
                    "multiplication" -> Multi()
                    "division" -> Division()
                    "subtraction" -> Subtraction()
                    "addition" -> Plus()
                    else -> firstNumber = mainBinding.textViewResult.text.toString().toDouble()
                }
            }
            status = "subtraction"
            operator = false;
            number = null
            dotControl = true;

        }

        mainBinding.btnPlus.setOnClickListener {
            history = mainBinding.textViewHistory.text.toString()
            currentResult = mainBinding.textViewResult.text.toString()
            mainBinding.textViewHistory.text = history.plus(currentResult).plus("+")
            if(operator){
                when(status){
                    "multiplication" -> Multi()
                    "division" -> Division()
                    "subtraction" -> Subtraction()
                    "addition" -> Plus()
                    else -> firstNumber = mainBinding.textViewResult.text.toString().toDouble()
                }
            }
            status = "addition"
            operator = false;
            number = null
            dotControl = true;

        }

        mainBinding.btnMulti.setOnClickListener {
            history = mainBinding.textViewHistory.text.toString()
            currentResult = mainBinding.textViewResult.text.toString()
            mainBinding.textViewHistory.text = history.plus(currentResult).plus("*")
            if(operator){
                when(status){
                    "multiplication" -> Multi()
                    "division" -> Division()
                    "subtraction" -> Subtraction()
                    "addition" -> Plus()
                    else -> firstNumber = mainBinding.textViewResult.text.toString().toDouble()
                }
            }
            status = "multiplication"
            operator = false;
            number = null
            dotControl = true;

        }

        mainBinding.btnPoint.setOnClickListener {
            if(dotControl){
                number = if(number == null){
                    "0."
                }
                else if(buttonEqualsControl){
                    if(mainBinding.textViewResult.text.toString().contains(".")){
                        mainBinding.textViewResult.text.toString()
                    } else{
                        mainBinding.textViewResult.text.toString().plus(".")
                    }
                }
                else{
                    "$number."
                }
                mainBinding.textViewResult.text = number
            }
            dotControl = false;

        }

        mainBinding.btnEqual.setOnClickListener {
            history = mainBinding.textViewHistory.text.toString()
            currentResult = mainBinding.textViewResult.text.toString()

            if(operator){
                when(status){
                    "multiplication" -> Multi()
                    "division" -> Division()
                    "subtraction" -> Subtraction()
                    "addition" -> Plus()
                    else -> firstNumber = mainBinding.textViewResult.text.toString().toDouble()
                }
                mainBinding.textViewHistory.text = history.plus(currentResult).plus("=")
            }
            operator = false;
            dotControl = true;
            buttonEqualsControl = true;
        }

        mainBinding.btnCE.setOnClickListener {
            number?.let{
                if(it.length == 1){
                    onButtonC()
                } else{
                    number = it.substring(0, it.length-1)
                    mainBinding.textViewResult.text = number
                    dotControl =!number!!.contains(".")
                }
            }

        }
        mainBinding.btnC.setOnClickListener {
            onButtonC()
        }
        mainBinding.btnSign.setOnClickListener {

        }
        mainBinding.btnBS.setOnClickListener {

        }

    }

    fun onNumberClicked(clickedNumber : String){
        if(number == null){
            number = clickedNumber
        } else if(buttonEqualsControl){
            number = if(dotControl){
                clickedNumber
            }else{
                mainBinding.textViewResult.text.toString().plus(clickedNumber)
            }
            firstNumber = number!!.toDouble()
            lastNumber = 0.0
            status = null
            mainBinding.textViewHistory.text = ""
        }
        else{
            number += clickedNumber
        }
        mainBinding.textViewResult.text = number

        operator = true
        buttonEqualsControl = false
    }

    fun Plus(){
        lastNumber = mainBinding.textViewResult.text.toString().toDouble()
        firstNumber += lastNumber
        mainBinding.textViewResult.text = myFormatter.format(firstNumber)
    }

    fun Subtraction(){
        lastNumber = mainBinding.textViewResult.text.toString().toDouble()
        firstNumber -= lastNumber
        mainBinding.textViewResult.text = myFormatter.format(firstNumber)
    }

    fun Multi(){
        lastNumber = mainBinding.textViewResult.text.toString().toDouble()
        firstNumber *= lastNumber
        mainBinding.textViewResult.text = myFormatter.format(firstNumber)
    }
    fun Division(){
        lastNumber = mainBinding.textViewResult.text.toString().toDouble()
        if(lastNumber == 0.0){
            Toast.makeText(applicationContext," The divisor cannot be zero", Toast.LENGTH_LONG).show()
        }else{
            firstNumber /= lastNumber
            mainBinding.textViewResult.text = myFormatter.format(firstNumber)
        }

    }

    fun onButtonC(){
        number = null
        status = null
        mainBinding.textViewHistory.text = ""
        mainBinding.textViewResult.text = "0"
        firstNumber = 0.0
        lastNumber = 0.0
        dotControl = true;
        buttonEqualsControl = false


    }

























}