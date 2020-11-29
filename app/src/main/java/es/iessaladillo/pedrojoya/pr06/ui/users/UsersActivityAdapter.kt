package es.iessaladillo.pedrojoya.pr06.ui.users

import android.service.autofill.OnClickAction
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import es.iessaladillo.pedrojoya.pr06.R
import es.iessaladillo.pedrojoya.pr06.data.model.User
import es.iessaladillo.pedrojoya.pr06.utils.loadUrl
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.users_activity_item.*
import kotlinx.android.synthetic.main.users_activity_item.view.*

class UsersActivityAdapter : RecyclerView.Adapter<UsersActivityAdapter.ViewHolder>() {

    private var data:List<User> = emptyList()
    var onEditListener: ((Int) -> Unit)? = null
    var onDeleteListener: ((Int) -> Unit)? = null

    init {
        setHasStableIds(true)
    }

    override fun getItemId(position: Int): Long {
        return data[position].id
    }

    inner class ViewHolder(override val containerView : View) : RecyclerView.ViewHolder(containerView), LayoutContainer {

        init {
            button_edit.setOnClickListener {
                onEditListener?.invoke(absoluteAdapterPosition)
            }
            button_delete.setOnClickListener {
                onDeleteListener?.invoke(absoluteAdapterPosition)
            }
        }

        fun bind(user: User) {
            user.run {
                containerView.item_name.text = name
                containerView.item_number.text = phoneNumber
                containerView.item_email.text = email
                containerView.lbl_photo.loadUrl(photoUrl.toString())
            }

        }


    }



    fun submistList (newList: List<User>){
        data = newList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemView = layoutInflater.inflate(R.layout.users_activity_item, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = data[position]
        holder.bind(user)
    }

    override fun getItemCount(): Int = data.size
    fun getItem(position: Int): User = data[position]
}