package com.pes.upcomingmovies

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pes.upcomingmovies.ui.theme.UpcomingMoviesTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            UpcomingMoviesTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {

    var movieList by remember {
        mutableStateOf(listOf<Movie>())
    }

    LaunchedEffect(Unit) {
        // send request to get upcoming movies - first composition
       val response = TMDBInterface.getInstance()
            .getUpcomingMovies("456b638afee7c7179adac748bc9d5bc7")

       movieList = response.results
    }

    if(movieList.isEmpty()) {
        Row(modifier = modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center) {
            CircularProgressIndicator()
            Text(
                text = "Loading..please wait",
                modifier = modifier
            )

        }
    }else {

        LazyColumn(modifier = modifier) {
            item {
                Text("Upcoming Movies",
                    fontSize = 30.sp,
                    modifier = Modifier.padding(20.dp))
            }
           items(items = movieList) { movie ->
               OutlinedCard(modifier = Modifier
                   .fillMaxWidth()
                   .padding(2.dp),
                   colors = CardColors(
                       contentColor = Color.Blue,
                       containerColor = Color.Cyan,
                       disabledContainerColor = Color.LightGray,
                       disabledContentColor = Color.LightGray
                   )
                  ) {
                   Column(modifier = Modifier.padding(8.dp)
                       ) {
                       Text(movie.title,
                           fontSize = 24.sp, color = Color.Black)
                       Text("Rating: ${movie.vote_average}")
                       Text("Releasing on: ${movie.release_date}")
                       HorizontalDivider()
                       Text(movie.overview,
                           style = TextStyle(fontStyle = FontStyle.Italic))
                   }
               }

           }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    UpcomingMoviesTheme {
        Greeting("Android")
    }
}