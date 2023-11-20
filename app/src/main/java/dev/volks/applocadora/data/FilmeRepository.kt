package dev.volks.applocadora.data

import kotlinx.coroutines.flow.Flow

//INTERFACE REPOSITORY UTILIZADA DENTRO DOS REPOSITÓRIOS UTILIZADOS NO PROJETO
interface FilmeRepository {

    val filmes: Flow<List<Filme>>
    suspend fun salvar(filme: Filme)
    suspend fun excluir(filme: Filme)
    suspend fun excluirTodos()
}