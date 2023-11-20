package dev.volks.applocadora.forms

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.view.isVisible
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import dev.volks.applocadora.databinding.ActivityFormLoginBinding

//FORMULÁRIO DE LOGIN DO PROJETO
class FormLogin : AppCompatActivity() {

    private lateinit var binding: ActivityFormLoginBinding

    private var text_cadastro : TextView?=null
    private var edit_email : EditText?=null
    private var edit_senha : EditText?=null
    private var bt_entrar : Button?=null
    val mensagens = arrayOf("Todos os campos devem ser preenchidos.", "Login feito com sucesso!", "Erro ao efetuar o login!")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFormLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getSupportActionBar()?.hide()
        IniciarComponentes()

        //ONCLICK NO TEXTO DE CRIAR CONTA
        text_cadastro?.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View?) {
                intent = Intent(this@FormLogin, FormCadastro::class.java)
                startActivity(intent)
            }

        })

        //ONCLICK NO BOTÃO DE ENTRAR
        bt_entrar?.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View) {

                var email : String = edit_email?.text.toString()
                var senha : String = edit_senha?.text.toString()

                if(email.isEmpty() || senha.isEmpty()){
                    var snackbar : Snackbar = Snackbar.make(view, mensagens[0], Snackbar.LENGTH_SHORT)
                    snackbar.setBackgroundTint(Color.WHITE)
                    snackbar.setTextColor(Color.BLACK)
                    snackbar.show()
                } else{
                    AutenticarUsuario(view)
                }
            }
        })
    }

    //AUTENTICAÇÃO DE LOGIN COM EMAIL E SENHA NO FIRABASE
    private fun AutenticarUsuario(view : View){
        var email : String = edit_email?.text.toString()
        var senha : String = edit_senha?.text.toString()

        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, senha)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {

                    MainActivity()

                } else {
                    val snackbar: Snackbar = Snackbar.make(view, mensagens[2], Snackbar.LENGTH_SHORT)
                    snackbar.setBackgroundTint(Color.WHITE)
                    snackbar.setTextColor(Color.BLACK)
                    snackbar.show()
                }
            }
    }

    //FUNÇÃO UTILIZADA PARA QUE O USUÁRIO NÃO PRECISE LOGAR NOVAMENTE CASO JÁ ESTEJA AUTENTICADO
//    override fun onStart() {
//        super.onStart()
//
//        val usuarioAtual : FirebaseUser? = FirebaseAuth.getInstance().currentUser
//
//        if(usuarioAtual != null){
//            MainActivity()
//        }
//    }

    //FUNÇÃO QUE LEVA PARA A ACTIVITY MAIN
    private fun MainActivity(){
        intent = Intent(this@FormLogin, dev.volks.applocadora.MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun IniciarComponentes(){
        text_cadastro = binding.cadastro
        edit_email    = binding.editEmail
        edit_senha    = binding.editSenha
        bt_entrar     = binding.btEntrar
    }
}