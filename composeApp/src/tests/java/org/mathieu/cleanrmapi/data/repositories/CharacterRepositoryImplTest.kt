package org.mathieu.cleanrmapi.data.repositories

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mathieu.cleanrmapi.data.local.CharacterDAO
import org.mathieu.cleanrmapi.data.local.objects.CharacterObject
import org.mathieu.cleanrmapi.domain.character.models.Character

class CharacterRepositoryImplTest {

    private lateinit var repository: CharacterRepositoryImpl
    private val mockApi = mockk<org.mathieu.cleanrmapi.data.remote.CharacterApi>(relaxed = true)
    private val mockDao = mockk<CharacterDAO>()

    @Before
    fun setUp() {
        repository = CharacterRepositoryImpl(mockApi).apply {
            // injecter manuellement le DAO si besoin
            val repo = this as org.koin.core.component.KoinComponent
            repo::class.java.getDeclaredField("characterDao").apply {
                isAccessible = true
                set(repo, mockDao)
            }
        }
    }

    @Test
    fun `getCharactersInLocation should return mapped characters from DAO`() = runBlocking {
        // GIVEN
        val locationId = 1
        val daoObjects = listOf(
            CharacterObject(
                id = 1,
                name = "Rick",
                status = "Alive",
                species = "Human",
                type = "",
                gender = "Male",
                originName = "Earth",
                originId = 1,
                locationName = "Earth",
                locationId = locationId,
                image = "url",
                episodesIds = "1,2",
                created = "today"
            )
        )

        coEvery { mockDao.getCharactersInLocation(locationId) } returns daoObjects

        // WHEN
        val result = repository.getCharactersInLocation(locationId)

        // THEN
        coVerify { mockDao.getCharactersInLocation(locationId) }
        assertEquals(1, result.size)
        assertEquals("Rick", result.first().name)
    }
}