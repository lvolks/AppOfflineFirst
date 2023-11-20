package dev.volks.applocadora.ui.filme

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.volks.applocadora.data.Filme
import dev.volks.applocadora.data.FilmeRepositoryFirebase
import dev.volks.applocadora.data.FilmeRepositorySQLite
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

//VIEW MODEL DO FILME
@HiltViewModel
class FilmeViewModel
@Inject constructor(val repository: FilmeRepositorySQLite,
                    val repositoryFirebase: FilmeRepositoryFirebase,
                    private val context: Context
    ) : ViewModel() {

    var filme: Filme = Filme()
    private var listaFilmes = MutableStateFlow(listOf<Filme>())
    val filmes : Flow<List<Filme>> = listaFilmes


    init {
        viewModelScope.launch {
            repositoryFirebase.filmes.collect{ filmes ->
                atualizarFilmesSeNecessario()
                listaFilmes.value = filmes
            }
        }
    }

    private fun isNetworkAvailable(): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val network = connectivityManager.activeNetwork ?: return false
            val activeNetwork =
                connectivityManager.getNetworkCapabilities(network) ?: return false
            return when {
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                else -> false
            }
        } else {
            val networkInfo = connectivityManager.activeNetworkInfo ?: return false
            return networkInfo.isConnected
        }
    }

    fun atualizarFilmesSeNecessario() {
        viewModelScope.launch {
            if (isNetworkAvailable()) {
                repository.listarFilmesLocais()
                repositoryFirebase.atualizarFilmesNoFirebase()
            }
        }
    }

    //FUNÇÕES
    fun novo(){
        this.filme = Filme()
    }

    fun editar(filme: Filme){
        this.filme = filme
//        repository.salvar(filme)
//        repositoryFirebase.salvar(filme)
    }

    fun salvar() = viewModelScope.launch {
        repository.salvar(filme)
        repositoryFirebase.salvar(filme)
    }

    fun excluir(filme: Filme) = viewModelScope.launch {
        repository.excluir(filme)
        repositoryFirebase.excluir(filme)
    }

}
