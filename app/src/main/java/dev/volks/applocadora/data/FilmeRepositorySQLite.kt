package dev.volks.applocadora.data

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

//REPOSITÓRIO SQLITE ONDE É PASSADO O PARÂMETRO FILME PARA ATIVAR AS FUNÇÕES
class FilmeRepositorySQLite
    @Inject constructor(val filmeDao: FilmeDao)
    : FilmeRepository {

   override val filmes: Flow<List<Filme>>
       get() = filmeDao.listar()

   override suspend fun salvar(filme: Filme) {
        if(filme.id == 0){
            var teste = filmeDao.inserir(filme)
            filme.id = teste.toInt()
        } else {
            filmeDao.atualizar(filme)
        }
    }

   override suspend fun excluir(filme: Filme) {
        filmeDao.excluir(filme)
    }

    override suspend fun excluirTodos() {
        filmeDao.excluirTodos()
    }

    fun listarFilmesLocais(): Flow<List<Filme>> {
        return filmeDao.listar()
    }

}