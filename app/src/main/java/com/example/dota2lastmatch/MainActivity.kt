package com.example.dota2lastmatch

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode.Companion.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.dota2lastmatch.models.LastMatch
import com.example.dota2lastmatch.ui.theme.Dota2LastmatchTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Dota2LastmatchTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    color = MaterialTheme.colors.background
                ) {
                    val lastMatch = LastMatch()
                    MyButton(lastMatch);
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    Dota2LastmatchTheme {
    }
}

@Composable
fun MyButton(lastMatch: LastMatch) {

    Column(
        // we are using column to align our
        // imageview to center of the screen.
        modifier = Modifier.fillMaxWidth().fillMaxHeight(),

        // below line is used for specifying
        // vertical arrangement.
        verticalArrangement = Arrangement.Center,

        // below line is used for specifying
        // horizontal arrangement.
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        // below line is use to get
        // the context for our app.
        val context = LocalContext.current

        // below line is use to create a button.
        Button(
            // below line is use to add onclick
            // parameter for our button onclick
            onClick = {
                // when user is clicking the button
                // we are displaying a toast message.
                Toast.makeText(context, "Welcome to Geeks for Geeks", Toast.LENGTH_LONG).show()
                Log.d("heroImage", lastMatch.heroImage)
                Log.d("itemListImage", lastMatch.itemListImage.toString())
                Log.d("heroName", lastMatch.heroName)
                Log.d("ModeGame", lastMatch.modeGame)
                Log.d("timeGame", lastMatch.timeGame)
                Log.d("gameResult", lastMatch.gameResult)
                Log.d("kdaInfo", lastMatch.kdaInfo)
            },
            // in below line we are using modifier
            // which is use to add padding to our button
            modifier = Modifier.padding(all = Dp(10F)),

            // below line is use to set or
            // button as enable or disable.
            enabled = true,

            // below line is use to
            // add border to our button.
            border = BorderStroke(width = 1.dp, brush = SolidColor(androidx.compose.ui.graphics.Color.Blue)),

            // below line is use to add shape for our button.
            shape = MaterialTheme.shapes.medium,
        )
        // below line is use to
        // add text on our button
        {
            Text(text = lastMatch.toString(), color = androidx.compose.ui.graphics.Color.White)
        }
    }
}