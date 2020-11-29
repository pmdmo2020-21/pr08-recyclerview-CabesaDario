package es.iessaladillo.pedrojoya.pr06.data.model

import android.os.Parcel
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(var id: Long, var name: String?, var email: String?, var phoneNumber: String?, var address: String?, var web: String?, var photoUrl: String?) : Parcelable {
    override fun equals(other: Any?): Boolean{ //personalizo mi equals pa que compare solo los ids
        if (other?.javaClass != javaClass) return false

        other as User

        if (id != other.id) return false

        return true
    }
}
// TODO:
//  Crear una clase de datos User que implemente Parcelable y que
//  contenga el id de tipo Long, nombre, email, phoneNumber, address, web y photoUrl
//  todas ellas de tipo String.
