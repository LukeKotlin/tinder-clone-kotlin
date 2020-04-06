package com.businesstinder.tinder.Chat

/**
 * Created by manel on 10/31/2017.
 */
class ChatObject(internal var message: String, var currentUser: Boolean) {
    fun getMessage(): String {
        return message
    }

    fun setMessage(userID: String?) {
        message = message
    }

}