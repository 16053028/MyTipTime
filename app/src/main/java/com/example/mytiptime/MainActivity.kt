package com.example.mytiptime

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mytiptime.ui.theme.MyTipTimeTheme
import java.text.NumberFormat
import java.util.Objects.toString

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyTipTimeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    MyAppScreen()
                }
            }
        }
    }
}

@Composable
fun MyAppScreen() {
    var amountInput by remember {
        mutableStateOf("")
    }

    var amountPercentages by remember {
        mutableStateOf("")
    }

    val tipPercentage = amountPercentages.toDoubleOrNull() ?: 0.0
    val amount = amountInput.toDoubleOrNull() ?: 0.0

    val tip = calTip(amount, tipPercentage)
    Column(
        modifier = Modifier.padding(32.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = stringResource(id = R.string.app_title),
            fontSize = 24.sp,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Spacer(modifier = Modifier.height(16.dp))
        EditAmountField(value = amountInput, onValueChange = {amountInput = it})
        Spacer(modifier = Modifier.height(16.dp))
        EditPercentageField(value = amountPercentages, onValueChange = {amountPercentages = it})
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = stringResource(id = R.string.text_display_tip, tip),
        modifier = Modifier.align(Alignment.CenterHorizontally),
        fontSize = 24.sp,
        fontWeight = FontWeight.Bold
        )
    }

}

@Composable
fun EditAmountField(value: String, onValueChange: (String) -> Unit){
    val numberFormat = NumberFormat.getCurrencyInstance()
//    println(numberFormat.currency)  // on my device in Italy prints: EUR

    val symbol = toString(numberFormat.currency?.symbol)

    TextField(
        value = value,
        onValueChange = onValueChange,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        singleLine = true,
        label =
            {
                Text (
                    text = stringResource(id = R.string.label_input_ammount, symbol),
                    modifier = Modifier.fillMaxWidth()
                    )
            },
    )
}

@Composable
fun EditPercentageField(value: String, onValueChange: (String) -> Unit){

    TextField(
        value = value,
        onValueChange = onValueChange,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        singleLine = true,
        label =
        {
            Text (
                text = stringResource(id = R.string.label_input_percentages),
                modifier = Modifier.fillMaxWidth()
            )
        },
    )
}


private fun calTip(amount: Double, tipPercentage: Double) : String {
    val tip = tipPercentage / 100 * amount
    return NumberFormat.getCurrencyInstance().format(tip)
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MyAppScreen()
}