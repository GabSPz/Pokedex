package com.example.pokedex




// ***** Now, i cant test the locations :( *****




import com.example.pokedex.data.maps.PermissionsManager
import com.example.pokedex.data.repository.LocationRepository
import com.example.pokedex.domain.ExplorationUseCase
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class ExplorationUseCaseTest {
/*
    @RelaxedMockK
    private lateinit var explorationUseCase: ExplorationUseCase
    private lateinit var locationRepository: LocationRepository
    private lateinit var permissionsManager: PermissionsManager

    @Before
    fun onBefore(){
        MockKAnnotations.init(this)

    }

    @Test
    fun `when the user entry on the exploration screen, the system show the location permission and all backend`() = runBlocking {
        //Give
        coEvery { explorationUseCase = ExplorationUseCase(any()) }
        coEvery { locationRepository = LocationRepository(any()) }
        coEvery { permissionsManager = PermissionsManager(any(), any()) }

        //When
        explorationUseCase.onViewCreated()

    //Then
        coVerify { explorationUseCase.onMapLoaded() }
    }

 */
}