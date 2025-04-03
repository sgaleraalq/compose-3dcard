package com.sgale.card3d.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sgale.card3d.R
import com.sgale.card3d.ui.theme.Compose3DTheme
import com.sgale.compose3d.Compose3DCard
import com.sgale.compose3d.ShimmerDirection

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
                        MyCard()
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun MyCard(
    frontImage: Int = R.drawable.img_example_front,
    backImage: Int = R.drawable.img_example_back,
) {
//    Box(
//        modifier = Modifier.fillMaxSize()
//    ){
//        Compose3DCard(
//            modifier = Modifier.align(Alignment.Center).height(500.dp).width(200.dp),
//            frontImage = frontImage,
//            backImage = backImage,
////            contentScale = ContentScale.Crop
//        )
//    }


    Column(
        modifier = Modifier.fillMaxSize().padding(12.dp)
    ) {
        Compose3DCard(
            modifier = Modifier.height(500.dp),
            frontImage = frontImage,
            backImage = backImage,
            contentScale = ContentScale.Fit
        )

//        Spacer(Modifier.height(12.dp))
//
//        Compose3DCard(
//            modifier = Modifier.width(500.dp).padding(12.dp),
//            frontImage = frontImage,
//            backImage = backImage,
//            contentScale = ContentScale.Crop
//        )

        HorizontalDivider(modifier = Modifier.padding(8.dp), thickness = 1.dp)

        LazyVerticalGrid(
            modifier = Modifier.fillMaxWidth(),
            columns = GridCells.Fixed(2),
        ) {
            val shimmerDirections = ShimmerDirection.entries
            items(shimmerDirections.size) { direction ->
                Column {
                    Compose3DCard(
                        frontImage = frontImage,
                        backImage = backImage,
                        shimmerDirection = shimmerDirections[direction]
                    )
                    Text(
                        text = shimmerDirections[direction].name
                            .lowercase()
                            .replace("_", " ")
                            .split(" ")
                            .joinToString(" ")
                            { it.replaceFirstChar { c -> c.uppercase() } },
                        modifier = Modifier
                            .fillMaxWidth(),
                        color = Color.White
                    )
                }
            }
        }
    }
}