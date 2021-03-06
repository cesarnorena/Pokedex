package com.cesarnorena.pokedex.data.repository

import com.cesarnorena.pokedex.data.model.PokedexEntry
import com.cesarnorena.pokedex.data.model.Pokemon
import com.cesarnorena.pokedex.data.model.Specie
import com.cesarnorena.pokedex.data.repository.local.PokemonDao
import com.cesarnorena.pokedex.data.repository.local.PokemonEntity
import com.cesarnorena.pokedex.domain.repository.LocalRepository
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

class DefaultLocalRepository @Inject constructor(
    private val pokemonDao: PokemonDao
) : LocalRepository {

    override fun savePokedex(list: List<PokedexEntry>): Completable {
        return Completable.fromAction {
            val pokemonList = list.map { PokemonEntity(it.number, it.specie.name) }
            pokemonDao.saveAll(pokemonList)
        }
    }

    override fun getPokedex(): Single<List<PokedexEntry>> {
        return pokemonDao.getAll().map { list ->
            list.map { PokedexEntry(it.id, Specie(it.name)) }
        }
    }

    override fun getPokedexSize(): Int {
        return pokemonDao.getSize()
    }

    override fun savePokemon(pokemon: Pokemon) {
        pokemonDao.save(PokemonEntity(pokemon.id, pokemon.name))
    }

    override fun getPokemon(id: Int): Single<Pokemon> {
        return pokemonDao.findById(id).map {
            Pokemon(it.id, it.name, emptyList())
        }
    }
}
