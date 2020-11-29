package es.iessaladillo.pedrojoya.pr06.ui.edit_user

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import es.iessaladillo.pedrojoya.pr06.data.model.User
import kotlin.random.Random

// TODO:
//  Crear la clase EditUserViewModel. Ten en cuenta que la url de la photo
//  deberá ser preservada por si la actividad es destruida por falta de recursos.

const val STATE_IMAGE = "STATE_IMAGE"
const val STATE_USER = "STATE_USER"

class EditUserViewModel(savedStateHandle: SavedStateHandle): ViewModel() {


    private val _image: MutableLiveData<String> =
            savedStateHandle.getLiveData(STATE_IMAGE, "https://picsum.photos/id/3/400/300")

    val image: LiveData<String>
        get() = _image

    private val _user: MutableLiveData<User> =
            savedStateHandle.getLiveData(STATE_USER, User(1,"paquito","@love.com","666666",
            "calle las torre","la casita.es","https://picsum.photos/id/3/400/300"))
    val user: LiveData<User>
        get() = _user



    // Para obtener un URL de foto de forma aleatoria (tendrás que definir
    // e inicializar el random a nivel de clase.
    private fun getRandomPhotoUrl(): String =
            "https://picsum.photos/id/${Random.nextInt(100)}/400/300"

    fun changePhoto(user: User) {
        _image.value = user.photoUrl
    }

    fun aleatoryPhoto() {
        _image.value = getRandomPhotoUrl()
    }



}
