package com.example.worksheet2_q2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextField
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.worksheet2_q2.ui.theme.Worksheet2Q2Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Worksheet2Q2Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    BasicCalculator(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun BasicCalculator(name: String, modifier: Modifier = Modifier) {

    var field1Text by remember { mutableStateOf(value = "") }
    var field2Text by remember { mutableStateOf(value = "") }
    var operator by remember { mutableStateOf("") }
    var result by remember { mutableStateOf("") }

    val pattern = remember { Regex("\\d+\$") }
    val pattern1 = remember { Regex("^[\\d.]+\$") }
    val pattern2 = remember { Regex("^[\\d+\\-/*]+\$") }


    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        TextField(
            value = field1Text,
            //  Type checks only numbers and "." at input
            onValueChange = {
                if (it.isEmpty() || it.matches(pattern1)){
                    field1Text = it
                }

            },
            label = { Text("Number 1") },
        )
        Spacer(modifier = Modifier.height(10.dp))
        TextField(
            value = field2Text,
            //  Type checks only numbers and "." at input
            onValueChange = {
                if (it.isEmpty() || it.matches(pattern1)){
                    field2Text = it
                }
            },
            label = { Text("Number 2") },

        )
        Spacer(modifier = Modifier.height(10.dp))
        OperatorRow(operator, onOperatorChange = {
            op -> operator = op
        })
        Button(onClick = {
            result = executeOperation(operator, field1Text, field2Text)
            field1Text = ""
            field2Text = ""
        }) {
            Text(
                text = "Calculate",
                fontSize = 15.sp,
                overflow = TextOverflow.Ellipsis
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = result,
            fontSize = 25.sp,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun OperatorRow(operator: String, onOperatorChange: (String) -> Unit) {
    val operators = listOf("+", "-", "*", "/", "%")

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        operators.forEach { op: String ->
            Button(
                onClick = {
                    onOperatorChange(op)
                },
                modifier = Modifier
                    .size(60.dp),
                // Check if button is selected
                colors = if (operator == op){
                    ButtonDefaults.buttonColors(containerColor = Color.Gray)
                }
                else {
                    ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
                }
            ) {
                Text(
                    text = op,
                    fontSize = 24.sp,
                    color = Color.Black
                )
            }
        }
    }
}

fun executeOperation(operator: String, num1: String, num2: String): String {
    // Convert num1 and num2 to floats and handle exceptions
    val number1: Float
    val number2: Float

    try {
        number1 = num1.toFloat()
        number2 = num2.toFloat()
    } catch (e: NumberFormatException) {
        return "Error: Invalid number format"
    }

    // Check for valid operator
    return when (operator) {
        "+" -> (number1 + number2).toString()
        "-" -> (number1 - number2).toString()
        "*" -> (number1 * number2).toString()
        "/" -> {
            if (number2 == 0f) {
                "Error: Division by zero is undefined"
            } else {
                (number1 / number2).toString()
            }
        }
        "%" -> {
            if (number2 == 0f) {
                "Error: Modulus of zero is undefined"
            } else {
                (number1 % number2).toString()
            }
        }
        else -> "Error: Invalid operator"
    }
}





@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Worksheet2Q2Theme {
        BasicCalculator("Android")
    }
}