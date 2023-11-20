package dev.volks.applocadora.data

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.forEach
import javax.inject.Inject

class FilmeRepositoryFirebase @Inject constructor(
    private val filmesRef: CollectionReference
) : FilmeRepository {

    private var _filmes = MutableStateFlow(listOf<Filme>())
    override val filmes: Flow<List<Filme>> get() = _filmes.asStateFlow()


    init {
        filmesRef.addSnapshotListener { snapshot, _ ->
            if (snapshot != null) {
                var filmes = mutableListOf<Filme>()
                snapshot.documents.forEach { doc ->
                    val filme = doc.toObject<Filme>()
                    if (filme != null) {
                        filme.docId = doc.id
                        filmes.add(filme)
                    }
                }
                _filmes.value = filmes
            } else {
                _filmes = MutableStateFlow(listOf())
            }
        }
    }

    override suspend fun salvar(filme: Filme) {
        try {
            if (filme.docId.isNullOrEmpty()) {
                val doc = filmesRef.document()
                filme.docId = doc.id
                doc.set(filme)
            } else {
                filmesRef.document(filme.docId).set(filme)
            }
        } catch (e: Exception) {
            println("Erro: $e")
            throw e
        }
    }



    override suspend fun excluir(filme: Filme) {
        filmesRef.document(filme.docId).delete()
    }

    override suspend fun excluirTodos() = coroutineScope {
        _filmes.collect{ filmes ->
            filmes.forEach {filme ->
                filmesRef.document(filme.docId).delete()
            }
        }
    }

    suspend fun atualizarFilmesNoFirebase() {
        _filmes.collect { filmes ->
            filmes.forEach{filme ->
                salvar(filme)
            }
        }
    }

}