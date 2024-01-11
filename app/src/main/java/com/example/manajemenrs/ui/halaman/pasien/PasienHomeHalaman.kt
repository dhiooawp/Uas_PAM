package com.example.manajemenrs.ui.halaman.pasien

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.manajemenrs.R
import com.example.manajemenrs.data.Pasien
import com.example.manajemenrs.navigasi.DestinasiNavigasi
import com.example.manajemenrs.navigasi.RsTopAppBar
import com.example.manajemenrs.ui.viewmodel.PenyediaViewModel
import com.example.manajemenrs.ui.viewmodel.pasien.PasienHomeViewModel

object DestinasiHomePasien : DestinasiNavigasi {
    override val route = "home_pasien"
    override val titleRes = R.string.app_name
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasienHomeScreen(
    navigateToItemEntry: () -> Unit,
    modifier: Modifier = Modifier,
    onDetailClick: (Int) -> Unit = {},
    onBackClick: () -> Unit,
    viewModel: PasienHomeViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection), topBar = {
            RsTopAppBar(
                title = stringResource(id = DestinasiHomePasien.titleRes),
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = onBackClick
            )
        }, floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToItemEntry,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(8.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Entry Pasien"
                )
            }
        }
    ) { innerPadding ->
        val uiStatePasien by viewModel.homeUiState.collectAsState()
        BodyHome(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            itemPasien = uiStatePasien.listPasien,
            onPasienClick = onDetailClick
        )
    }
}

@Composable
fun BodyHome(
    modifier: Modifier,
    itemPasien: List<Pasien>,
    onPasienClick: (Int) -> Unit = {}
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = modifier) {
        if (itemPasien.isEmpty()) {
            Text(
                text = "Tidak ada Data",
                textAlign = TextAlign.Center, style = MaterialTheme.typography.titleLarge
            )
        } else {
            ListPasien(
                itemPasien = itemPasien,
                modifier = Modifier.padding(horizontal = 8.dp),
                onItemClick = { onPasienClick(it.id) }
            )
        }
    }
}

@Composable
fun ListPasien(
    itemPasien: List<Pasien>,
    modifier: Modifier = Modifier,
    onItemClick: (Pasien) -> Unit
) {
    LazyColumn(modifier = Modifier) {
        items(items = itemPasien, key = { it.id }) { person ->
            DataPasien(
                pasien = person,
                modifier = Modifier
                    .padding(8.dp)
                    .clickable { onItemClick(person) }
            )
        }
    }
}

@Composable
fun DataPasien(
    pasien: Pasien, modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier, elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(18.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = pasien.nama,
                    style = MaterialTheme.typography.titleLarge,
                )
                Spacer(Modifier.weight(1f))
                Icon(
                    imageVector = Icons.Default.Phone,
                    contentDescription = null
                )
                Text(
                    text = pasien.nohp,
                    style = MaterialTheme.typography.titleMedium
                )
            }
            Text(
                text = pasien.alamat,
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}