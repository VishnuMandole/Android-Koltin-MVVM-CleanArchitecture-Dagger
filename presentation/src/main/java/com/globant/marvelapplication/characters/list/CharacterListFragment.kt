package com.marvel.marvelapplication.characters.list

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.marvel.domain.model.Characters
import com.marvel.domain.util.Status
import com.marvel.marvelapplication.characters.CharacterListViewModel
import com.marvel.marvelapplication.characters.CharacterListViewModelFactory
import com.marvel.marvelapplication.databinding.CharacterListFragmentBinding
import com.marvel.marvelapplication.util.MyApplication
import com.marvel.marvelapplication.util.StringExtensions.toast
import javax.inject.Inject

/* MainFragment to load characters list from api and on click navigate to details screen */
class CharacterListFragment : Fragment(), ItemClickListener {

    @Inject
    lateinit var factory: CharacterListViewModelFactory
    private lateinit var characterDataAdapter: CharacterListAdapter
    private lateinit var binding: CharacterListFragmentBinding
    private lateinit var mainViewModel: CharacterListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = CharacterListFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainViewModel =
            ViewModelProvider(requireActivity(), factory)[CharacterListViewModel::class.java]
        characterDataAdapter = CharacterListAdapter(this)
        binding.charList.adapter = characterDataAdapter
        // observer characters list and populate in recycler view
        mainViewModel.charactersList.observe(viewLifecycleOwner, {
            when (it.status) {
                Status.SUCCESS -> {
                    binding.progressBar.visibility = View.GONE
                    it.data?.let { characters ->
                        characterDataAdapter.setItems(characters)
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
        mainViewModel.getCharactersList()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity?.applicationContext as MyApplication).appComponent.inject(this)
    }

    override fun onItemClick(character: Characters) {
        val action =
            CharacterListFragmentDirections.actionCharacterListFragmentToCharacterDetailsFragment(
                character.id
            )
        findNavController().navigate(action)
    }
}