package io.vopenia.app.utils

import dev.icerock.moko.mvvm.viewmodel.ViewModel
import eu.codlab.viewmodel.launch
import kotlinx.coroutines.CoroutineScope

fun ViewModel.safeLaunch(
    onError: (Throwable) -> Unit = { /* nothing */ },
    run: suspend CoroutineScope.() -> Unit
) = OriginalException().let { original ->
    launch(
        onError = { error ->
            error.addSuppressed(original)
            onError(error)
        },
        run
    )
}

class OriginalException : Exception()
