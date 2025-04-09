package org.mathieu.cleanrmapi.domain.location

import org.mathieu.cleanrmapi.domain.location.models.Location
import org.mathieu.cleanrmapi.domain.location.models.LocationPreview

interface LocationRepository {

    /**
     * Fetches a location based on the provided ID.
     *
     * @param id The unique identifier of the location to be fetched.
     */
    suspend fun getLocation(id: Int): LocationPreview
}