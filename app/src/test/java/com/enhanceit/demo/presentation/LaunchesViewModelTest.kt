package com.enhanceit.demo.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.enhanceit.demo.data.model.Launches
import com.enhanceit.demo.data.model.Links
import com.enhanceit.demo.data.model.Patch
import com.enhanceit.demo.domain.LaunchesRepository
import com.enhanceit.demo.utils.Resource
import com.enhanceit.demo.utils.TestCoroutineRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class LaunchesViewModelTest {
    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @Mock
    private lateinit var apiLaunchesObserver: Observer<Resource<List<Launches>>>

    @Mock
    private lateinit var launchesRepository: LaunchesRepository

    private lateinit var launchesViewModel: LaunchesViewModel

    @Before
    fun setUp() {
        launchesViewModel = LaunchesViewModel(launchesRepository)
    }

    @Test
    fun `Test fetch launches successful and list is empty`() {
        testCoroutineRule.runBlockingTest {
            Mockito.doReturn(emptyList<Launches>())
                .`when`(launchesRepository)
                .getLaunches()

            launchesViewModel.launches.observeForever(apiLaunchesObserver)
            launchesViewModel.fetchLaunches()
            verify(launchesRepository).getLaunches()
            verify(apiLaunchesObserver).onChanged(Resource.success(emptyList()))
            launchesViewModel.launches.removeObserver(apiLaunchesObserver)

        }

    }

    @Test
    fun `Test fetch launches successful and list is not empty`() {
        testCoroutineRule.runBlockingTest {
            doReturn(getLaunchesResponse())
                .`when`(launchesRepository)
                .getLaunches()

            launchesViewModel.launches.observeForever(apiLaunchesObserver)
            launchesViewModel.fetchLaunches()
            verify(launchesRepository).getLaunches()
            verify(apiLaunchesObserver).onChanged(Resource.success(getLaunchesResponse()))
            launchesViewModel.launches.removeObserver(apiLaunchesObserver)

        }
    }

    @Test
    fun `test server error with message`() {
        testCoroutineRule.runBlockingTest {
            val errorMessage = "Getting error while loading launches"
            doThrow(java.lang.RuntimeException(errorMessage))
                .`when`(launchesRepository)
                .getLaunches()
            launchesViewModel.launches.observeForever(apiLaunchesObserver)
            launchesViewModel.fetchLaunches()
            verify(launchesRepository).getLaunches()
            verify(apiLaunchesObserver).onChanged(
                Resource.error(
                    RuntimeException(errorMessage).toString(),
                    null
                )
            )
            launchesViewModel.launches.removeObserver(apiLaunchesObserver)
        }
    }

    private fun getLaunchesResponse() =
        listOf(
            Launches(
                links = Links(patch = Patch(small = "", large = "")),
                static_fire_date_unix = 0,
                staticFireDateUtc = "",
                success = false,
                name = "Test1"

            ),
            Launches(
                links = Links(patch = Patch(small = "", large = "")),
                static_fire_date_unix = 0,
                staticFireDateUtc = "",
                success = false,
                name = "Test2"

            )
        )
}