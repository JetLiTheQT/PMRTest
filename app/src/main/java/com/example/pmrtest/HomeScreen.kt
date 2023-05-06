package com.example.pmrtest


import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController


@Composable
// Dummy route for main screen
fun HomeScreen(navController: NavHostController) { }

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val sharedViewModel = viewModel<SharedViewModel>()
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .padding(0.dp),
        topBar = { TopNavBar(navController = navController, sharedViewModel = sharedViewModel) },
        content = { innerPadding ->
            Box(modifier = Modifier.padding(innerPadding)) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        WelcomeText()
                        PatientInfoCard()
                        MedicationSchedule()
                        HealthStatisticsCard()
                    }
                }
                AppNavigator(navController)
            }
        }
    )
}

@Composable
fun HealthStatisticsCard() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        backgroundColor = MaterialTheme.colors.surface
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Health Statistics",
                style = MaterialTheme.typography.h6,
                color = MaterialTheme.colors.primary
            )
            Spacer(modifier = Modifier.height(8.dp))
            SimpleBarChart(
                data = listOf(75f, 80f, 120f, 100f, 90f),
                maxValue = 150f,
                barColor = MaterialTheme.colors.primary
            )
        }
    }
}

@Composable
fun PatientInfoCard() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        backgroundColor = MaterialTheme.colors.surface
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Patient Information",
                style = MaterialTheme.typography.h6,
                color = MaterialTheme.colors.primary
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text("Name: John Doe", color = MaterialTheme.colors.onSurface)
            Text("Age: 45", color = MaterialTheme.colors.onSurface)
            Text("Gender: Male", color = MaterialTheme.colors.onSurface)
            Text("Diagnosis: Hypertension", color = MaterialTheme.colors.onSurface)
        }
    }
}

@Composable
fun WelcomeText() {
    Text(
        text = "Welcome to the Patient Monitoring App!",
        style = MaterialTheme.typography.h4,
        textAlign = TextAlign.Center,
        color = MaterialTheme.colors.primary
    )
}


@Composable
fun MedicationSchedule() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Medication Schedule",
                style = MaterialTheme.typography.h6,
                color = MaterialTheme.colors.primary
            )
            Spacer(modifier = Modifier.height(8.dp))
            styledMedicationText("8:00 AM", "Lisinopril", "10mg")
            styledMedicationText("12:00 PM", "Amlodipine", "5mg")
            styledMedicationText("6:00 PM", "Metoprolol", "50mg")
        }
    }
}
@Composable
fun styledMedicationText(time: String, medication: String, dosage: String) {
    val styledText = buildAnnotatedString {
        withStyle(style = SpanStyle(fontWeight = FontWeight.Light, fontSize = MaterialTheme.typography.subtitle1.fontSize, textDecoration = TextDecoration.Underline)) {
            append(time)
        }
        append(" - ")
        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, fontSize = MaterialTheme.typography.subtitle1.fontSize)) {
            append(medication)
        }
        append(" - ")
        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, fontSize = MaterialTheme.typography.subtitle1.fontSize, fontStyle = FontStyle.Italic)) {
            append(dosage)
        }
    }
    Text(text = styledText)
}
@Composable
fun SimpleBarChart(
    data: List<Float>,
    maxValue: Float,
    barColor: androidx.compose.ui.graphics.Color,
    modifier: Modifier = Modifier
) {
    val barWidth: Dp = 30.dp
    val spaceBetweenBars: Dp = 8.dp

    Column(
        modifier = modifier
            .fillMaxWidth()
            .height((maxValue * 1.2f).dp)
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val canvasWidth = size.width
            val canvasHeight = size.height
            val numberOfBars = data.size
            val totalBarWidth = numberOfBars * barWidth.value + (numberOfBars - 1) * spaceBetweenBars.value

            data.forEachIndexed { index, value ->
                val barHeight = (value / maxValue) * canvasHeight
                val barTop = canvasHeight - barHeight
                val barLeft = (canvasWidth - totalBarWidth) / 2 + index * (barWidth.value + spaceBetweenBars.value)
                val barRight = barLeft + barWidth.value
                drawRect(
                    color = barColor,
                    topLeft = Offset(x = barLeft.toFloat(), y = barTop.toFloat()),
                    size = size.copy(width = barRight - barLeft, height = barHeight)
                )
            }
        }
    }
}
