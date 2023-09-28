package com.example.week3.ui.view

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.week3.R
import com.example.week3.ui.theme.Purple40
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AVGCalculatorView() {

    var nilai1 by rememberSaveable { mutableStateOf("") }
    var nilai2 by rememberSaveable { mutableStateOf("") }
    var nilai3 by rememberSaveable { mutableStateOf("") }
    var clicked by remember { mutableStateOf(false) }
    var result by rememberSaveable { mutableStateOf(0f) }

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = Purple40)
                ) {
                    Text(
                        text = "Student Score",
                        modifier = Modifier
                            .padding(16.dp),
                        color = Color.White
                    )
                }

                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = "Logo",
                    modifier = Modifier
                        .size(175.dp)
                        .padding(vertical = 24.dp)
                )

                CustomTextFieldPurple(
                    value = nilai1,
                    onValueChanged = {
                        if (it.isNumeric()) {
                            nilai1 = it
                        }
                    },
                    text = "Rafi's Score",
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Next
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                )

                CustomTextFieldPurple(
                    value = nilai2,
                    onValueChanged = {
                        if (it.isNumeric()) {
                            nilai2 = it
                        }
                    },
                    text = "Kevin's Score",
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Next
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                )

                CustomTextFieldPurple(
                    value = nilai3,
                    onValueChanged = {
                        if (it.isNumeric()) {
                            nilai3 = it
                        }
                    },
                    text = "Bahar's Score",
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Next
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                )

                Button(
                    onClick = {
                        if (nilai1.isNotBlank() && nilai2.isNotBlank() && nilai3.isNotBlank()) {
                            result = Average(
                                ((nilai1.toFloatOrNull() ?: 0f)),
                                (nilai2.toFloatOrNull() ?: 0f),
                                (nilai3.toFloatOrNull() ?: 0f)
                            )
                            if (result < 70) {
                                scope.launch {
                                    snackbarHostState.showSnackbar(
                                        "Siswa perlu diberi soal tambahan."
                                    )
                                }
                            } else {
                                scope.launch {
                                    snackbarHostState.showSnackbar(
                                        "Siswa mengerti pembelajaran."
                                    )
                                }
                            }
                            clicked = true
                        }
                    },
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 16.dp),
                    colors = ButtonDefaults.buttonColors(Purple40)
                )
                {
                    Text(
                        text = "CALCULATE AVERAGE",
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }

                if (clicked) {
                    OutlinedCard(
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surface,
                        ),
                        border = BorderStroke(1.dp, Purple40),
                        modifier = Modifier
                            .padding(horizontal = 16.dp, vertical = 8.dp),
                        shape = RoundedCornerShape(40.dp)
                    ) {
                        Text(
                            text = "Average Score : $result",
                            modifier = Modifier
                                .padding(16.dp),
                            textAlign = TextAlign.Center,
                            color = Purple40
                        )
                    }
                }

            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTextFieldPurple(
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
                style = TextStyle(color = Purple40)
            )
        },
        keyboardOptions = keyboardOptions,
        modifier = modifier,
        shape = RoundedCornerShape(40.dp),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Purple40,
            unfocusedBorderColor = Purple40
        )
    )
}

fun Average(
    nilai1: Float,
    nilai2: Float,
    nilai3: Float,
): Float {
    return (nilai1 + nilai2 + nilai3) / 3
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AVGCalculatorPreview() {
    AVGCalculatorView()
}