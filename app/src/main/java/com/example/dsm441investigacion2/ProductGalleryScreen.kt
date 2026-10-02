package com.example.dsm441investigacion2

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductGalleryScreen() {
    var selectedCategory by remember { mutableStateOf("Todos") }
    var searchQuery by remember { mutableStateOf("") }

    val allProducts = getProductList()

    val filteredProducts = allProducts.filter { product ->
        (selectedCategory == "Todos" || product.category == selectedCategory) &&
        product.name.contains(searchQuery, ignoreCase = true)
    }

    val categories = listOf("Todos") + allProducts.map { it.category }.distinct()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFAFAFA))
    ) {
        // Header
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            color = Color(0xFF0066CC),
            shadowElevation = 4.dp
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    text = "Galería de Productos",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    modifier = Modifier.paddingFromBaseline(top = 32.dp)
                )
                Text(
                    text = "Jetpack Compose",
                    fontSize = 14.sp,
                    color = Color.White.copy(alpha = 0.8f),
                    modifier = Modifier.paddingFromBaseline(top = 8.dp)
                )
            }
        }

        // Search Bar
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .padding(bottom = 12.dp),
            placeholder = { Text("Buscar productos...") },
            leadingIcon = {
                Icon(Icons.Filled.Search, contentDescription = null)
            },
            singleLine = true,
            shape = androidx.compose.foundation.shape.RoundedCornerShape(8.dp)
        )

        // Category Filter
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .padding(bottom = 12.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(categories) { category ->
                FilterChip(
                    selected = selectedCategory == category,
                    onClick = { selectedCategory = category },
                    label = { Text(category) },
                    modifier = Modifier.height(40.dp)
                )
            }
        }

        // Product List
        if (filteredProducts.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "No hay productos disponibles",
                    fontSize = 18.sp,
                    color = Color.Gray
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 8.dp),
                contentPadding = PaddingValues(vertical = 8.dp)
            ) {
                items(filteredProducts) { product ->
                    ProductCard(product = product)
                }
            }
        }
    }
}

fun getProductList(): List<Product> = listOf(
    Product(1, "Laptop Pro", 1299.99, android.R.drawable.ic_dialog_info, 4.8f, "Laptop de alto rendimiento", "Electrónica"),
    Product(2, "Auriculares Wireless", 199.99, android.R.drawable.ic_dialog_info, 4.6f, "Auriculares inalámbricos de calidad", "Audio"),
    Product(3, "Smartwatch", 399.99, android.R.drawable.ic_dialog_info, 4.5f, "Reloj inteligente con múltiples funciones", "Wearables"),
    Product(4, "Cámara 4K", 799.99, android.R.drawable.ic_dialog_info, 4.7f, "Cámara de video en 4K", "Fotografía"),
    Product(5, "Teléfono Inteligente", 899.99, android.R.drawable.ic_dialog_info, 4.9f, "Último modelo de smartphone", "Electrónica"),
    Product(6, "Tablet 10\"", 599.99, android.R.drawable.ic_dialog_info, 4.4f, "Tablet grande para productividad", "Electrónica"),
    Product(7, "Altavoz Portátil", 149.99, android.R.drawable.ic_dialog_info, 4.3f, "Altavoz con batería larga", "Audio"),
    Product(8, "Cargador Rápido", 49.99, android.R.drawable.ic_dialog_info, 4.2f, "Cargador rápido 65W", "Accesorios"),
    Product(9, "Funda Premium", 29.99, android.R.drawable.ic_dialog_info, 4.5f, "Funda protectora para smartphone", "Accesorios"),
    Product(10, "Batería Externa", 79.99, android.R.drawable.ic_dialog_info, 4.6f, "Power bank de 20000mAh", "Accesorios"),
)
