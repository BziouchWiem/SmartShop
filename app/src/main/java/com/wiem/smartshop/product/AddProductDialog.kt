package com.wiem.smartshop.product

import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.foundation.layout.*
import androidx.compose.ui.unit.dp

@Composable
fun AddProductDialog(
    onDismiss: () -> Unit,
    onAdd: (String, Int, Double) -> Unit
) {
    var name by remember { mutableStateOf("") }
    var quantity by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Ajouter un produit") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(name, { name = it }, label = { Text("Nom") })
                OutlinedTextField(quantity, { quantity = it }, label = { Text("Quantité") })
                OutlinedTextField(price, { price = it }, label = { Text("Prix") })
            }
        },
        confirmButton = {
            Button(onClick = {
                onAdd(
                    name,
                    quantity.toIntOrNull() ?: 0,
                    price.toDoubleOrNull() ?: 0.0
                )
            }) {
                Text("Ajouter")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Annuler")
            }
        }
    )
}
