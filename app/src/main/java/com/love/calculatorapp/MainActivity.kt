package com.love.calculatorapp

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import net.objecthunter.exp4j.ExpressionBuilder

class MainActivity : AppCompatActivity() {

    private var inputtedDigit = StringBuilder()
 //   var operation: Char = ' '
//    var firstDigit: Double = 0.00
//    var lastDigit: Double = 0.00
private var lastNumeric: Boolean = false
    private var lastDecimalPoint: Boolean = false
    private var error: Boolean = false





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        result_tv.text = "0"
        initViews()


    }

    private fun initViews() {
        numericalBtns()
        operationalBtns()
        functionalBtns()
    }

    private fun functionalBtns() {
        delete_tv.setOnClickListener{
            val length = inputtedDigit.length
            if(length>0) {
                inputtedDigit.deleteCharAt(length - 1)
                result_tv.text = inputtedDigit
            }
        }
        clear_tv.setOnClickListener{
            inputtedDigit.clear()
            result_tv.text = ""
        }
        equals_tv.setOnClickListener{
            performOperation()
        }
    }

    private fun performOperation() {
        if (lastNumeric && !error){
            val expressionString = inputtedDigit.toString().replace(getString(R.string.divide),
                "/").replace(getString(R.string.multiply), "*")
            Log.v("log", expressionString)
            try {
                val expression = ExpressionBuilder(expressionString).build()
                val result = expression.evaluate()
                Log.v("log", result.toString())
                val resultString = result.toFloat().toString()
                values_tv.text = resultString

            }
            catch (e: Exception){
                val errorStr = e.localizedMessage ?: ""
                Log.v("log", errorStr)

                this.toast(errorStr)

                error = true

            }
        }
        else{
            this.toast()
        }

    }

    private fun operationalBtns() {
        add_tv.setOnClickListener{
            addOperatorToScreen(getString(R.string.add))
        }
        divide_tv.setOnClickListener{
            addOperatorToScreen(getString(R.string.divide))
        }
        subtract_tv.setOnClickListener{
            addOperatorToScreen(getString(R.string.subtract))
        }
        multiply_tv.setOnClickListener{
            addOperatorToScreen(getString(R.string.multiply))
        }
        percent_tv.setOnClickListener{
            addOperatorToScreen(getString(R.string.percent))
        }
    }

    private fun addOperatorToScreen(string: String) {
        if(lastNumeric && !error){
            inputtedDigit.append(string)
            result_tv.text = inputtedDigit.toString()
            lastNumeric = false
            lastDecimalPoint = false
        }
        else this.toast()
    }

    private fun numericalBtns() {
        zero.setOnClickListener{
            addDigitToScreen("0")
        }
        one.setOnClickListener{
            addDigitToScreen("1")
        }
        two.setOnClickListener{
            addDigitToScreen("2")
        }
        three.setOnClickListener{
            addDigitToScreen("3")
        }
        four.setOnClickListener{
            addDigitToScreen("4")
        }
        five.setOnClickListener{
            addDigitToScreen("5")
        }
        six.setOnClickListener{
            addDigitToScreen("6")
        }
        seven.setOnClickListener{
            addDigitToScreen("7")
        }
        eight.setOnClickListener{
            addDigitToScreen("8")
        }
        nine.setOnClickListener{
            addDigitToScreen("9")
        }
        point_tv.setOnClickListener{
            addDecimalToScreen()
        }
    }

    private fun addDecimalToScreen() {
        if(lastNumeric && !lastDecimalPoint && !error){
            inputtedDigit.append(".")
            result_tv.text = inputtedDigit.toString()
            lastNumeric = false
            lastDecimalPoint = true
        }
        else this.toast()
    }

    private fun addDigitToScreen(number: String){
      if(error){
          this.toast()
      }
        else{
          inputtedDigit.append(number)
          result_tv.text = inputtedDigit.toString()
          lastNumeric = true
      }
    }

    private fun Context.toast(message: String = "Invalid Input!"){
        Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
    }
}