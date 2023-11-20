package dev.volks.applocadora.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

//INTERFACE DAO CONTENDO AS FUNÇÕES A SEREM UTILIZADAS NO CRUD DO FILME
@Dao
interface FilmeDao {

    @Query("SELECT * FROM filmes")
    fun listar() : Flow<List<Filme>>

    @Insert
    suspend fun inserir(filme : Filme) : Long

    @Update
    suspend fun atualizar(filme : Filme)

    @Delete
    suspend fun excluir(filme: Filme)

    @Query("DELETE FROM filmes WHERE id = (:id)")
    suspend fun excluir (id : Int)

    @Query("DELETE FROM filmes")
    suspend fun excluirTodos()

}