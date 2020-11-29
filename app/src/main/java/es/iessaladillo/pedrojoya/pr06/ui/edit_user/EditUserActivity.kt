package es.iessaladillo.pedrojoya.pr06.ui.edit_user

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
import com.google.android.material.snackbar.Snackbar
import es.iessaladillo.pedrojoya.pr06.R
import es.iessaladillo.pedrojoya.pr06.data.model.User
import es.iessaladillo.pedrojoya.pr06.databinding.UserActivityBinding
import es.iessaladillo.pedrojoya.pr06.ui.users.UsersActivity
import es.iessaladillo.pedrojoya.pr06.utils.SoftInputUtils
import es.iessaladillo.pedrojoya.pr06.utils.loadUrl
import kotlinx.android.synthetic.main.user_activity.*
import java.lang.RuntimeException

class EditUserActivity : AppCompatActivity() {

    // TODO: Código de la actividad.
    //  Ten en cuenta que la actividad debe recibir a través del intent
    //  con el que es llamado el objeto User corresondiente
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

    private val viewModel: EditUserViewModel by viewModels()

    private lateinit var user:User

    companion object {
        const val EXTRA_USER: String = "EXTRA_USER"
        fun newIntent(context: Context, user: User): Intent {
            return Intent(context, EditUserActivity::class.java)
                    .putExtra(EXTRA_USER, user)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.user_activity)

        if(savedInstanceState == null){
            getIntenData()
            viewModel.changePhoto(user)
            setUpViews()
        }
        binding.photoUser!!.setOnClickListener { changeAleatory() } //no se porque da nullpointer al girar, no me esta cargando el (land)
        binding.photoUser!!.loadUrl(viewModel.image.value!!)
        lbl_edit_url.setOnEditorActionListener { _, actionId, _ -> //en caso de que se de pulse action done en el ultimo edit text
            if(actionId == EditorInfo.IME_ACTION_DONE){
                validateAndGoBack()
                true
            } else {
                false
            }
        }
    }

    private fun getIntenData() {
        if(intent == null || !intent.hasExtra(EXTRA_USER)){
            throw RuntimeException("willy el intent que pasa")
        }
        user = intent.getParcelableExtra<User>(EXTRA_USER)!! //saco el parcelable del intent
    }

    private fun changeAleatory() {
        viewModel.aleatoryPhoto() //se cambia el valor en el viewmodel
        binding.photoUser!!.loadUrl(viewModel.image.value!!) //se carga el valor del viewmodel en la vista
    }

    private fun setUpViews() { //pinto los editText y la photo del usuario
        binding.lblEditName!!.text = sacarEditable(user.name)
        binding.lblEditNumber!!.text = sacarEditable(user.phoneNumber)
        binding.lblEditEmail!!.text = sacarEditable(user.email)
        binding.lblEditAdress!!.text = sacarEditable(user.address)
        binding.lblEditUrl!!.text = sacarEditable(user.web)

    }
    private fun sacarEditable(string: String?):Editable = Editable.Factory.getInstance().newEditable(string) //pa pasar de strin a editable




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