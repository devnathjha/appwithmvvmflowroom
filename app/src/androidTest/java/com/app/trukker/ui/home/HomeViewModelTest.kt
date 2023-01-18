package com.app.trukker.ui.home

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.app.trukker.MainCoroutineRule
import com.app.trukker.data.model.AssociatedDrug
import com.app.trukker.data.repository.HomeRepository
import com.app.trukker.utils.State
import com.app.trukker.viewmodel.HomeViewModel
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.RuleChain
import javax.inject.Inject

@HiltAndroidTest
@ExperimentalCoroutinesApi
class HomeViewModelTest {

    private val hiltRule = HiltAndroidRule(this)
    private lateinit var homeViewModel: HomeViewModel

    @Inject
    lateinit var homeRepository: HomeRepository

    @get:Rule
    var coroutineRule = MainCoroutineRule()

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val rule: RuleChain = RuleChain
        .outerRule(hiltRule)
        .around(instantTaskExecutorRule)
        .around(coroutineRule)

    @Before
    fun setUp() {
        hiltRule.inject()
        homeViewModel = HomeViewModel(homeRepository, Application())
    }

    @Test
    fun getUsers_requestAllUsersFromRemoteDataSource() = runBlocking {
        // When users are requested from the users repository
        val usersList = mutableListOf<State<AssociatedDrug>>()

        val userFlow = homeRepository.getMedicines()

        //userFlow.toList(usersList)

        assert(usersList[0] is State.LoadingState)

        assert(usersList[1] is State.DataState)
    }
}
