package org.mathieu.cleanrmapi.data.local.objects

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.mathieu.cleanrmapi.data.local.RMDatabase
import org.mathieu.cleanrmapi.domain.location.models.LocationPreview

@Entity(tableName = RMDatabase.LOCATION_TABLE)
class LocationObject (@PrimaryKey val id: Int,
                      val name: String,
                      val origin: String)

internal fun LocationObject.toModel() = LocationPreview(
    id = id,
    name = name,
    origin = origin
)


internal fun LocationPreview.toDBObject() = LocationObject(
    id = id,
    name = name,
    origin = origin
)