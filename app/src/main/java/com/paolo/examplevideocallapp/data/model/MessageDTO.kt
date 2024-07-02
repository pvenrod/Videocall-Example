package com.paolo.examplevideocallapp.data.model

import com.google.gson.annotations.SerializedName

data class MessageDTO(
    @SerializedName("question")
    val question: String?,
    @SerializedName("answers")
    val answers: List<String?>?,
)

enum class MessageType {
    REAL_USER,
    FAKE_USER,
}