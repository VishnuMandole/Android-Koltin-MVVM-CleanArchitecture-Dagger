package com.marvel.marvelapplication.characters.details

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.marvel.domain.util.Status
import com.marvel.marvelapplication.characters.CharacterListViewModel
import com.marvel.marvelapplication.characters.CharacterListViewModelFactory
import com.marvel.marvelapplication.databinding.FragmentCharacterDetailsBinding
import com.marvel.marvelapplication.util.MyApplication
import com.marvel.marvelapplication.util.StringExtensions.toast
import javax.inject.Inject

/* CharacterDetailsFragment show  characters details*/
class CharacterDetailsFragment : Fragment() {

    @Inject
    lateinit var factory: CharacterListViewModelFactory
    private lateinit var binding: FragmentCharacterDetailsBinding
    private lateinit var mainViewModel: CharacterListViewModel
    private var characterId: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let { characterId = it.getInt(CHARACTER_ID) }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCharacterDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainViewModel =
            ViewModelProvider(requireActivity(), factory)[CharacterListViewModel::class.java]
        mainViewModel.getCharactersDetails(characterId).observe(viewLifecycleOwner, { it ->
            when (it.status) {
                Status.SUCCESS -> {
                    binding.progressBar.visibility = View.GONE
                    it.data?.let {
                        binding.character = it
                    }
                }
                Status.LOADING -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                Status.ERROR -> {
                    binding.progressBar.visibility = View.GONE
                    it.message?.toast(requireContext())
                }
            }
        })
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity?.applicationContext as MyApplication).appComponent.inject(this)
    }

    companion object {
        const val CHARACTER_ID = "characterId"
    }
}