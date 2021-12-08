package com.nsu.focusstartproject.data.preferences

import com.nsu.focusstartproject.TestEntities.IS_DARK_MODE
import com.nsu.focusstartproject.TestEntities.IS_FIRST_ENTER
import com.nsu.focusstartproject.TestEntities.TOKEN
import io.mockk.*
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class PreferencesRepositoryImplTest {

    private val preferencesDataSource: PreferencesDataSource = mockk {
        coEvery { getToken() } returns flowOf(TOKEN)
        coEvery { setToken(TOKEN) } just runs
        coEvery { deleteToken() } just runs
        coEvery { isFirstEnter() } returns flowOf(IS_FIRST_ENTER)
        coEvery { setFirstEnter(IS_FIRST_ENTER) } just runs
        coEvery { isDarkMode() } returns flowOf(IS_DARK_MODE)
        coEvery { setDarkMode(IS_DARK_MODE) } just runs
    }

    private val preferencesRepository = PreferencesRepositoryImpl(
        preferencesDataSource
    )

    @Test
    fun `get token EXPECT call get token from datasource`() {
        runBlocking {
            val actual = preferencesRepository.getToken()
            val expected = TOKEN

            assertEquals(actual, expected)
        }
    }

    @Test
    fun `set token EXPECT call set token from datasource`() {
        runBlocking {
            preferencesRepository.setToken(TOKEN)

            coVerify {
                preferencesDataSource.setToken(TOKEN)
            }
        }
    }

    @Test
    fun `delete token EXPECT delete token from datasource`() {
        runBlocking {
            preferencesRepository.deleteToken()

            coVerify {
                preferencesDataSource.deleteToken()
            }
        }
    }

    @Test
    fun `is firstEnter EXPECT call is firstEnter from datasource`() {
        runBlocking {
            val actual = preferencesRepository.isFirstEnter()
            val expected = IS_FIRST_ENTER

            assertEquals(actual, expected)
        }
    }

    @Test
    fun `set firstEnter EXPECT call set firstEnter from datasource`() {
        runBlocking {
            preferencesRepository.setFirstEnter(IS_FIRST_ENTER)

            coVerify {
                preferencesDataSource.setFirstEnter(IS_FIRST_ENTER)
            }
        }
    }

    @Test
    fun `is darkMode EXPECT call is darkMode from datasource`() {
        runBlocking {
            val actual = preferencesRepository.isDarkMode()
            val expected = IS_DARK_MODE

            assertEquals(actual, expected)
        }
    }

    @Test
    fun `set darkMode EXPECT call set darkMode from datasource`() {
        runBlocking {
            preferencesRepository.setDarkMode(IS_DARK_MODE)

            coVerify {
                preferencesDataSource.setDarkMode(IS_DARK_MODE)
            }
        }
    }
}