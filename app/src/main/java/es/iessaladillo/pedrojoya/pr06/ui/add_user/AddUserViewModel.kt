package es.iessaladillo.pedrojoya.pr06.ui.add_user

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import es.iessaladillo.pedrojoya.pr06.data.Database
import es.iessaladillo.pedrojoya.pr06.data.model.User
import kotlin.random.Random
import kotlin.random.Random.Default.nextInt



const val STATE_IMAGE = "STATE_IMAGE"
const val STATE_USER = "STATE_USER"

class AddUserViewModel(savedStateHandle: SavedStateHandle): ViewModel() {


    private val _image: MutableLiveData<String> =
            savedStateHandle.getLiveData(STATE_IMAGE, getRandomPhotoUrl())

    val image: LiveData<String>
        get() = _image

    private val _user: MutableLiveData<User> = //recordemos que me tengo que acordar de poner -1 como id
            savedStateHandle.getLiveData(STATE_USER, User(-1,"","","","","",""))
    val user: LiveData<User>
        get() = _user



    // Para obtener un URL de foto de forma aleatoria (tendrás que definir
    // e inicializar el random a nivel de clase.
    private fun getRandomPhotoUrl(): String =
            "https://picsum.photos/id/${nextInt(100)}/400/300"

    fun sacarUsuario(user: User) {
        _user.value = user
        _image.value = user.photoUrl
    }

    fun changePhoto(it: User?) {
        _image.value = it!!.photoUrl

    }

    fun aleatoryPhoto() {
        _image.value = getRandomPhotoUrl()
    }

}

