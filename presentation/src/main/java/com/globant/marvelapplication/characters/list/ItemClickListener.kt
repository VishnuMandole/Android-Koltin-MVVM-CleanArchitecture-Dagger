package com.marvel.marvelapplication.characters.list

import com.marvel.domain.model.Characters

/* Used for click characters card from DataAdapter*/
interface ItemClickListener {
    fun onItemClick(character: Characters)
}