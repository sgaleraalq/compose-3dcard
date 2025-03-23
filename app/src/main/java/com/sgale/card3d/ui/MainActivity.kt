package com.sgale.card3d.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sgale.card3d.R
import com.sgale.compose3d.Compose3DCard

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Scaffold { innerPadding ->
                Box(
                    modifier = Modifier.fillMaxSize().padding(innerPadding)
                ) {
                    MyCard(
                        modifier = Modifier.align(Alignment.Center).height(500.dp),
                        img = R.drawable.img_example
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun MyCard(
    modifier: Modifier = Modifier,
    img: Int = R.drawable.img_example
){
    Compose3DCard(
        modifier = modifier,
        img = img
    )
}