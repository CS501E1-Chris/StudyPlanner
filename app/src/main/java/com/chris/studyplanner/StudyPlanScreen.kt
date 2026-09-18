package com.chris.studyplanner

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

fun durationCategory(minutes: Int): String{

    return when {
        minutes < 10 -> "Invalid"
        minutes in 10..29 -> "Quick Review"
        minutes in 30..60 -> "Focussed Session"
        else -> "Extended session"
    }
}

fun recommendedBreak(minutes: Int): Int{
    return when {
        minutes in 10..29 -> 5
        minutes in 30..60 -> 10
        else -> 15
    }
}

@Composable
fun FocusPlanRoute(
    modifier: Modifier = Modifier
)
{
    var subject by rememberSaveable {mutableStateOf("")}
    var minutesInput by rememberSaveable {mutableStateOf("")}
    //making focus plan nullable type
    var plan by rememberSaveable {mutableStateOf<FocusPlan?>(null)}

    val minutes = minutesInput.toIntOrNull()
    val canCreatePlan =
        subject.isNotBlank() &&
                minutes != null &&
                minutes in 10..180

//    if(canCreatePlan)
//    {
//        FocusPlan
//
//    }

    FocusPlanScreen(subject,
        minutesInput,
        plan,
        //callback parameters to enable state hoisting
        onSubjectChange = { Val ->
            subject = Val
            plan = null
        },
        onMinutesChange = { minVal ->
            minutesInput = minVal
            plan = null
        },
        canCreatePlan,
        onCreatePlan = {
            //check if create plan is valid
            if (canCreatePlan)
            {
             //create a new plan
             plan = FocusPlan(
                 subject = subject.trim(),
                 minutes = minutes,
                 category = durationCategory(minutes),
                 breakMinutes = recommendedBreak(minutes)
             )
            }
        },
        modifier
        )
}

@Composable
fun FocusPlanScreen(
    subject: String,
    minutesInput: String,
    plan: FocusPlan?,
    onSubjectChange: (String) -> Unit,
    onMinutesChange: (String) -> Unit,
    canCreatePlan: Boolean,
    onCreatePlan: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = Modifier
            //fill the maximum with the screen
            .fillMaxSize()
            //padding included so it does not get placed near the edges of the screen
            .padding(24.dp)
            //since the user and display text field was getting cut off because of the dimensions
            .verticalScroll(rememberScrollState()), verticalArrangement = Arrangement.Top)
    {
        Spacer(Modifier.height(50.dp))

        Text(
            text = stringResource(R.string.app_name),
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(16.dp))
        Text(
            text = stringResource(R.string.app_desc),
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(50.dp))

        //card with user input
        Card(
            modifier = Modifier.fillMaxWidth()
        )
        {
            Spacer(Modifier.height(25.dp))

            OutlinedTextField(
                value = subject,
                onValueChange = onSubjectChange,
                label = {Text("Subject")},
                modifier = Modifier,
            )

            Spacer(Modifier.height(16.dp))

            OutlinedTextField(
                value = minutesInput,
                onValueChange = onMinutesChange,
                label = {Text("Duration")},
                modifier = Modifier,
            )

            Spacer(Modifier.height(25.dp))

            Button(
                onClick = onCreatePlan,
                //button is enabled if canCreatePlan is true
                enabled = canCreatePlan,
                modifier = Modifier.align(Alignment.CenterHorizontally),
            )
                {
                    Text ("Create Plan")
                }

        }

        Spacer(Modifier.height(50.dp))

        //card with study plan
        //since class focus plan is nullable type, we need to put a check as below
        //the below card only gets created on the condition that the plan is not null
        if (plan != null)
        {
            Card(
                modifier = Modifier.fillMaxWidth()
            )
            {
                Spacer(Modifier.height(25.dp))

                //subject, duration, category, break
                Text(
                    text = "Your Study Plan",
                )
                Text(
                    text = plan.subject,
                    style = MaterialTheme.typography.bodyLarge)

                Card()
                {
                    Text(
                        text = "Duration: ${plan.minutes}",
                    )
                }
                Card()
                {
                    Text(
                        text = "Category: ${plan.category}",
                    )
                }
                Spacer(Modifier.height(16.dp))

                Text("Recommended Break")
                Text(
                    text = "${plan.breakMinutes} minutes after session",
                    style = MaterialTheme.typography.bodyLarge
                )
                Spacer(Modifier.height(25.dp))

                Card()
                {
                    Text(
                    text = "Study ${plan.subject} for ${plan.minutes} minutes, then take a ${plan.breakMinutes} minute break! Take it slow...",
                    )
                }
            }
        }

    }
}