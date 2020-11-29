package es.iessaladillo.pedrojoya.pr06.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import es.iessaladillo.pedrojoya.pr06.data.model.User


object Database : DataSource{
    private val list : MutableList<User> = mutableListOf()
    private val lista : MutableLiveData<List<User>> = MutableLiveData<List<User>>()
    private var id: Long = 0;

    init {
        updateLiveData()
    }

    override fun getAllUsersOrderedByName(): LiveData<List<User>> = lista

    private fun updateLiveData(){
        lista.value = list.sortedBy { it.name }
    }

    override fun insertUser(user: User) {
        user.id = ++id
        list.add(user)
        updateLiveData()
    }

    override fun updateUser(user: User) {
        val indexOfFirst = list.indexOfFirst { it.id == user.id }
        if (indexOfFirst != -1) {
            list[indexOfFirst] = user
            updateLiveData()
        }
    }

    override fun deleteUser(user: User) {
        val indexOfFirst = list.indexOfFirst { it.id == user.id }
        if (indexOfFirst != -1) {
            list.removeAt(indexOfFirst)
            updateLiveData()
        }
    }

}
// TODO:
//  Crear una singleton Database que implemente la interfaz DataSource.
//  Al insertar un usuario, se le asignará un id autonumérico
//  (primer valor válido será el 1) que deberá ser gestionado por la Database.
//  La base de datos debe ser reactiva, de manera que cuando se agrege,
//  actualice o borre un usuario, los observadores de la lista deberán
//  recibir la nueva lista.
//  Al consultar los usuario se deberá retornar un LiveData con la lista
//  de usuarios ordenada por nombre

