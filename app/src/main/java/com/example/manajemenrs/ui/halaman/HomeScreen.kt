package com.example.manajemenrs.ui.halaman

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.manajemenrs.R
import com.example.manajemenrs.navigasi.DestinasiNavigasi
import com.example.manajemenrs.navigasi.RsTopAppBar

object Home : DestinasiNavigasi {
    override val route = "home"
    override val titleRes = R.string.app_name
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenDisplay(
    modifier: Modifier,
    navigateToPasien: () -> Unit,
    navigateToMedis: () -> Unit
) {
    Scaffold(
        modifier = modifier, topBar = {
            RsTopAppBar(
                title = stringResource(id = Home.titleRes),
                canNavigateBack = false
            )
        }
    ) { innerPadding ->
        HalamanHome(
            modifier = Modifier.padding(innerPadding),
            onPasienClicked = navigateToPasien,
            onMedisClicked = navigateToMedis
        )
    }
}

@Composable
fun HalamanHome(
    modifier: Modifier,
    onPasienClicked: () -> Unit,
    onMedisClicked: () -> Unit,
) {
    Column(
        modifier = Modifier,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = "Selamat Datang!")
        Text(text = "Aplikasi PAM Manajemen Rumah Sakit")
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .weight(1f, false),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.Bottom
        ) {
            Button(
                modifier = Modifier.weight(1f),
                onClick = onPasienClicked
            ) {
                Text(text = "Data Pasien")
            }
            Spacer(modifier = Modifier.padding(16.dp))
            Button(
                modifier = Modifier.weight(1f),
                onClick = onMedisClicked
            ) {
                Text(text = "Data Medis")
            }
        }
    }
}