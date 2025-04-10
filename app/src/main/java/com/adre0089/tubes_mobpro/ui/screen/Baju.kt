package com.adre0089.tubes_mobpro.ui.screen

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
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

@Composable
fun ScreenContent(katalog: Katalog, modifier: Modifier = Modifier) {
    var lebarDada by rememberSaveable { mutableStateOf("") }
    var lebarDadaError by rememberSaveable { mutableStateOf(false) }
    var panjangBadan by rememberSaveable { mutableStateOf("") }
    var panjangBadanError by rememberSaveable { mutableStateOf(false) }
    val radioOptions = listOf(
        stringResource(id = R.string.lengan_pendek),
        stringResource(id = R.string.lengan_panjang)
    )
    var katalogBaju by rememberSaveable { mutableStateOf(radioOptions[0]) }
    var kategori by rememberSaveable { mutableIntStateOf(0) }
    var selectedImage by rememberSaveable { mutableIntStateOf(R.drawable.ukuran_s) }


    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
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
            text = stringResource(id = R.string.baju),
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = lebarDada,
            onValueChange = { lebarDada = it },
            label = { Text(text = stringResource(R.string.lebar_dada)) },
            trailingIcon = { IconPicker(lebarDadaError, "cm") },
            supportingText = { ErorrHit(lebarDadaError) },
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
            trailingIcon = { IconPicker(panjangBadanError, "cm") },
            supportingText = { ErorrHit(panjangBadanError) },
            isError = panjangBadanError,
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            modifier = Modifier.fillMaxWidth()
        )
        Row(
            modifier = Modifier
                .padding(top = 6.dp)
                .border(1.dp, Color.Gray, RoundedCornerShape(4.dp))
        ) {
            radioOptions.forEach { text ->
                GenderOption(
                    label = text,
                    isSelected = katalogBaju == text,
                    modifier = Modifier
                        .selectable(
                            selected = katalogBaju == text,
                            onClick = { katalogBaju = text },
                            role = Role.RadioButton
                        )
                        .weight(1f)
                        .padding(16.dp)
                )
            }
        }
        Button(
            onClick = {
                lebarDadaError = (lebarDada.isEmpty() || lebarDada == "0")
                panjangBadanError = (panjangBadan.isEmpty() || panjangBadan == "0")
                if (lebarDadaError || panjangBadanError) return@Button

                kategori = getLategori(lebarDada.toFloat(), lebarDada.toFloat(), katalogBaju == radioOptions[0])
                selectedImage = getimage(panjangBadan.toFloat(), panjangBadan.toFloat(), katalogBaju == radioOptions[0])
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

@Composable
fun GenderOption(label: String, isSelected: Boolean, modifier: Modifier) {
    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
        RadioButton(selected = isSelected, onClick = null)
        Text(text = label, style = MaterialTheme.typography.bodyLarge, modifier = Modifier.padding(start = 8.dp))
    }
}

private fun getLategori(lebarDada: Float, panjangTubuh: Float, lengan: Boolean): Int {
    return if (lengan) {
        when {
            lebarDada >= 58 || panjangTubuh >= 75.5 -> R.string.Ukuran_xxxl
            lebarDada >= 56 || panjangTubuh >= 75 -> R.string.Ukuran_xxl
            lebarDada >= 54.5 || panjangTubuh >= 74 -> R.string.Ukuran_xl
            lebarDada >= 54 || panjangTubuh >= 72 -> R.string.Ukuran_l
            lebarDada >= 50 || panjangTubuh >= 70 -> R.string.Ukuran_m
            lebarDada >= 48 || panjangTubuh >= 68 -> R.string.Ukuran_s
            else -> R.string.Custem_Ukuran
        }
    } else {
        when{
            lebarDada >= 58 || panjangTubuh >= 75.5 -> R.string.Ukuran_xxxl_LP
            lebarDada >= 56 || panjangTubuh >= 75 -> R.string.Ukuran_xxl_LP
            lebarDada >= 54.5 || panjangTubuh >= 74 -> R.string.Ukuran_xl_LP
            lebarDada >= 54 || panjangTubuh >= 72 -> R.string.Ukuran_l_LP
            lebarDada >= 50 || panjangTubuh >= 70 -> R.string.Ukuran_m_LP
            lebarDada >= 48 || panjangTubuh >= 68 -> R.string.Ukuran_s_LP
            else->R.string.Custem_Ukuran
        }

    }
}

private fun getimage(lebarDada: Float, panjangTubuh: Float, isMale: Boolean): Int {
    return if (isMale) {
        when {
            lebarDada >= 58 || panjangTubuh >= 75.5 -> R.drawable.ukuran_xxxl
            lebarDada >= 56 || panjangTubuh >= 75 -> R.drawable.ukuran_xxl
            lebarDada >= 54 || panjangTubuh >= 74 -> R.drawable.ukuran_xl
            lebarDada >= 54.5 || panjangTubuh >= 72 -> R.drawable.ukuran_l
            lebarDada >= 50 || panjangTubuh >= 70 -> R.drawable.ukuran_m
            lebarDada >= 48 || panjangTubuh >= 68 -> R.drawable.ukuran_s
            else -> R.string.Custem_Ukuran
        }
    } else {
        when{
            lebarDada >= 58 || panjangTubuh >= 75.5 -> R.drawable.ukuran_xxxl_lp
            lebarDada >= 56 || panjangTubuh >= 75 -> R.drawable.ukuran_xxl_lp
            lebarDada >= 54 || panjangTubuh >= 74 -> R.drawable.ukuran_xl_lp
            lebarDada >= 54.5 || panjangTubuh >= 72 -> R.drawable.ukuran_l_lp
            lebarDada >= 50 || panjangTubuh >= 70 -> R.drawable.ukuran_m_lp
            lebarDada >= 48 || panjangTubuh >= 68 -> R.drawable.ukuran_s_lp
            else->R.string.Custem_Ukuran
        }
    }
}

@Composable
fun IconPicker(isError: Boolean, unit: String) {
    if (isError) {
        Icon(imageVector = Icons.Filled.Warning, contentDescription = null)

    } else {
        Text(text = unit)
    }
}

@Composable
fun ErorrHit(isError: Boolean) {
    if (isError) {
        Text(text = stringResource(R.string.input_invalid_LP))

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Baju(navController: NavHostController) {
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
     val katalogkategori = data[0]
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
                title = { Text(text = stringResource(id = R.string.app_name)) },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = Color(0xFF001F5B),
                    titleContentColor = Color.White,
                    actionIconContentColor = Color.White
                ),


            )
        }
    ) { innerPadding ->
        ScreenContent(katalog = katalogkategori, Modifier.padding(innerPadding))
    }
}

@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun MainScreenPreview() {
    Tubes_mobproTheme {
        Baju(rememberNavController())
    }
}
