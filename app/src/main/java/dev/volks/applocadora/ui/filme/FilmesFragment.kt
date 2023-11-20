package dev.volks.applocadora.ui.filme

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import dev.volks.applocadora.data.FilmeRepository
import dev.volks.applocadora.data.SQLite
import dev.volks.applocadora.databinding.FragmentFilmeBinding
import dev.volks.applocadora.databinding.ListaFilmesBinding
import kotlinx.coroutines.launch

//FRAGMENT DOS FILMES
@AndroidEntryPoint
class FilmesFragment : Fragment() {

    private var columnCount = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        arguments?.let {
            columnCount = it.getInt(ARG_COLUMN_COUNT)
        }

    }

    @SuppressLint("UnsafeRepeatOnLifecycleDetector")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val viewModel : FilmeViewModel by activityViewModels()

        val binding = ListaFilmesBinding.inflate(layoutInflater)

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.filmes.collect{ filmes ->
                    if (binding.root is RecyclerView) {
                        val recyclerView = binding.root
                        with(recyclerView) {
                            layoutManager = LinearLayoutManager(context)
                            adapter = FilmesAdapter(filmes,viewModel)
                        }
                    }
                }
            }

        }
        return binding.root
    }

    companion object {

        const val ARG_COLUMN_COUNT = "column-count"

        @JvmStatic
        fun newInstance(columnCount: Int) =
            FilmesFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_COLUMN_COUNT, columnCount)
                }
            }
    }
}

