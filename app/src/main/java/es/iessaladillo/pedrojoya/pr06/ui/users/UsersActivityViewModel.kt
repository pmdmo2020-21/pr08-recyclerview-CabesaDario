package es.iessaladillo.pedrojoya.pr06.ui.users

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import es.iessaladillo.pedrojoya.pr06.data.DataSource
import es.iessaladillo.pedrojoya.pr06.data.model.User

// TODO:
//  Crear clase UsersActivityViewModel
class UsersActivityViewModel(val dataSource: DataSource) :ViewModel() {


    val users: LiveData<List<User>> = dataSource.getAllUsersOrderedByName()

    fun addUser(user: User) {
        dataSource.insertUser(user)
    }

    fun changeUser(user: User) {
        dataSource.updateUser(user)
    }

    fun deleteUser(user: User) {
        dataSource.deleteUser(user)
    }

    fun sacarUsuario(user: User) {
        if(user.id.toInt() == -1){ //si venimos de la actividad de añadir, me aseguraré de que el id sea -1, en caso contrario, será
            //que estamos viniendo de la de editar
            addUser(user)
        }else{
            changeUser(user)
        }
    }


}

