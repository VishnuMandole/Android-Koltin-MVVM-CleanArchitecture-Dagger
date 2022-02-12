package com.marvel.marvelapplication.characters

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.marvel.marvelapplication.R
import com.marvel.marvelapplication.util.MyApplication

/* MainActivity show all fragments */
class CharacterListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (applicationContext as MyApplication).appComponent.inject(this)
        setContentView(R.layout.character_list_activity)
    }
}