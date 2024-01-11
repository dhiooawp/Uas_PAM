package com.example.manajemenrs.ui.halaman.rekammedis

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.manajemenrs.R
import com.example.manajemenrs.navigasi.DestinasiNavigasi
import com.example.manajemenrs.navigasi.RsTopAppBar
import com.example.manajemenrs.ui.viewmodel.PenyediaViewModel
import com.example.manajemenrs.ui.viewmodel.rekammedis.DetailRekamMedis
import com.example.manajemenrs.ui.viewmodel.rekammedis.RekamMedisCreateViewModel
import com.example.manajemenrs.ui.viewmodel.rekammedis.UIStateRekamMedis
import kotlinx.coroutines.launch

object DestinasiEntryRekamMedis : DestinasiNavigasi {
    override val route = "rekam_entry"
    override val titleRes = R.string.entry_rekamMedis
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntryRekamMedisScreen(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: RekamMedisCreateViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            RsTopAppBar(
                title = stringResource(DestinasiEntryRekamMedis.titleRes),
                canNavigateBack = true,
                scrollBehavior = scrollBehavior
            )
        }
    ) { innerPadding ->
        EntryRekamMedisBody(
            uiStateRekamMedis = viewModel.UiStateRekamMedis,
            onRekamMedisValueChange = viewModel::updateUiState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.saveRekamMedis()
                    navigateBack()
                }
            },
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .fillMaxWidth()
        )
    }
}

@Composable
fun EntryRekamMedisBody(
    uiStateRekamMedis: UIStateRekamMedis,
    onRekamMedisValueChange: (DetailRekamMedis) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier = modifier.padding(16.dp)
    ) {
        FormInputRekamMedis(
            detailRekamMedis = uiStateRekamMedis.detailRekamMedis,
            onValueChange = onRekamMedisValueChange,
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = onSaveClick,
            enabled = uiStateRekamMedis.isEntryValid,
            shape = MaterialTheme.shapes.small,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Submit")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormInputRekamMedis(
    detailRekamMedis: DetailRekamMedis,
    modifier: Modifier = Modifier,
    onValueChange: (DetailRekamMedis) -> Unit = {},
    enabled: Boolean = true
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        OutlinedTextField(
            value = detailRekamMedis.nama,
            onValueChange = { onValueChange(detailRekamMedis.copy(nama = it)) },
            label = { Text(text = "Masukan Nama Rekam Medis") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = detailRekamMedis.riwayat,
            onValueChange = { onValueChange(detailRekamMedis.copy(riwayat = it)) },
            label = { Text(text = "Masukan Riwayat Penyakit") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = detailRekamMedis.resep,
            onValueChange = { onValueChange(detailRekamMedis.copy(resep = it)) },
            label = { Text(text = "Masukan Resep Obat") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        if (enabled) {
            Text(
                text = "Required field*",
                modifier = Modifier.padding(start = 16.dp)
            )
        }
        Divider(
            thickness = 8.dp,
            modifier = Modifier.padding(
                bottom = 16.dp
            )
        )
    }
}