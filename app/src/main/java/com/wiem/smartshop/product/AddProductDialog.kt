package com.wiem.smartshop.product

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddProductDialog(
    onDismiss: () -> Unit,
    onAdd: (String, String, Int, Double, String) -> Unit
) {
    var name by remember { mutableStateOf("") }
    var selectedBrand by remember { mutableStateOf("Dior") }
    var quantity by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var expandedBrand by remember { mutableStateOf(false) }

    val brands = listOf(
        "Dior",
        "Gucci",
        "Prada",
        "Louis Vuitton",
        "Chanel",
        "Hermès",
        "Versace",
        "Burberry",
        "Fendi",
        "Balenciaga"
    )

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Row(
                verticalAlignment = androidx.compose.ui.Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(
                    Icons.Default.AddShoppingCart,
                    contentDescription = null,
                    tint = Color(0xFF3E2522)
                )
                Text(
                    "Ajouter un produit",
                    color = Color(0xFF3E2522),
                    fontWeight = FontWeight.Bold
                )
            }
        },
        text = {
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .padding(vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Nom du produit
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Nom du produit *") },
                    placeholder = { Text("Ex: Sac Elegance") },
                    modifier = Modifier.fillMaxWidth(),
                    leadingIcon = {
                        Icon(Icons.Default.ShoppingBag, null, tint = Color(0xFF3E2522))
                    },
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFF3E2522),
                        focusedLabelColor = Color(0xFF3E2522)
                    )
                )

                // Marque (Dropdown)
                ExposedDropdownMenuBox(
                    expanded = expandedBrand,
                    onExpandedChange = { expandedBrand = !expandedBrand }
                ) {
                    OutlinedTextField(
                        value = selectedBrand,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Marque *") },
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expandedBrand)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor(),
                        leadingIcon = {
                            Icon(Icons.Default.Label, null, tint = Color(0xFF3E2522))
                        },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color(0xFF3E2522),
                            focusedLabelColor = Color(0xFF3E2522)
                        )
                    )

                    ExposedDropdownMenu(
                        expanded = expandedBrand,
                        onDismissRequest = { expandedBrand = false }
                    ) {
                        brands.forEach { brand ->
                            DropdownMenuItem(
                                text = {
                                    Text(
                                        brand,
                                        fontWeight = if (brand == selectedBrand)
                                            FontWeight.Bold else FontWeight.Normal
                                    )
                                },
                                onClick = {
                                    selectedBrand = brand
                                    expandedBrand = false
                                },
                                leadingIcon = if (brand == selectedBrand) {
                                    { Icon(Icons.Default.Check, null, tint = Color(0xFF3E2522)) }
                                } else null
                            )
                        }
                    }
                }

                // Prix et Quantité (côte à côte)
                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    // Prix
                    OutlinedTextField(
                        value = price,
                        onValueChange = { price = it },
                        label = { Text("Prix (DT) *") },
                        placeholder = { Text("0.00") },
                        modifier = Modifier.weight(1f),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Decimal
                        ),
                        leadingIcon = {
                            Icon(Icons.Default.AttachMoney, null, tint = Color(0xFF3E2522))
                        },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color(0xFF3E2522),
                            focusedLabelColor = Color(0xFF3E2522)
                        )
                    )

                    // Quantité
                    OutlinedTextField(
                        value = quantity,
                        onValueChange = { quantity = it },
                        label = { Text("Quantité *") },
                        placeholder = { Text("0") },
                        modifier = Modifier.weight(1f),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number
                        ),
                        leadingIcon = {
                            Icon(Icons.Default.Numbers, null, tint = Color(0xFF3E2522))
                        },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color(0xFF3E2522),
                            focusedLabelColor = Color(0xFF3E2522)
                        )
                    )
                }

                // Description
                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Description") },
                    placeholder = { Text("Décrivez votre produit...") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp),
                    maxLines = 5,
                    leadingIcon = {
                        Icon(Icons.Default.Description, null, tint = Color(0xFF3E2522))
                    },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFF3E2522),
                        focusedLabelColor = Color(0xFF3E2522)
                    )
                )

                // Note
                Text(
                    "* Champs obligatoires",
                    fontSize = 12.sp,
                    color = Color.Gray,
                    modifier = Modifier.padding(start = 4.dp)
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    onAdd(
                        name,
                        selectedBrand,
                        quantity.toIntOrNull() ?: 0,
                        price.toDoubleOrNull() ?: 0.0,
                        description
                    )
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF3E2522)
                ),
                enabled = name.isNotBlank() &&
                        quantity.isNotBlank() &&
                        price.isNotBlank() &&
                        (quantity.toIntOrNull() ?: 0) >= 0 &&
                        (price.toDoubleOrNull() ?: 0.0) > 0,
                shape = RoundedCornerShape(8.dp)
            ) {
                Icon(Icons.Default.Check, null, modifier = Modifier.size(18.dp))
                Spacer(modifier = Modifier.width(6.dp))
                Text("Ajouter")
            }
        },
        dismissButton = {
            TextButton(
                onClick = onDismiss,
                colors = ButtonDefaults.textButtonColors(
                    contentColor = Color.Gray
                )
            ) {
                Icon(Icons.Default.Close, null, modifier = Modifier.size(18.dp))
                Spacer(modifier = Modifier.width(6.dp))
                Text("Annuler")
            }
        },
        containerColor = Color.White,
        shape = RoundedCornerShape(16.dp)
    )
}