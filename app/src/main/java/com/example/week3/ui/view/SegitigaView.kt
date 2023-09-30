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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.week3.R

@Composable
fun SegitigaView() {

    var base by rememberSaveable { mutableStateOf("") }
    var height by rememberSaveable { mutableStateOf("") }

    var luas =
        if (base.isNotEmpty() && height.isNotEmpty()) {
            hitungLuas(base.toDoubleOrNull() ?: 0.0, height.toDoubleOrNull() ?: 0.0)
        } else {
            0.0
        }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            painter = painterResource(id = R.drawable.outline_play_arrow_24),
            contentDescription = "Icon",
            modifier = Modifier
                .size(250.dp)
                .fillMaxWidth()
                .rotate(270f),
            tint = Color.Blue
        )

        CustomTextField(
            value = base,
            onValueChanged = {
                if (it.isNumeric()) {
                    base = it
                }
            },
            text = "Base",
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
        )

        CustomTextField(
            value = height,
            onValueChanged = {
                if (it.isNumeric()) {
                    height = it
                }
            },
            text = "Height",
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
        )

        Text(
            text = "The Triangle Area is:",
            fontSize = 30.sp,
            fontWeight = FontWeight.Medium
        )

        Text(
            text = "$luas",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

fun hitungLuas(
    base: Double,
    height: Double
): Double {
    return (base * height) / 2
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTextField(
    value: String,
    onValueChanged: (String) -> Unit,
    text: String,
    keyboardOptions: KeyboardOptions,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChanged,
        label = {
            Text(
                text = text,
                style = TextStyle(color = Color.Blue)
            )
        },
        keyboardOptions = keyboardOptions,
        modifier = modifier,
        shape = RoundedCornerShape(40.dp),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Color.Blue,
            unfocusedBorderColor = Color.Blue
        )
    )
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun SegitigaPreview() {
    SegitigaView()
}