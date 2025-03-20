package com.sgale.card3d.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.sgale.card3d.R
import com.sgale.compose3d.Compose3DCard

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Scaffold { innerPadding ->
                Compose3DCard(
                    modifier = Modifier.padding(innerPadding),
                    img = R.drawable.img_example
                )
            }
        }
    }
}