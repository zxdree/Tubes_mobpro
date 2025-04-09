package com.adre0089.tubes_mobpro.ui.screen.katalogbaju

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.adre0089.Katalog
import com.adre0089.tubes_mobpro.R
import com.adre0089.tubes_mobpro.ui.theme.Tubes_mobproTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JaketScreenContent(katalog: Katalog, modifier: Modifier = Modifier) {
    var lebarDada by rememberSaveable { mutableStateOf("") }
    var lebarDadaError by rememberSaveable { mutableStateOf(false) }
    var panjangBadan by rememberSaveable { mutableStateOf("") }
    var panjangBadanError by rememberSaveable { mutableStateOf(false) }
    var expanded by remember { mutableStateOf(false) }

    val dorpDown = listOf("Coach Jacket", "Hoodie", "Varsity Jacket")
    var dropdownError by rememberSaveable { mutableStateOf(false) }

    var selectedText  by rememberSaveable { mutableStateOf("") }
    var kategori by rememberSaveable { mutableIntStateOf(0) }
    var selectedImage by rememberSaveable { mutableIntStateOf(R.drawable.ukuran_s) }


    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(id = R.string.Batas_input),
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.fillMaxWidth()
        )
        Text(
            text = stringResource(id = R.string.C_jaket),
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.fillMaxWidth()
        )
        Text(
            text = stringResource(id = R.string.h_jaket),
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.fillMaxWidth()
        )
        Text(
            text = stringResource(id = R.string.v_jaket),
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.fillMaxWidth()
        )
        Text(
            text = stringResource(id = R.string.Ukuran),
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = lebarDada,
            onValueChange = { lebarDada = it },
            label = { Text(text = stringResource(R.string.lebar_dada)) },
            trailingIcon = { IconPickerr(lebarDadaError, "cm") },
            supportingText = { ErorrHitt(lebarDadaError) },
            isError = lebarDadaError,
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = panjangBadan,
            onValueChange = { panjangBadan = it },
            label = { Text(text = stringResource(R.string.panjang_badan)) },
            trailingIcon = { IconPickerr(panjangBadanError, "cm") },
            supportingText = { ErorrHitt(panjangBadanError) },
            isError = panjangBadanError,
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            modifier = Modifier.fillMaxWidth()
        )
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded },
            modifier = Modifier.fillMaxWidth()
        ) {
            OutlinedTextField(
                value = selectedText,
                onValueChange = {},
                readOnly = true,
                label = { Text("Pilih Jaket") },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                },
                isError = dropdownError,
                supportingText = {
                    if (dropdownError) {
                        Text(text = "Pilih jenis jaket terlebih dahulu.")
                    }
                },
                colors = ExposedDropdownMenuDefaults.textFieldColors(),
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth()
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                dorpDown.forEach { jaket ->
                    DropdownMenuItem(
                        text = { Text(jaket) },
                        onClick = {
                            selectedText = jaket
                            expanded = false
                        }
                    )
                }
            }
        }


        Button(
            onClick = {
                lebarDadaError = (lebarDada.isEmpty() || lebarDada == "0")
                panjangBadanError = (panjangBadan.isEmpty() || panjangBadan == "0")
                dropdownError = selectedText.isEmpty()
                if (lebarDadaError || panjangBadanError || dropdownError) return@Button

                kategori = getLategori(lebarDada.toFloat(), lebarDada.toFloat(), selectedText)
                selectedImage = getImage(panjangBadan.toFloat(), panjangBadan.toFloat(), selectedText)
            },
            modifier = Modifier.padding(top = 8.dp),
            contentPadding = PaddingValues(horizontal = 32.dp, vertical = 16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF001F5B),  // Warna latar (biru dongker)
                contentColor = Color.White           // Warna teks/icon di dalam button
            )
        ) {
            Text(text = stringResource(R.string.cari_ukuran))
        }
        if (kategori != 0) {
            HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp), thickness = 1.dp)
            Image(
                painter = painterResource(id = selectedImage),
                contentDescription = stringResource(kategori, katalog.nama),
                modifier = Modifier.size(300.dp)
            )
            Text(
                text = stringResource(kategori),
                style = MaterialTheme.typography.headlineLarge,
                fontSize = 18.5.sp
            )
        }
    }
}



private fun getLategori(lebarDada: Float, panjangTubuh: Float, jenisJaket: String): Int {
  return when(jenisJaket){
      "Coach Jacket" -> {
          when{
              lebarDada >= 61 || panjangTubuh >= 73 -> R.string.c_xxl
              lebarDada >= 59 || panjangTubuh >= 71 -> R.string.c_xl
              lebarDada >= 57 || panjangTubuh >= 69 -> R.string.c_l
              lebarDada >= 55 || panjangTubuh >= 67 -> R.string.c_m
              lebarDada >= 53 || panjangTubuh >= 65 -> R.string.c_s
              else->R.string.Custem_Ukuran
          }
      }
      "Hoodie" -> {
          when{
              lebarDada >= 62 || panjangTubuh >= 72 -> R.string.h_xxl
              lebarDada >= 60 || panjangTubuh >= 70 -> R.string.h_xl
              lebarDada >= 58 || panjangTubuh >= 68 -> R.string.h_l
              lebarDada >= 56 || panjangTubuh >= 66 -> R.string.h_m
              lebarDada >= 54 || panjangTubuh >= 64 -> R.string.h_s
              else->R.string.Custem_Ukuran
          }
      }
      "Varsity Jacket" -> {
          when{

              lebarDada >= 62 || panjangTubuh >= 73 -> R.string.v_xxl
              lebarDada >= 60 || panjangTubuh >= 71 -> R.string.v_xl
              lebarDada >= 58 || panjangTubuh >= 69 -> R.string.v_l
              lebarDada >= 56 || panjangTubuh >= 67 -> R.string.v_m
              lebarDada >= 54 || panjangTubuh >= 65 -> R.string.v_s
              else->R.string.Custem_Ukuran
          }
      }
      else -> R.string.Custem_Ukuran
  }
}

private fun getImage(lebarDada: Float, panjangTubuh: Float, jenisJaket: String): Int {
    return when(jenisJaket){
        "Coach Jacket" -> {
            when{
                lebarDada >= 61 || panjangTubuh >= 73 -> R.drawable.cj_xxl
                lebarDada >= 59 || panjangTubuh >= 71 -> R.drawable.cj_xl
                lebarDada >= 57 || panjangTubuh >= 69 -> R.drawable.cj_l
                lebarDada >= 55 || panjangTubuh >= 67 -> R.drawable.cj_m
                lebarDada >= 53 || panjangTubuh >= 65 -> R.drawable.cj_s
                else->R.string.Custem_Ukuran
            }
        }
        "Hoodie" -> {
            when{
                lebarDada >= 62 || panjangTubuh >= 72 -> R.drawable.h_s
                lebarDada >= 60 || panjangTubuh >= 70 -> R.drawable.h_m
                lebarDada >= 58 || panjangTubuh >= 68 -> R.drawable.h_l
                lebarDada >= 56 || panjangTubuh >= 66 -> R.drawable.h_xl
                lebarDada >= 54 || panjangTubuh >= 64 -> R.drawable.h_xxl
                else->R.string.Custem_Ukuran
            }
        }
        "Varsity Jacket" -> {
            when{
                lebarDada >= 62 || panjangTubuh >= 73 -> R.drawable.v_s
                lebarDada >= 60 || panjangTubuh >= 71 -> R.drawable.v_m
                lebarDada >= 58 || panjangTubuh >= 69 -> R.drawable.v_l
                lebarDada >= 56 || panjangTubuh >= 67 -> R.drawable.v_xl
                lebarDada >= 54 || panjangTubuh >= 65 -> R.drawable.v_xxl
                else->R.string.Custem_Ukuran
            }
        }
        else -> R.string.Custem_Ukuran
    }
}

@Composable
fun IconPickerr(isError: Boolean, unit: String) {
    if (isError) {
        Icon(imageVector = Icons.Filled.Warning, contentDescription = null)

    } else {
        Text(text = unit)
    }
}

@Composable
fun ErorrHitt(isError: Boolean) {
    if (isError) {
        Text(text = stringResource(R.string.input_invalid_LP))

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Jaket(navController: NavHostController) {
    val data = listOf(
        Katalog("Ukuran_s", R.drawable.ukuran_s),
        Katalog("Ukuran_m", R.drawable.ukuran_m),
        Katalog("Ukuran_l", R.drawable.ukuran_l),
        Katalog("Ukuran_xl", R.drawable.ukuran_xl),
        Katalog("Ukuran_xxl", R.drawable.ukuran_xxl),
        Katalog("Ukuran_xxxl", R.drawable.ukuran_xxxl),

        Katalog("Ukuran_s_lp", R.drawable.ukuran_s_lp),
        Katalog("Ukuran_m_lp", R.drawable.ukuran_m_lp),
        Katalog("Ukuran_l_lp", R.drawable.ukuran_l_lp),
        Katalog("Ukuran_xl_lp", R.drawable.ukuran_xl_lp),
        Katalog("Ukuran_xxl_lp", R.drawable.ukuran_xxl_lp),
        Katalog("Ukuran_xxxl_lp", R.drawable.ukuran_xxxl_lp),






        )
    val katalogkategorii = data[0]
    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = {navController.popBackStack()}) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.kembali),
                            tint = Color.White,

                        )
                    }
                },
                title = { Text(text = stringResource(id = R.string.Kemeja)) },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = Color(0xFF001F5B),
                    titleContentColor = Color.White,
                    actionIconContentColor = Color.White
                ),


            )
        }
    ) { innerPadding ->
        JaketScreenContent(katalog = katalogkategorii, Modifier.padding(innerPadding))
    }
}

@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun JaketScreenPreview() {
    Tubes_mobproTheme {
        Jaket(rememberNavController())
    }
}
