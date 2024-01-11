package com.example.manajemenrs.ui.halaman.pasien

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.manajemenrs.R
import com.example.manajemenrs.data.Pasien
import com.example.manajemenrs.navigasi.DestinasiNavigasi
import com.example.manajemenrs.navigasi.RsTopAppBar
import com.example.manajemenrs.ui.viewmodel.PenyediaViewModel
import com.example.manajemenrs.ui.viewmodel.pasien.PasienDetailViewModel
import com.example.manajemenrs.ui.viewmodel.pasien.PasienDetaislUiState
import com.example.manajemenrs.ui.viewmodel.pasien.toPasien
import kotlinx.coroutines.launch

object DetailsDestinationPasien : DestinasiNavigasi {
    override val route = "item_details"
    override val titleRes = R.string.detail_pasien
    const val pasienIdArg = "itemId"
    val routeWithArgs = "$route/{$pasienIdArg}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasienDetailsScreen(
    navigateToEditItem: (Int) -> Unit,
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: PasienDetailViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val uiState = viewModel.uiState.collectAsState()
    val coroutineScope = rememberCoroutineScope()
    Scaffold(
        topBar = {
            RsTopAppBar(
                title = stringResource(DetailsDestinationPasien.titleRes),
                canNavigateBack = true,
                navigateUp = navigateBack
            )
        }, floatingActionButton = {
            FloatingActionButton(
                onClick = { navigateToEditItem(uiState.value.detailPasien.id) },
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(18.dp)

            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = stringResource(R.string.edit_pasien),
                )
            }
        }, modifier = modifier
    ) { innerPadding ->
        PasienDetailsBody(
            pasienDetaislUiState = uiState.value,
            onDelete = {
                // Note: If the user rotates the screen very fast, the operation may get cancelled
                // and the item may not be deleted from the Database. This is because when config
                // change occurs, the Activity will be recreated and the rememberCoroutineScope will
                // be cancelled - since the scope is bound to composition.
                coroutineScope.launch {
                    viewModel.deleteItem()
                    navigateBack()
                }
            },
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState()),

            )
    }
}

@Composable
private fun PasienDetailsBody(
    pasienDetaislUiState: PasienDetaislUiState,
    onDelete: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        var deleteConfirmationRequired by rememberSaveable { mutableStateOf(false) }
        ItemDetails(
            pasien = pasienDetaislUiState.detailPasien.toPasien(),
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedButton(
            onClick = { deleteConfirmationRequired = true },
            shape = MaterialTheme.shapes.small,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Hapus")
        }
        if (deleteConfirmationRequired) {
            DeleteConfirmDialog(
                onDeleteConfirm = {
                    deleteConfirmationRequired = false
                    onDelete()
                },
                onDeleteCancel = { deleteConfirmationRequired = false },
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}

@Composable
fun ItemDetails(
    pasien: Pasien, modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier, colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            PasienDetailRow(
                labelResID = R.string.nama_pasien,
                pasienDetail = pasien.nama,
                modifier = Modifier.padding(
                    horizontal = 16.dp
                )
            )

            PasienDetailRow(
                labelResID = R.string.alamat_pasien,
                pasienDetail = pasien.alamat,
                modifier = Modifier.padding(
                    horizontal = 16.dp
                )
            )
            PasienDetailRow(
                labelResID = R.string.nohp_pasien,
                pasienDetail = pasien.nohp,
                modifier = Modifier.padding(
                    horizontal = 16.dp
                )
            )
        }

    }
}

@Composable
private fun PasienDetailRow(
    @StringRes labelResID: Int, pasienDetail: String, modifier: Modifier = Modifier
) {
    Row(modifier = modifier) {
        Text(text = stringResource(labelResID))
        Spacer(modifier = Modifier.weight(1f))
        Text(text = pasienDetail, fontWeight = FontWeight.Bold)
    }
}

@Composable
private fun DeleteConfirmDialog(
    onDeleteConfirm: () -> Unit,
    onDeleteCancel: () -> Unit,
    modifier: Modifier = Modifier
) {
    AlertDialog(
        onDismissRequest = { /*TODO*/ },
        title = { Text(text = "Warning") },
        text = { Text(text = "Apakah kamu yakin ingin menghapus data ini?") },
        modifier = modifier,
        dismissButton = {
            TextButton(onClick = onDeleteCancel) {
                Text(text = "Tidak")
            }
        },
        confirmButton = {
            TextButton(onClick = onDeleteConfirm) {
                Text(text = "Iya")
            }
        }
    )
}