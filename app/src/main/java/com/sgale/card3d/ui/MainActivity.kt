package com.sgale.card3d.ui

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
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
import com.sgale.card3d.ui.theme.Compose3DTheme
import com.sgale.compose3d.Compose3DCard

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Compose3DTheme {
                Scaffold { innerPadding ->
                    Box(
                        modifier = Modifier
                            .padding(innerPadding)
                            .fillMaxSize()
                            .background(
                                Compose3DTheme.colors.background
                            )
                    ) {
                        MyCard(
                            modifier = Modifier.align(Alignment.Center).height(500.dp)
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_MASK)
@Composable
fun MyCard(
    modifier: Modifier = Modifier,
    frontImage: Int = R.drawable.img_example_front,
    backImage: Int = R.drawable.img_example_back,
) {
    Compose3DCard(
        modifier = modifier,
        frontImage = frontImage,
        backImage = backImage
    )
}