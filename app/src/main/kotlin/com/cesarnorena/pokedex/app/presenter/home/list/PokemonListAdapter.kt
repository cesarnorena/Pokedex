package com.cesarnorena.pokedex.app.presenter.home.list

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.cesarnorena.pokedex.R
import com.cesarnorena.pokedex.app.libraries.extensions.formattedId
import com.cesarnorena.pokedex.data.model.PokedexEntry

class PokemonListAdapter(
    context: Context,
    private val pokemonList: List<PokedexEntry>,
    private val onItemClick: ((PokedexEntry) -> Any)
) : RecyclerView.Adapter<PokemonListAdapter.ViewHolder>() {

    private val numberFormat = context.resources.getString(R.string.pokemon_number)
    private val imageLoader = Glide.with(context)

    override fun getItemCount(): Int = pokemonList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_pokemon_list, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val pokemon = pokemonList[position]

        with(holder) {
            imageLoader.load(pokemon.imageUrl).into(imageView)
            numberView.text = getFormattedNumber(pokemon.number)
            nameView.text = pokemon.specie.name.capitalize()
            containerView.setOnClickListener { onItemClick.invoke(pokemon) }
        }
    }

    private fun getFormattedNumber(number: Int): String {
        return String.format(numberFormat, number.formattedId())
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val containerView: View = view
        val imageView: ImageView = view.findViewById(R.id.pokemonImage)
        val numberView: TextView = view.findViewById(R.id.pokemonNumber)
        val nameView: TextView = view.findViewById(R.id.pokemonName)
    }
}
