package com.example.manajemenrs.ui.halaman.pasien

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
import com.example.manajemenrs.ui.viewmodel.pasien.DetailPasien
import com.example.manajemenrs.ui.viewmodel.pasien.PasienCreateViewModel
import com.example.manajemenrs.ui.viewmodel.pasien.UIStatePasien
import kotlinx.coroutines.launch

object DestinasiEntryPasien : DestinasiNavigasi {
    override val route = "item_entry"
    override val titleRes = R.string.entry_pasien
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntryPasienScreen(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: PasienCreateViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            RsTopAppBar(
                title = stringResource(DestinasiEntryPasien.titleRes),
                canNavigateBack = true,
                scrollBehavior = scrollBehavior
            )
        }
    ) { innerPadding ->
        EntryPasienBody(
            uiStatePasien = viewModel.UiStatePasien,
            onPasienValueChange = viewModel::updateUiState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.savePasien()
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
fun EntryPasienBody(
    uiStatePasien: UIStatePasien,
    onPasienValueChange: (DetailPasien) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier = modifier.padding(16.dp)
    ) {
        FormInputPasien(
            detailPasien = uiStatePasien.detailPasien,
            onValueChange = onPasienValueChange,
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = onSaveClick,
            enabled = uiStatePasien.isEntryValid,
            shape = MaterialTheme.shapes.small,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Submit")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormInputPasien(
    detailPasien: DetailPasien,
    modifier: Modifier = Modifier,
    onValueChange: (DetailPasien) -> Unit = {},
    enabled: Boolean = true
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        OutlinedTextField(
            value = detailPasien.nama,
            onValueChange = { onValueChange(detailPasien.copy(nama = it)) },
            label = { Text(text = "Masukan Nama Pasien")},
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true)
        OutlinedTextField(
            value = detailPasien.alamat,
            onValueChange = { onValueChange(detailPasien.copy(alamat = it)) },
            label = { Text(text = "Masukan Alamat") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = detailPasien.nohp,
            onValueChange = { onValueChange(detailPasien.copy(nohp = it)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            label = { Text(text = "Masukan No Hp") },
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