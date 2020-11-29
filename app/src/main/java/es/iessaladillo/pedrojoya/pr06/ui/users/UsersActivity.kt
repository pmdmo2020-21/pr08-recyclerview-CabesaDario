package es.iessaladillo.pedrojoya.pr06.ui.users

import android.content.ClipData.newIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.provider.ContactsContract
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.*
import es.iessaladillo.pedrojoya.pr06.R
import es.iessaladillo.pedrojoya.pr06.data.Database
import es.iessaladillo.pedrojoya.pr06.data.model.User
import es.iessaladillo.pedrojoya.pr06.ui.add_user.AddUserActivity
import es.iessaladillo.pedrojoya.pr06.ui.edit_user.EditUserActivity
import kotlinx.android.synthetic.main.users_activity.*

private const val RC_ADD: Int = 1
private const val RC_CHANGE: Int = 2
private const val EXTRA_USER: String = "EXTRA_USER"

class UsersActivity : AppCompatActivity(){

    // TODO: Código de la actividad.
    //  Ten en cuenta que el recyclerview se muestra en forma de grid,
    //  donde el número de columnas está gestionado
    //  por R.integer.users_grid_columns
    //  ...

    // NO TOCAR: Estos métodos gestionan el menú y su gestión

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.users, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.mnuAdd) {
            onAddUser()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    //Fin no tocar



    private val listAdapter : UsersActivityAdapter = UsersActivityAdapter().also {
        it.onEditListener = { position: Int -> editUser(position) }
        it.onDeleteListener = { position: Int -> deleteUser(position) }
    }

    private val usersViewModel: UsersActivityViewModel by viewModels {
        UsersActivityViewModelFactory(Database)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.users_activity)
        setUpViews()
        observeUsers()
    }



    private fun updateList(newList : List<User>) {
        listAdapter.submistList(newList)
        lblEmptyView.visibility = if(newList.isEmpty()) View.VISIBLE else View.INVISIBLE
        imageViewAdd.visibility = if(newList.isEmpty()) View.VISIBLE else View.INVISIBLE
    }

    private fun setUpViews() {
        setUpRecyclerView()
        imageViewAdd.setOnClickListener { navigateToAdd() }
    }

    private fun setUpRecyclerView() {
        lstUsers.setHasFixedSize(true)
        lstUsers.adapter=listAdapter
        lstUsers.layoutManager = GridLayoutManager(this, resources.getInteger(R.integer.users_grid_columns))
        lstUsers.addItemDecoration(DividerItemDecoration(this,RecyclerView.VERTICAL))
        lstUsers.itemAnimator = DefaultItemAnimator()
    }

    private fun observeUsers() {
        usersViewModel.users.observe(this){
            updateList(it)
        }
    }

    //parte de la navegación

    fun onAddUser() {
        navigateToAdd()
    }

    private fun navigateToAdd() {
        val intent = AddUserActivity.newIntent(this)
        startActivityForResult(intent, RC_ADD)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent?) {
        super.onActivityResult(requestCode, resultCode, intent)
        if (resultCode == RESULT_OK  && intent != null) {
            extractResult(intent)
        }
    }

    private fun extractResult(intent: Intent) {
        usersViewModel.sacarUsuario(intent.getParcelableExtra(EXTRA_USER)!!)
    }

    private fun editUser(position : Int){
        val user: User = listAdapter.getItem(position) //esto es por el adapter
        navigateToChangeUser(user)
    }

    private fun navigateToChangeUser(user: User) {
        val intent = EditUserActivity.newIntent(this, user)
        startActivityForResult(intent, RC_CHANGE)
    }

    private fun deleteUser(position : Int){
        val user: User = listAdapter.getItem(position)
        usersViewModel.deleteUser(user)

    }





}