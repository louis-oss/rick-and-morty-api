package org.mathieu.cleanrmapi.data.repositories

import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.java.KoinJavaComponent.inject
import org.mathieu.cleanrmapi.data.local.LocationDAO
import org.mathieu.cleanrmapi.data.local.objects.LocationObject
import org.mathieu.cleanrmapi.data.local.objects.toDBObject
import org.mathieu.cleanrmapi.data.local.objects.toModel
import org.mathieu.cleanrmapi.data.remote.LocationApi
import org.mathieu.cleanrmapi.domain.location.LocationRepository
import org.mathieu.cleanrmapi.domain.location.models.Location
import org.mathieu.cleanrmapi.domain.location.models.LocationPreview

internal class LocationRepositoryImpl() : LocationRepository {

    private val api: LocationApi by inject(
        clazz = TODO(),
        qualifier = TODO(),
        parameters = TODO()
    )
    private val dao: LocationDAO by inject(
        clazz = TODO(),
        qualifier = TODO(),
        parameters = TODO()
    )

    override suspend fun getLocation(id: Int): LocationPreview {
        val local = GetLocationIfExists(id)
        return local.toModel()
    }
}

private object GetLocationIfExists : KoinComponent {
    private val api: LocationApi by inject()
    private val dao: LocationDAO by inject()

    suspend operator fun invoke(id: Int) =
        tryToGetLocally(id).fetchRemotelyIfNeeded(id).throwIfMissing()

    private suspend fun tryToGetLocally(id: Int) = dao.getLocation(id)

    private suspend fun LocationObject?.fetchRemotelyIfNeeded(id: Int): LocationObject? {
        if (this != null) return this
        return api.getLocation(id)?.let { response ->
            val obj = LocationPreview(
                id = response.id,
                name = response.name,
                origin = response.dimension // on mappe dimension comme "origin"
            ).toDBObject()
            dao.insert(obj)
            obj
        }
    }

    private fun LocationObject?.throwIfMissing(): LocationObject {
        return this ?: throw Exception("Location not found locally or remotely.")
    }
}