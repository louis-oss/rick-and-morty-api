package org.mathieu.cleanrmapi.ui.screens.locationdetails

import org.koin.core.component.inject
import org.mathieu.cleanrmapi.domain.character.CharacterRepository
import org.mathieu.cleanrmapi.domain.character.models.Character
import org.mathieu.cleanrmapi.domain.location.LocationRepository
import org.mathieu.cleanrmapi.ui.core.Destination
import org.mathieu.cleanrmapi.ui.core.ViewModel

sealed interface LocationDetailsAction {
    data class SelectedCharacter(val character: Character) : LocationDetailsAction
}

class LocationDetailsViewModel : ViewModel<LocationDetailsState>(LocationDetailsState.Loading) {

    private val locationRepo: LocationRepository by inject()
    private val characterRepo: CharacterRepository by inject()

    fun init(locationId: Int) {
        fetchData(
            source = {
                val location = locationRepo.getLocation(locationId)
                val characters = characterRepo.getCharactersInLocation(locationId)
                location to characters
            }
        ) {
            onSuccess { (location, characters) ->
                updateState {
                    LocationDetailsState.Loaded(
                        name = location.name,
                        origin = location.origin,
                        characters = characters
                    )
                }
            }

            onFailure {
                updateState {
                    LocationDetailsState.Error(it.message ?: it.toString())
                }
            }
        }
    }

    fun handleAction(action: LocationDetailsAction) {
        when (action) {
            is LocationDetailsAction.SelectedCharacter ->
                sendEvent(Destination.CharacterDetails(action.character.id.toString()))
        }
    }
}

sealed interface LocationDetailsState {

    object Loading : LocationDetailsState

    data class Error(val message: String) : LocationDetailsState

    data class Loaded(
        val name: String,
        val origin: String,
        val characters: List<Character>
    ) : LocationDetailsState
}