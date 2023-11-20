package dev.volks.applocadora.forms

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dev.volks.applocadora.databinding.ActivityFormCadastroBinding

//FORMULÁRIO DE CADASTRO DO PROJETO
class FormCadastro : AppCompatActivity() {

    private lateinit var binding: ActivityFormCadastroBinding

    private var edit_nome : EditText?=null
    private var edit_email : EditText?=null
    private var edit_senha : EditText?=null
    private var bt_cadastrar : Button?=null
    val mensagens = arrayOf("Todos os campos devem ser preenchidos.", "Cadastro feito com sucesso!", "Erro ao efetuar o cadastro!")



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFormCadastroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getSupportActionBar()?.hide()
        IniciarComponentes()

        //CLICK NO BOTÃO CADASTRAR
        bt_cadastrar?.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View) {

                var nome : String = edit_nome?.text.toString()
                var email : String = edit_email?.text.toString()
                var senha : String = edit_senha?.text.toString()

                if(nome.isEmpty() || email.isEmpty() || senha.isEmpty()){
                    var snackbar : Snackbar = Snackbar.make(view, mensagens[0], Snackbar.LENGTH_SHORT)
                    snackbar.setBackgroundTint(Color.WHITE)
                    snackbar.setTextColor(Color.BLACK)
                    snackbar.show()

                    Handler().postDelayed({
                        FormLogin()
                    }, 1500)

                } else{
                    CadastrarUsuario(view)
                }
            }
        })
    }


    //FUNÇÃO CADASTRAR USUARIO
    private fun CadastrarUsuario(view: View) {
        val email: String = edit_email?.text.toString()
        val senha: String = edit_senha?.text.toString()

        //AUTENTICAÇÃO COM BANCO DE DADOS FIREBASE
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, senha)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {

                    SalvarDadosUsuario()

                    val snackbar: Snackbar = Snackbar.make(view, mensagens[1], Snackbar.LENGTH_SHORT)
                    snackbar.setBackgroundTint(Color.WHITE)
                    snackbar.setTextColor(Color.BLACK)
                    snackbar.show()

                    Handler().postDelayed({
                        FormLogin()
                    }, 1500)

                } else {
                    val snackbar: Snackbar = Snackbar.make(view, mensagens[2], Snackbar.LENGTH_SHORT)
                    snackbar.setBackgroundTint(Color.WHITE)
                    snackbar.setTextColor(Color.BLACK)
                    snackbar.show()
                }
            }
    }

    //FUNÇÃO SALVAR USUÁRIO
    private fun SalvarDadosUsuario(){
        val nome: String = edit_nome?.text.toString()
        val email: String = edit_email?.text.toString()
        val db: FirebaseFirestore = FirebaseFirestore.getInstance()
        val usuarioID = FirebaseAuth.getInstance().currentUser?.uid

        if (usuarioID != null) {
            val usuario = hashMapOf(
                "nome" to nome,
                "email" to email

            )

            db.collection("Usuarios").document(usuarioID)
                .set(usuario)
                .addOnSuccessListener {
                    Log.d("db", "Sucesso ao salvar os dados!")
                }
                .addOnFailureListener { e ->
                    Log.d("db_error", "Erro ao salvar os dados!" + e)
                }
        }
    }

    //FUNÇÃO QUE LEVA PARA A ACTIVITY DO FORMULÁRIO DE LOGIN
    private fun FormLogin(){
        intent = Intent(this@FormCadastro, FormLogin::class.java)
        startActivity(intent)
        finish()
    }

    private fun IniciarComponentes(){
        edit_nome    = binding.editNome
        edit_email   = binding.editEmail
        edit_senha   = binding.editSenha
        bt_cadastrar = binding.btCadastrar
    }
}