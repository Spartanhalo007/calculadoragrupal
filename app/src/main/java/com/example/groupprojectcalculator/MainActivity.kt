package com.example.groupprojectcalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import java.lang.Math.*

class MainActivity : AppCompatActivity() {

    var textView_result:TextView?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textView_result=findViewById(R.id.textView_result)
    }
    fun calculate(view : View){
        var button = view as Button
        var buttonText = button.text.toString()
        var concatenate = textView_result?.text.toString() + buttonText
        var show = noLeftZeros(concatenate)
        var lastParenthesisOpen = false
        if (buttonText == "( )"){
            if (lastParenthesisOpen == true) {
                textView_result?.append(")")
                lastParenthesisOpen = false
            } else {
                textView_result?.append("(")
                lastParenthesisOpen = true
            }
        } else if (buttonText=="=") {
            var result = 0.0
            if (concatenate.isNotBlank()) {
                result = evaluate(textView_result?.text.toString())
                textView_result?.text = result.toString()
            }
        } else if (button.id == R.id.button1){
            textView_result?.text="0"
        } else if (button.id == R.id.button19){
            concatenate = removeLastCharacter(concatenate)
            textView_result?.text = concatenate
        } else {
            textView_result?.text=show
        }
    }

    fun removeLastCharacter(input: String): String {
        if (input.isNotEmpty()) {
            return input.substring(0, input.length - 1)
        }
        return input
    }

    fun noLeftZeros(str: String): String {
        var i = 0
        while (i < str.length && str[i] == '0') {
            i++
        }
        return str.substring(i)
    }

    fun noTwoOperations(input: String, newOperator: Char): Boolean {

        val lastChar = input.last()
        val operators = setOf('+', '-', '*', '/')

        return if (operators.contains(lastChar) && operators.contains(newOperator)) {
            lastChar == ')' || newOperator == '('
        } else {
            true
        }
    }

    fun evaluate(string: String): Double{
        return object : Any() {
            var position = -1
            var char = 0

            fun nextChar() {
                char = if (++position < string.length) string[position].toInt() else -1
            }

            fun eat(charToEat: Int): Boolean {
                while (char == ' '.toInt()) nextChar()
                if (char == charToEat){
                    nextChar()
                    return true
                }
                return false
            }

            fun parse(): Double{
                nextChar()
                val x = parseExpression()
                if (position < string.length) throw RuntimeException("Unexpected: "+ char.toChar())
                return x
            }


}