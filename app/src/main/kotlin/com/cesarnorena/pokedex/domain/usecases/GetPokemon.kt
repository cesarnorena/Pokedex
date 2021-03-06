package com.cesarnorena.pokedex.domain.usecases

import com.cesarnorena.pokedex.data.model.Pokemon
import com.cesarnorena.pokedex.domain.repository.RemoteRepository
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class GetPokemon @Inject constructor(
    private val remoteRepository: RemoteRepository
) {
    operator fun invoke(id: Int): Single<Pokemon> {
        return remoteRepository.getPokemon(id)
            .map { pokemon ->
                pokemon.copy(
                    id = pokemon.id,
                    name = pokemon.name,
                    typeSlots = pokemon.typeSlots.sortedBy { it.position }
                )
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}
