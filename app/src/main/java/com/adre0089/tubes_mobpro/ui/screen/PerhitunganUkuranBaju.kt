package com.adre0089.tubes_mobpro.ui.screen

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.adre0089.Nav
import com.adre0089.tubes_mobpro.R
import com.adre0089.tubes_mobpro.navigation.Screen
import com.adre0089.tubes_mobpro.ui.theme.Tubes_mobproTheme

@Composable
fun HomeConten(
    katalogList: List<Nav>,
    navController: NavHostController,
    modifier: Modifier
) {
    Column(
        modifier = modifier
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        katalogList.forEach { nav ->
            Card(
                shape = RoundedCornerShape(12.dp),
                elevation = CardDefaults.cardElevation(6.dp),
                modifier = Modifier
                    .padding(vertical = 6.dp)
                    .fillMaxWidth()
                    .clickable {
                        navController.navigate(nav.arah)
                    }
            ) {
                Box(modifier = Modifier.fillMaxWidth()) {
                    Image(
                        painter = painterResource(id = nav.imagesResId),
                        contentDescription = nav.nama,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(150.dp)
                            .clip(RoundedCornerShape(12.dp))
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.BottomCenter)
                            .background(Color.Black.copy(alpha = 0.8f))
                            .padding(8.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = nav.nama,
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
                .background(Color(0xFF005BBB))
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Mini Projek Mobpro",
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PerhitunganUkuranBaju(navController: NavHostController) {
    val data = listOf(
       Nav(Screen.Home.route, "Pengukur baju kaos",R.drawable.baju),
       Nav(Screen.Kalaog_kemeja.route, "pengukur Kemeja", R.drawable.kemeja),
       Nav(Screen.katalog_Jaket.route, "Penukur Jaket", R.drawable.jajet)
       

    )


    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(id = R.string.app_name)) },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = Color(0xFF001F5B),
                    titleContentColor = Color.White,
                    actionIconContentColor = Color.White


                ),
                actions = {
                    IconButton(onClick = { navController.navigate(Screen.About.route) }) {
                        Icon(imageVector = Icons.Outlined.Info, contentDescription = stringResource(
                            R.string.tentang_aplikasi)
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
            HomeConten(katalogList = data,navController = navController,Modifier.padding(innerPadding))


    }
}



@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun PerhitunganUkuranBajuPriview() {
    Tubes_mobproTheme {
        PerhitunganUkuranBaju(rememberNavController())
    }
}