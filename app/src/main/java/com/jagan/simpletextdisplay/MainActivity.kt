package com.jagan.simpletextdisplay

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.*
import com.jagan.simpletextdisplay.memeDataPackage.Meme
import com.jagan.simpletextdisplay.memeDataPackage.MemeData
import com.jagan.simpletextdisplay.ui.theme.SimpleTextDisplayTheme
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class MainActivity : ComponentActivity() {
    @SuppressLint("MutableCollectionMutableState")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            InitialProcess()
        }
    }
}

@Composable
fun MakeACard(meme: Meme) {

    println("updated 1 : $meme")
    val context = LocalContext.current
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Card(
            elevation = 4.dp,
            shape = RoundedCornerShape(CornerSize(10.dp)),
            modifier = Modifier
                .padding(10.dp),

            backgroundColor = Color(0xFFFF6678),
        ) {
            Image(
                painter = rememberAsyncImagePainter(meme.url),
                contentDescription = meme.name,
                modifier = Modifier
                    .padding(5.dp)
                    .height(500.dp)
                    .width(330.dp)
                    .background(Color(0xFFFF6678)),
                contentScale = ContentScale.Fit
            )
        }

        Text(
            text = "_________________________________________________________",
            fontWeight = FontWeight.ExtraBold,
            fontSize = 10.sp
        )
        Text(
            text = meme.name,
            fontWeight = FontWeight.Bold,
            fontSize = 19.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .width(200.dp)
                .clickable {
                    Toast
                        .makeText(context, "you clicked on ${meme.name}", Toast.LENGTH_SHORT)
                        .show()
                }
        )
        Text(
            text = "_________________________________________________________",
            fontWeight = FontWeight.ExtraBold,
            fontSize = 10.sp
        )
        Spacer(modifier = Modifier.height(40.dp))
    }
}

@Composable
fun InitialProcess() {
    val scaffoldState = rememberScaffoldState(rememberDrawerState(DrawerValue.Closed))
    val context = LocalContext.current

    /* Retrofit */
    val meme = MemeDataInstance.getMemeData.getMemeInfo()


    val memeState = listOf<Meme>()

    val data = remember {
        mutableStateOf(memeState)
    }


    meme.enqueue(object : Callback<MemeData> {
        override fun onResponse(call: Call<MemeData>, response: Response<MemeData>) {
            Toast.makeText(context, "Welcome to karigallan show", Toast.LENGTH_SHORT).show()

            val memeData = response.body()
            if (memeData != null) {
                println("Here is Data ${memeData.data.memes}")
                data.value = memeData.data.memes
                //data.value = memeData.data.memes
            }
        }

        override fun onFailure(call: Call<MemeData>, t: Throwable) {
            println("YOUR ERROR : $t ")
            Toast.makeText(context, t.toString(), Toast.LENGTH_SHORT).show()
        }

    })





    /* SCREEN DESIGN */
    SimpleTextDisplayTheme {
        Scaffold(
            scaffoldState = scaffoldState,
            topBar = {
                TopAppBar(
                    title = {
                        Text("Kaipulla Meme's")
                    },
                    backgroundColor = Color(0xFFFF6678)
                )
            },
        ) {
            LazyRow {
                itemsIndexed(data.value) { _, d ->
                    println("Updated Data : $d")
                    MakeACard(meme = d)
                }
            }
        }

    }

}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    InitialProcess()
}