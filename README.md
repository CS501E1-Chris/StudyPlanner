# Focus Plan Builder

**Name:** Chris Mary Benson
**Assignment:** Individual Coding Assignment 2 - Study Planner
**Course:** CS 501 E1

## Description

'Focus' is a single-screen Android application, built with Kotlin and Jetpack
Compose (Material 3), that helps a student turn a study subject and duration time
into a study plan. The user enters a subject and the number of minutes they
have available. The app validates both inputs in real time, and once they're valid, generates
a plan showing the study subject, session duration, a duration category, a recommended break length, and a short summary
sentence describing the plan.

## Running the Application

1. Clone this repository:
   ```
   git clone https://github.com/CS501E1-Chris/StudyPlanner.git
   ```
2. Open the project in Android Studio.
3. Let Gradle sync automatically finish.
4. Run the app on an emulator or physical device running Android 24+.
5. Enter a subject (e.g. "Kotlin") and a duration between 10 and 180 minutes, then tap **Create plan** to generate a study plan.

## Screenshot & Questions

![View Report](./StudyPlanner_Assignment2.pdf)

## State and Recomposition

'FocusPlanRoute' owns the screen’s state: subject, minutesText, and plan. The text fields use rememberSaveable so that the state i.e the values persist in cases of activity changes such as screen rotation, etc.
Since text fields use String, minutesText.toIntOrNull() is used to safely convert the input to a number.
'canCreatePlan' and the error states are derived from the current input, so they do not need to be stored separately. When the user types, the state changes and Compose triggers recomposition. 
During recomposition, the derived values are recalculated and the updated values are passed to FocusPlanScreen, so the button and error messages update automatically. 
FocusPlanScreen does not manage the state itself; it receives the values and sends changes back through callbacks.

## Generative AI Assistance

- **Tool used:** Claude (Anthropic)
- **What assistance it provided:** Suggested Material 3
  color theming and layout adjustments, helped debug a compile error in the `onCreatePlan`
  callback (nullable `Int` smart-casting), and helped structure `strings.xml` resources with
  format arguments, helped in suggestions for input validations.
  Asked AI to generate Color.kt and Theme.kt according to the theme that I generated on Figma.
- **What portions I changed or verified:** I wrote the initial validation logic myself and had it reviewed
- **How I confirmed I understand the submitted code:** I traced through the
  recomposition flow manually for each state change, tested all 11 cases from the assignment
  spec, and can explain every function and composable in this file without referring to
  notes
