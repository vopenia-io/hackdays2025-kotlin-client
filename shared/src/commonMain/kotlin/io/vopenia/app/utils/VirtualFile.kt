package io.vopenia.app.utils

import eu.codlab.files.VirtualFile

suspend fun VirtualFile.readStringIfExists() = if (exists()) {
    readString()
} else {
    null
}
