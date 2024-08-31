package com.example.new_kuz.domain.modules

import androidx.annotation.DrawableRes
import com.example.new_kuz.R
import kotlinx.serialization.Serializable

sealed class welcomeRoute(val route:String) {
    object splash: welcomeRoute("splash")
    object welcome: welcomeRoute("welcome")
}

sealed class authRoute(val route:String) {
    object signIn: authRoute("sign_in")
    object forgotPassword: authRoute("forgot")
    object signUp: authRoute("sign_up")
    object verifyPin: authRoute("verify_pin")
    object verifyEmail: authRoute("verify_mail")
    object verifyPhone: authRoute("verify_phone")
    object newPassword: authRoute("new_pass")
    object phoneNumber: authRoute("phone")
    object createPassword: authRoute("create_pass")
    object personalInfo: authRoute("personal_info")

}



sealed class inAppNav(
    val route: String,
    @DrawableRes val icon: Int? = null,
    @DrawableRes val filledIcon: Int? = null
){
    object chat: inAppNav("Chat", R.drawable.chat_empty, R.drawable.chat_filled)
    object settings: inAppNav("Settings", R.drawable.setting_empty, R.drawable.settings_filled)
    object home: inAppNav("Home", R.drawable.home_empty, R.drawable.home_filled  )
    object message: inAppNav("message/{receiver}"){
        fun createMessage(receiveruid: String) = "message/$receiveruid"
    }
    object about: inAppNav("about")
    object archive: inAppNav("archive")
}

@Serializable
data class Contact(
    val uid: Users
)