package io.vopenia.app

import androidx.compose.material.ScaffoldState
import eu.codlab.files.VirtualFile
import eu.codlab.viewmodel.StateViewModel
import eu.codlab.viewmodel.launch
import io.vopenia.app.content.navigation.NavigateTo
import io.vopenia.app.http.BackendConnection
import io.vopenia.app.session.SavedSession
import io.vopenia.app.utils.readStringIfExists
import io.vopenia.app.utils.safeLaunch
import io.vopenia.app.widgets.AppBarState
import io.vopenia.app.widgets.FloatingActionButtonState
import io.vopenia.konfig.Konfig
import io.vopenia.sdk.VisioSdk
import io.vopenia.sdk.room.RequestEntryStatus
import io.vopenia.sdk.room.Room
import korlibs.io.lang.toByteArray
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import moe.tlaster.precompose.navigation.Navigator
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.seconds

data class AppModelState(
    var currentRoute: NavigateTo,
    val appBarState: AppBarState = AppBarState.Hidden,
    val floatingActionButtonState: FloatingActionButtonState? = null,

    var initialized: Boolean = false,
    var loading: Boolean = false,
    val session: SavedSession? = null,
    val room: Room? = null,

    val authenticating: Boolean = false,

    val loadRoomInfo: Boolean = false,
    val waitingRoomCode: String? = null,
    val waitingRoomValidation: RequestEntryStatus = RequestEntryStatus.Idle
)

interface AppModel {
    val states: StateFlow<AppModelState>

    var navigator: Navigator?
    var scaffoldState: ScaffoldState?

    var onBackPressed: AppBackPressProvider

    fun isInitialized(): Boolean

    fun initialize()

    fun joinRoom(participant: String)

    fun login(participant: String, room: String, callback: (Boolean) -> Unit)

    fun leaveRoom()

    fun show(navigateTo: NavigateTo)

    fun setAppBarState(appBarState: AppBarState)

    fun showWaitingRoom(roomCode: String, onError: () -> Unit)

    fun logout()
}

class AppModelPreview : AppModel {
    override var navigator: Navigator? = null
    override var scaffoldState: ScaffoldState? = null
    override val states = MutableStateFlow(AppModelState(NavigateTo.Initialize))

    override var onBackPressed = AppBackPressProvider()

    override fun isInitialized(): Boolean {
        return false
    }

    override fun initialize() {
        // nothing
    }

    override fun joinRoom(participant: String) {
        // nothing
    }

    override fun login(username: String, password: String, callback: (Boolean) -> Unit) {
        // nothing
    }

    override fun showWaitingRoom(roomCode: String, onError: () -> Unit) {
        // nothing
    }

    override fun logout() {
        // nothing
    }

    override fun leaveRoom() {
        // nothing
    }

    override fun show(navigateTo: NavigateTo) {
        // nothing
    }

    override fun setAppBarState(appBarState: AppBarState) {
        // nothing
    }
}

class AppModelImpl : StateViewModel<AppModelState>(AppModelState(NavigateTo.Initialize)),
    AppModel {
    private val session = VisioSdk.openSession(
        "${Konfig.tunnelApiForwarder}/api/v1.0",
        false
    ) {
        states.value.session?.let {
            backendConnection.token(it.userName ?: "", it.password ?: "")
        } ?: throw IllegalStateException("Couldn't connect the user")
    }
    override var navigator: Navigator? = null
    override var scaffoldState: ScaffoldState? = null

    private val backendConnection = BackendConnection()

    override var onBackPressed: AppBackPressProvider = AppBackPressProvider()

    private val sessionFile = VirtualFile(VirtualFile.Root, "user.json")

    companion object {
        fun fake() = AppModelImpl()
    }

    override fun isInitialized() = states.value.initialized

    @Suppress("TooGenericExceptionCaught", "SwallowedException")
    override fun initialize() {
        launch {
            try {
                val session = sessionFile.readStringIfExists()?.let {
                    return@let Json.decodeFromString<SavedSession>(it)
                }

                delay(100.milliseconds)

                updateState {
                    copy(
                        initialized = true,
                        session = session,
                        waitingRoomCode = session?.lastRoomCode,
                    )
                }

                show(NavigateTo.Main())
            } catch (err: Throwable) {
                err.printStackTrace()
            }
        }
    }

    override fun joinRoom(participant: String) {
        safeLaunch(onError = { it.printStackTrace() }) {
            val roomObject = states.value.room!!
            val management = roomObject.requestEntry(participant)

            updateState { copy(waitingRoomValidation = management.currentRequestEntryStatus.status) }

            while (management.currentRequestEntryStatus.status != RequestEntryStatus.Accepted) {
                delay(5.seconds)
            }

            updateState { copy(waitingRoomValidation = management.currentRequestEntryStatus.status) }
            roomObject.connect()

            show(NavigateTo.Room())
        }
    }

    override fun login(username: String, password: String, callback: (Boolean) -> Unit) {
        safeLaunch {
            try {
                updateState { copy(authenticating = true) }

                val info = BackendConnection().token(username, password)
                val newSession = SavedSession(
                    userName = username,
                    password = password,
                    lastRoomCode = states.value.waitingRoomCode
                )

                sessionFile.write(Json.encodeToString(newSession).toByteArray())

                updateState {
                    copy(
                        authenticating = true,
                        session = newSession
                    )
                }
                callback(true)
            } catch (err: Throwable) {
                updateState {
                    copy(
                        authenticating = false
                    )
                }
                callback(false)
            }
        }
    }

    override fun leaveRoom() {
        launch {
            states.value.room?.disconnect()
            updateState { copy(room = null) }
        }
    }

    override fun show(navigateTo: NavigateTo) {
        if (navigateTo.popBackStack) {
            popBackStack()
        }

        navigator?.navigate(
            route = navigateTo.route,
            options = navigateTo.options
        )

        safeLaunch {
            scaffoldState?.drawerState?.close()
        }

        updateState {
            copy(
                currentRoute = navigateTo,
                appBarState = AppBarState.Hidden,
                floatingActionButtonState = null
            )
        }
    }

    override fun setAppBarState(appBarState: AppBarState) {
        safeLaunch {
            updateState {
                copy(appBarState = appBarState)
            }
        }
    }

    override fun showWaitingRoom(roomCode: String, onError: () -> Unit) {
        launch {
            try {
                updateState {
                    copy(
                        loadRoomInfo = true,
                        waitingRoomCode = roomCode
                    )
                }
                val roomObject = session.room(roomCode)!!

                val newSession = (states.value.session ?: SavedSession()).copy(
                    lastRoomCode = states.value.waitingRoomCode
                )

                sessionFile.write(Json.encodeToString(newSession).toByteArray())

                updateState {
                    copy(
                        session = newSession,
                        loadRoomInfo = false,
                        room = roomObject
                    )
                }

                show(NavigateTo.Join())
            } catch (err: Throwable) {
                updateState {
                    copy(
                        loadRoomInfo = false
                    )
                }
            }
        }
    }

    override fun logout() {
        safeLaunch {
            sessionFile.delete()

            updateState {
                copy(session = null)
            }
        }
    }

    fun popBackStack() {
        navigator?.popBackStack()
    }
}
