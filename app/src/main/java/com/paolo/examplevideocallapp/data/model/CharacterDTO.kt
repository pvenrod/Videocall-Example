package com.paolo.examplevideocallapp.data.model

import com.google.gson.annotations.SerializedName

private const val DUMMY_NAME = "......."

data class CharacterDTO(
    @SerializedName("name")
    val name: String?,
    @SerializedName("phoneNumber")
    val phoneNumber: String?,
    @SerializedName("conversation")
    val conversation: List<MessageDTO?>?,
) {

    fun isDummy() = name == DUMMY_NAME

    companion object {
        val DUMMY = CharacterDTO(
            name = DUMMY_NAME,
            phoneNumber = ".......",
            conversation = listOf(
                MessageDTO(
                    question = "question 1",
                    answers = listOf(
                        "answer1",
                        "answer2",
                        "answer3",
                    )
                ),
                MessageDTO(
                    question = "question 2",
                    answers = listOf(
                        "answer4",
                        "answer5",
                        "answer6",
                    )
                ),
            )
        )
    }
}