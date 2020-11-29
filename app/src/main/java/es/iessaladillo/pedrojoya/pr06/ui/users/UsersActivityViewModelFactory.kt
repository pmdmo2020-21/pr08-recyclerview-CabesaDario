package es.iessaladillo.pedrojoya.pr06.ui.users

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import es.iessaladillo.pedrojoya.pr06.data.DataSource


class UsersActivityViewModelFactory (private val data: DataSource) :
        ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UsersActivityViewModel::class.java)) {
            return UsersActivityViewModel(data) as T
        } else {
            throw IllegalArgumentException("Wrong viewModel class passed")
        }

    }
}