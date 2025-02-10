package com.example.moneytransferapp.ui.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun IntroSlider(navController: NavHostController,
                showLogin : () -> Unit) {
    val pagerState = rememberPagerState(initialPage = 0, pageCount = { 3 })
    val coroutineScope = rememberCoroutineScope() // Create a coroutine scope for paging

    Box(modifier = Modifier.fillMaxSize().background(Color(0xFF4A90E2))) { // Set background color

        HorizontalPager(state = pagerState, modifier = Modifier.fillMaxSize()) { page ->
            when (page) {
                0 -> {
                    // Slide 1 content
                    SlideContent(
                        title = "Welcome to the Money Transfer App!",
                        description = "Manage and transfer money effortlessly with our secure platform."
                    )
                }
                1 -> {
                    // Slide 2 content
                    SlideContent(
                        title = "Transfer money securely and easily.",
                        description = "Quickly transfer funds with just a few taps."
                    )
                }
                2 -> {
                    // Slide 3 content
                    SlideContent(
                        title = "Track your transactions and account activity.",
                        description = "Stay on top of all your financial movements."
                    )
                }
            }
        }

        // Bottom navigation area with Dots and Next button in the same row
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Dots to show the current slide
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.weight(1f)
            ) {
                repeat(3) { index ->
                    DotIndicator(isSelected = pagerState.currentPage == index)
                }
            }

            // Next button at the end
            Button(
                onClick = {
                    // Navigate to the login screen after the last slide
                    if (pagerState.currentPage == 2) {
                        showLogin()
                    } else {
                        // Scroll to the next page using coroutine
                        if (pagerState.currentPage < 2) {
                            coroutineScope.launch {
                                pagerState.animateScrollToPage(pagerState.currentPage + 1)
                            }
                        }
                    }
                },
                shape = RoundedCornerShape(50),
                modifier = Modifier
                    .wrapContentWidth()
                    .height(40.dp)
                    .padding(start = 8.dp)
            ) {
                Text("Next", color = Color(0xFF4A90E9), fontSize = 14.sp) // Small text size
            }
        }
    }
}

@Composable
fun DotIndicator(isSelected: Boolean) {
    val dotColor = if (isSelected) Color.White else Color(0x80FFFFFF) // Inactive dots are slightly transparent
    Box(
        modifier = Modifier
            .size(10.dp)
            .clip(CircleShape)
            .background(dotColor)
            .padding(4.dp)
    )
}

@Composable
fun SlideContent(title: String, description: String) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.headlineSmall,
            color = Color.White,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        Text(
            text = description,
            style = MaterialTheme.typography.bodyMedium,
            color = Color.White,
            textAlign = TextAlign.Center
        )
    }
}

