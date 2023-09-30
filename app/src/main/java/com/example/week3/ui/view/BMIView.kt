package com.example.week3.ui.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.text.isDigitsOnly
import com.example.week3.R

@Composable
fun BMIView() {

    var weight by rememberSaveable { mutableStateOf("") }
    var height by rememberSaveable { mutableStateOf("") }

    var isWeightValid by rememberSaveable { mutableStateOf(true) }
    var isHeightValid by rememberSaveable { mutableStateOf(true) }
    val openAlertDialog = remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            painter = painterResource(id = R.drawable.baseline_face_24),
            contentDescription = "Face Icon",
            modifier = Modifier
                .size(100.dp),
            tint = Color.Blue
        )

        CustomNumberInput(
            value = weight,
            onValueChanged = {
                if (it.isNumeric()) {
                    weight = it
                }
            },
            text = "Weight in Kg",
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            isNumberValid = isWeightValid,
            errorText = "Please enter a valid weight greater than 0"
        )

        CustomNumberInput(
            value = height,
            onValueChanged = {
                if (it.isNumeric()) {
                    height = it
                }
            },
            text = "Height in Cm",
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            isNumberValid = isHeightValid,
            errorText = "Please enter a valid height greater than 0"
        )

        Button(
            onClick = {
                isWeightValid = isValid(weight.toDoubleOrNull() ?: 0.0)
                isHeightValid = isValid(height.toDoubleOrNull() ?: 0.0)
                openAlertDialog.value = true
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            enabled = weight.isNotBlank() && height.isNotBlank()
        )
        {
            Text(
                text = "Calculate BMI"
            )
        }

        if (isHeightValid && isWeightValid && openAlertDialog.value) {
            val result =
                hitungBMI((height.toDoubleOrNull() ?: 0.0) / 100, weight.toDoubleOrNull() ?: 0.0)
            val roundResult = String.format("%.1f", result)
            val roundHeight = String.format("%.2f", (height.toDouble() / 100))

            var BMI: String

            if (result < 18.5) {
                BMI = "Underweight"
            } else if (result > 18.5 && result < 24.9) {
                BMI = "Normal"
            } else if (result > 25 && result < 29.9) {
                BMI = "Overweight"
            } else {
                BMI = "Obese"
            }

            AlertDialog(
                onDismissRequest = { openAlertDialog.value = false },
                title = {
                    Text(
                        text = "Your BMI Analysis"
                    )
                },
                text = {
                    Text(
                        text = """
                            Your Height : $roundHeight m
                            Your Weight : $weight kg
                            Your BMI Score : $roundResult
                            You are $BMI
                        """.trimIndent()
                    )
                },
                confirmButton = {
                    Button(
                        onClick = { openAlertDialog.value = false }
                    ) {
                        Text(
                            text = "Ok"
                        )
                    }
                }
            )
        }
    }
}

fun String.isNumeric(): Boolean {
    // Cek apakah Inputnya adalah angka atau angka desimal
    return try {
        this.toDouble() // Cobakan untuk mengonversi string ke Double
        true
    } catch (e: NumberFormatException) {
        false
    }
}

fun isValid(value: Double): Boolean {
    var TF: Boolean = value > 0
    return TF
}

fun hitungBMI(
    height: Double,
    weight: Double
): Double {
    return weight / (height * height)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomNumberInput(
    value: String,
    onValueChanged: (String) -> Unit,
    text: String,
    keyboardOptions: KeyboardOptions,
    modifier: Modifier = Modifier,
    isNumberValid: Boolean,
    errorText: String
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChanged,
        label = { Text(text = text) },
        keyboardOptions = keyboardOptions,
        modifier = modifier,
        isError = !isNumberValid,
        shape = RoundedCornerShape(40.dp),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Color.Blue,
            unfocusedBorderColor = Color.Blue
        )
    )

    if (!isNumberValid) {
        Text(
            text = errorText,
            color = Color.Red,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, bottom = 4.dp)
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun BMIPreview() {
    BMIView()
}