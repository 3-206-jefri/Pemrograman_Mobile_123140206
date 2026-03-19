package com.example.tugas3

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun InfoItem(icon: ImageVector, label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Gunakan Color.Gray dari library graphics yang benar
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = Color.Gray
        )

        Spacer(modifier = Modifier.width(16.dp))

        Column {
            // Di Material 3, 'caption' diganti jadi 'labelMedium' atau 'bodySmall'
            Text(
                text = label,
                style = MaterialTheme.typography.labelMedium,
                color = Color.Gray
            )
            // Di Material 3, 'body1' diganti jadi 'bodyLarge' atau 'bodyMedium'
            Text(
                text = value,
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}