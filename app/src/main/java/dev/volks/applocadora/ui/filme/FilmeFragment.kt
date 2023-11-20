package dev.volks.applocadora.ui.filme

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import dev.volks.applocadora.databinding.FragmentFilmeBinding

//FRAGMENT FILME ONDE ESTÁ O CADASTRO DO FILME
@AndroidEntryPoint
class FilmeFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        val viewModel : FilmeViewModel by activityViewModels()


        val binding = FragmentFilmeBinding.inflate(layoutInflater)

        var filme = viewModel.filme
        binding.labNome.setText(filme.nome)
        binding.labDesc.setText(filme.descricao)
        binding.labPreco.setText(filme.preco.toString())
        binding.labNota.setText(filme.nota)
        binding.labFoto.setText(filme.foto.toString())

        //ONCLICK BOTÃO SALVAR
        binding.btSalvar.setOnClickListener {
            try {
                viewModel.filme.nome = binding.labNome.text.toString()
                viewModel.filme.descricao = binding.labDesc.text.toString()
                viewModel.filme.nota = binding.labNota.text.toString()
                viewModel.filme.preco = binding.labPreco.text.toString().toDouble()
                viewModel.filme.foto = binding.labFoto.text.toString()
            } catch (e: Exception){
            }
            viewModel.salvar()
            findNavController().popBackStack()

        }
        return binding.root

    }
}
