package com.example.new_kuz.util

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import java.lang.Error

sealed class RequestState<out T> {
    data object Idle : RequestState<Nothing>()
    data object Loading : RequestState<Nothing>()
    data class Success<T>(val data: T) : RequestState<T>()
    data class Error(val message: String) : RequestState<Nothing>()

    fun isLoading() = this is Loading
    fun isSuccess() = this is Success
    fun isError() = this is Error

    /**
     * Returns data from [Success]
     * Throws [ClassCastException] - if the current class data is not [Success]
     */

    fun getSuccessData() = (this as Success).data
    fun getSuccessDataOrNull(): T? {
        return try {
            (this as Success).data
        } catch (e: Exception) {
            null
        }
    }

    /**
     * Returns data from [Error]
     * Throws [ClassCastException] - if the current class data is not [Error]
     */

    fun getErrorData() = (this as Error).message
    fun getErrorDataOrNull(): String? {
        return try {
            (this as Error).message
        } catch (e: Exception) {
            null
        }
    }

    @Composable
    fun DisplayResult(
        onIdle: (@Composable () -> Unit)? = null,
        onLoading: @Composable () -> Unit,
        onSuccess: @Composable () -> Unit,
        onError: @Composable () -> Unit
    ) {
        AnimatedContent(
            targetState = this,
            label = "Content Animation",
            transitionSpec = {
                fadeIn(tween(300)) togetherWith fadeOut(tween(300))
            }
        ) { state->
            when (state) {
                Idle -> {
                    onIdle?.invoke()
                }
                Loading -> {
                    onLoading()
                }
                is Success -> {
                    onSuccess()
                }
                is Error -> {
                    onError()
                }
            }
        }

    }
}