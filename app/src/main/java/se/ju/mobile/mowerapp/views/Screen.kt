package se.ju.mobile.mowerapp.views

sealed class Screen(val route: String) {
    object DrivingScreen : Screen("driving_screen")
    object SessionHistoryScreen : Screen("session_history_screen")
    object SessionSummaryScreen : Screen("sessionSummary")
    object CollisionAvoidedScreen : Screen("collisionAvoided")
}
