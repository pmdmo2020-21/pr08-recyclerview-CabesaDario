package es.iessaladillo.pedrojoya.pr06.ui.add_user

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.text.Editable
import android.view.Menu
import android.view.MenuItem
import android.view.inputmethod.EditorInfo
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import es.iessaladillo.pedrojoya.pr06.R
import es.iessaladillo.pedrojoya.pr06.data.model.User
import es.iessaladillo.pedrojoya.pr06.databinding.UserActivityBinding
import es.iessaladillo.pedrojoya.pr06.ui.edit_user.EditUserActivity
import es.iessaladillo.pedrojoya.pr06.ui.edit_user.EditUserViewModel
import es.iessaladillo.pedrojoya.pr06.ui.users.UsersActivity
import es.iessaladillo.pedrojoya.pr06.utils.SoftInputUtils
import es.iessaladillo.pedrojoya.pr06.utils.loadUrl
import kotlinx.android.synthetic.main.user_activity.*
import kotlinx.android.synthetic.main.users_activity.*
const val EXTRA_USER: String = "EXTRA_USER"
class AddUserActivity : AppCompatActivity() {


    // TODO: Código de la actividad.
    //  ...

    // NO TOCAR: Estos métodos gestionan el menú y su gestión

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.user, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.mnuSave) {
            onSave()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    // FIN NO TOCAR

    private val binding: UserActivityBinding by lazy { //inflo perezosamente la activida
        UserActivityBinding.inflate(layoutInflater)
    }

    private val viewModel: AddUserViewModel by viewModels()

    private lateinit var user: User

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, AddUserActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        user= viewModel.user.value!!
        setUpViews()
        binding.photoUser!!.setOnClickListener { changeAleatory() }
        lbl_edit_url.setOnEditorActionListener { _, actionId, _ -> //en caso de que se pulse el action done en el ultimo edit text
            if(actionId == EditorInfo.IME_ACTION_DONE){
                validateAndGoBack()
                true
            } else {
                false
            }
        }
    }

    private fun changeAleatory() {
        viewModel.aleatoryPhoto() //se cambia el valor en el viewmodel
        binding.photoUser!!.loadUrl(viewModel.image.value!!) //se carga el valor del viewmodel en la vista
    }

    private fun setUpViews() { //pinto los editText y la photo del usuario
        binding.photoUser!!.loadUrl(viewModel.image.value!!)
    }

    private fun onSave() {
        validateAndGoBack()
    }

    private fun validateAndGoBack() {
        SoftInputUtils.hideSoftKeyboard(binding.root)
        if(!binding.lblEditName!!.text.isEmpty() && !binding.lblEditNumber!!.text.isEmpty() &&
                !binding.lblEditEmail!!.text.isEmpty()){ //campos de los editText
            user.name=binding.lblEditName!!.text.toString()
            user.email=binding.lblEditEmail!!.text.toString()
            user.phoneNumber=binding.lblEditNumber!!.text.toString()
            user.address=binding.lblEditAdress!!.text.toString()
            user.photoUrl=viewModel.image.value!!
            user.web=binding.lblEditUrl!!.text.toString()
            backToUsers()
        }else{
            Snackbar.make(binding.root, getString(R.string.no_valid_user), Snackbar.LENGTH_SHORT).show()
        }
    }

    private fun backToUsers() {
        val intent = Intent().putExtras(bundleOf(EXTRA_USER to user))
        setResult(RESULT_OK, intent)
        finish()
    }

}