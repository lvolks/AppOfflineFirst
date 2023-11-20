package dev.volks.applocadora.ui.dados

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dev.volks.applocadora.MainActivity
import dev.volks.applocadora.databinding.ActivityDadosUsuarioBinding
import dev.volks.applocadora.forms.FormLogin

class DadosUsuario : AppCompatActivity() {

    //ACTIVITY QUE MOSTRA OS DADOS DO USUÁRIO AUTENTICADO
    private lateinit var binding: ActivityDadosUsuarioBinding

    private var nomeUser : TextView?=null
    private var emailUser  : TextView?=null
    private var bt_deslogar  : Button?=null
    private var bt_voltar  : Button?=null
    val db: FirebaseFirestore = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDadosUsuarioBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getSupportActionBar()?.hide()
        IniciarComponentes()

        //ONCLICK BOTÃO DESLOGAR
        bt_deslogar?.setOnClickListener(object: View.OnClickListener {
            override fun onClick(view : View?) {
                FirebaseAuth.getInstance().signOut()
                FormLogin()
            }
        })

        //ONCLICK BOTÃO VOLTAR
        bt_voltar?.setOnClickListener(object: View.OnClickListener {
            override fun onClick(view : View?) {
                MainActivity()
            }
        })

    }

    //FUNÇÃO QUE LEVA PARA A ACTIVITY DE LOGIN
    private fun FormLogin(){
        intent = Intent(this@DadosUsuario, FormLogin::class.java)
        startActivity(intent)
        finish()
    }

    //FUNÇÃO QUE LEVA PARA A ACTIVITY MAIN
    private fun MainActivity(){
        intent = Intent(this@DadosUsuario, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    //FUNÇÃO PARA RECUPERAR OS DADOS QUANDO A ACTIVITY FOR INICIADA
    override fun onStart() {
        super.onStart()

        val usuarioID = FirebaseAuth.getInstance().currentUser?.uid
        val email = FirebaseAuth.getInstance().currentUser?.email
        if (usuarioID != null) {
            val documentReference = db.collection("Usuarios").document(usuarioID)
            documentReference?.addSnapshotListener { documentSnapshot, error ->
                if (documentSnapshot != null) {
                    nomeUser?.setText(documentSnapshot.getString("nome"))
                    emailUser?.setText(email)
                }
            }
        }
    }

    private fun IniciarComponentes(){
        nomeUser = binding.textNomeUser
        emailUser = binding.textEmailUser
        bt_voltar = binding.btVoltar
        bt_deslogar = binding.btDeslogar
    }
}