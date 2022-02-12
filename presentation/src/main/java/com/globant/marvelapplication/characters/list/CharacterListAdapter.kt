package com.marvel.marvelapplication.characters.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.marvel.domain.model.Characters
import com.marvel.marvelapplication.R
import com.marvel.marvelapplication.databinding.ItemCharacterListBinding

/* CharacterListAdapter used to populate characters list on main fragment*/
class CharacterListAdapter(private val itemClickListener: ItemClickListener) :
    RecyclerView.Adapter<CharacterListAdapter.MyViewHolder>() {

    private var charactersList = listOf<Characters>()

    class MyViewHolder(private var binding: ItemCharacterListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(character: Characters, mItemClickListener: ItemClickListener) {
            binding.character = character
            binding.characterCard.setOnClickListener { mItemClickListener.onItemClick(character) }
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): MyViewHolder {
        // Create a new view, which defines the UI of the list item
        val binding: ItemCharacterListBinding = DataBindingUtil.inflate(
            LayoutInflater.from(viewGroup.context),
            R.layout.item_character_list,
            viewGroup,
            false
        )
        return MyViewHolder(binding)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: MyViewHolder, position: Int) {
        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.bind(charactersList[position], itemClickListener)
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = charactersList.size

    fun setItems(items: List<Characters>) {
        this.charactersList = items
        notifyDataSetChanged()
    }

}
