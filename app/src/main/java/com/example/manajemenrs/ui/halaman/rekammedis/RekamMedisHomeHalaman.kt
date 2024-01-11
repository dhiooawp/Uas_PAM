package com.example.manajemenrs.ui.halaman.rekammedis

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
import androidx.compose.material.icons.filled.Info
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
import com.example.manajemenrs.data.RekamMedis
import com.example.manajemenrs.navigasi.DestinasiNavigasi
import com.example.manajemenrs.navigasi.RsTopAppBar
import com.example.manajemenrs.ui.viewmodel.PenyediaViewModel
import com.example.manajemenrs.ui.viewmodel.rekammedis.RekamMedisHomeViewModel

object DestinasiHomeRekamMedis : DestinasiNavigasi {
    override val route = "home_rekammedis"
    override val titleRes = R.string.app_name
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RekamMedisHomeScreen(
    navigateToMedisEntry: () -> Unit,
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
    onDetailClick: (Int) -> Unit = {},
    viewModel: RekamMedisHomeViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection), topBar = {
            RsTopAppBar(
                title = stringResource(id = DestinasiHomeRekamMedis.titleRes),
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = onBackClick
            )
        }, floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToMedisEntry,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(8.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Entry RekamMedis"
                )
            }
        }
    ) { innerPadding ->
        val uiStateRekamMedis by viewModel.homeUiState.collectAsState()
        RekamMedisBodyHome(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            itemRekamMedis = uiStateRekamMedis.listRekamMedis,
            onRekamMedisClick = onDetailClick
        )
    }
}

@Composable
fun RekamMedisBodyHome(
    modifier: Modifier,
    itemRekamMedis: List<RekamMedis>,
    onRekamMedisClick: (Int) -> Unit = {}
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = modifier) {
        if (itemRekamMedis.isEmpty()) {
            Text(
                text = "Tidak ada Data",
                textAlign = TextAlign.Center, style = MaterialTheme.typography.titleLarge
            )
        } else {
            ListRekamMedis(
                itemRekamMedis = itemRekamMedis,
                modifier = Modifier.padding(horizontal = 8.dp),
                onItemClick = { onRekamMedisClick(it.id) }
            )
        }
    }
}

@Composable
fun ListRekamMedis(
    itemRekamMedis: List<RekamMedis>,
    modifier: Modifier = Modifier,
    onItemClick: (RekamMedis) -> Unit
) {
    LazyColumn(modifier = Modifier) {
        items(items = itemRekamMedis, key = { it.id }) { person ->
            DataRekamMedis(
                RekamMedis = person,
                modifier = Modifier
                    .padding(8.dp)
                    .clickable { onItemClick(person) }
            )
        }
    }
}

@Composable
fun DataRekamMedis(
    RekamMedis: RekamMedis, modifier: Modifier = Modifier
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
                    text = RekamMedis.nama,
                    style = MaterialTheme.typography.titleLarge,
                )
                Spacer(Modifier.weight(1f))
                Icon(
                    imageVector = Icons.Default.Info,
                    contentDescription = null
                )
                Text(
                    text = RekamMedis.riwayat,
                    style = MaterialTheme.typography.titleMedium
                )
            }
            Text(
                text = RekamMedis.resep,
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}