package com.example.manajemenrs.navigasi

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.manajemenrs.ui.halaman.HalamanHome
import com.example.manajemenrs.ui.halaman.Home
import com.example.manajemenrs.ui.halaman.HomeScreenDisplay
import com.example.manajemenrs.ui.halaman.pasien.DestinasiEntryPasien
import com.example.manajemenrs.ui.halaman.pasien.DestinasiHomePasien
import com.example.manajemenrs.ui.halaman.pasien.DetailsDestinationPasien
import com.example.manajemenrs.ui.halaman.pasien.EntryPasienScreen
import com.example.manajemenrs.ui.halaman.pasien.PasienDetailsScreen
import com.example.manajemenrs.ui.halaman.pasien.PasienEditDestination
import com.example.manajemenrs.ui.halaman.pasien.PasienEditScreen
import com.example.manajemenrs.ui.halaman.pasien.PasienHomeScreen
import com.example.manajemenrs.ui.halaman.rekammedis.DestinasiEntryRekamMedis
import com.example.manajemenrs.ui.halaman.rekammedis.DestinasiHomeRekamMedis
import com.example.manajemenrs.ui.halaman.rekammedis.DetailsDestinationRekamMedis
import com.example.manajemenrs.ui.halaman.rekammedis.EntryRekamMedisScreen
import com.example.manajemenrs.ui.halaman.rekammedis.RekamMedisDetailsScreen
import com.example.manajemenrs.ui.halaman.rekammedis.RekamMedisEditDestination
import com.example.manajemenrs.ui.halaman.rekammedis.RekamMedisEditScreen
import com.example.manajemenrs.ui.halaman.rekammedis.RekamMedisHomeScreen

@Composable
fun RSApp(navController: NavHostController = rememberNavController()) {
    HostNavigasi(navController = navController)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RsTopAppBar(
    title: String,
    canNavigateBack: Boolean,
    modifier: Modifier = Modifier,
    scrollBehavior: TopAppBarScrollBehavior? = null,
    navigateUp: () -> Unit = {}
) {
    CenterAlignedTopAppBar(title = { Text(title) },
        modifier = modifier,
        scrollBehavior = scrollBehavior,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Kembali"
                    )
                }
            }
        }
    )
}

@Composable
fun HostNavigasi(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Home.route,
        modifier = Modifier
    ) {
        composable(Home.route) {
            HomeScreenDisplay(
                modifier = modifier,
                navigateToPasien = { navController.navigate(DestinasiHomePasien.route) },
                navigateToMedis = { navController.navigate(DestinasiHomeRekamMedis.route) }
            )
        }
        composable(DestinasiHomePasien.route) {
            PasienHomeScreen(
                navigateToItemEntry = { navController.navigate(DestinasiEntryPasien.route) },
                onDetailClick = { itemId -> navController.navigate("${DetailsDestinationPasien.route}/$itemId") },
                onBackClick = { navController.popBackStack() })
        }
        composable(DestinasiHomeRekamMedis.route) {
            RekamMedisHomeScreen(
                navigateToMedisEntry = { navController.navigate(DestinasiEntryRekamMedis.route) },
                onDetailClick = { itemId -> navController.navigate("${DetailsDestinationRekamMedis.route}/$itemId") },
                onBackClick = { navController.popBackStack() }
            )
        }
        composable(DestinasiEntryRekamMedis.route) {
            EntryRekamMedisScreen(navigateBack = { navController.popBackStack() })
        }
        composable(DestinasiEntryPasien.route) {
            EntryPasienScreen(navigateBack = { navController.popBackStack() })
        }
        composable(
            DetailsDestinationRekamMedis.routeWithArgs,
            arguments = listOf(navArgument(DetailsDestinationRekamMedis.rekamMedisIdArg) {
                type = NavType.IntType
            })
        ) { backStackEntry ->
            val itemId =
                backStackEntry.arguments?.getInt(DetailsDestinationRekamMedis.rekamMedisIdArg)
            itemId?.let {
                RekamMedisDetailsScreen(
                    navigateBack = { navController.popBackStack() },
                    navigateToEditItem = { navController.navigate("${RekamMedisEditDestination.route}/$it") }
                )
            }
        }
        composable(
            DetailsDestinationPasien.routeWithArgs,
            arguments = listOf(navArgument(DetailsDestinationPasien.pasienIdArg) {
                type = NavType.IntType
            })
        ) { backStackEntry ->
            val itemId = backStackEntry.arguments?.getInt(DetailsDestinationPasien.pasienIdArg)
            itemId?.let {
                PasienDetailsScreen(
                    navigateBack = { navController.popBackStack() },
                    navigateToEditItem = { navController.navigate("${PasienEditDestination.route}/$it") }
                )
            }
        }
        composable(
            RekamMedisEditDestination.routeWithArgs,
            arguments = listOf(navArgument(RekamMedisEditDestination.itemIdArg) {
                type = NavType.IntType
            })
        ) {
            RekamMedisEditScreen(
                navigateBack = { navController.popBackStack() },
                onNavigateUp = { navController.navigateUp() })
        }
        composable(
            PasienEditDestination.routeWithArgs,
            arguments = listOf(navArgument(PasienEditDestination.itemIdArg) {
                type = NavType.IntType
            })
        ) {
            PasienEditScreen(
                navigateBack = { navController.popBackStack() },
                onNavigateUp = { navController.navigateUp() })
        }
    }
}