package com.example.persistence

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.persistence.dao.AdviceDao
import com.example.persistence.data.AdviceEntity
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import junit.framework.Assert.assertTrue
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentCaptor

@RunWith(AndroidJUnit4::class)
class AdviceDaoTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: AppDatabase
    private lateinit var adviceDao: AdviceDao

    @Before
    fun initDb() {
        database = Room.inMemoryDatabaseBuilder(
                InstrumentationRegistry.getInstrumentation().context,
                AppDatabase::class.java
        ).build()

        adviceDao = database.adviceDao
    }

    @After
    fun closeDb() {
        database.close()
    }

    @Test
    fun getAllAdvices_ReturnsEmptyList() {
        val testObserver: Observer<List<AdviceEntity>> = mock()
        adviceDao.getAdvices().observeForever(testObserver)
        verify(testObserver).onChanged(emptyList())
    }

    @Test
    fun saveAdvice_SavesData() {
        val advice1 = AdviceEntity(1, "test_1")
        val advice2 = AdviceEntity(2, "test_2")
        adviceDao.insertAdvice(advice1, advice2)

        val testObserver: Observer<List<AdviceEntity>> = mock()
        adviceDao.getAdvices().observeForever(testObserver)

        val listClass = ArrayList::class.java as Class<ArrayList<AdviceEntity>>
        val argumentCaptor = ArgumentCaptor.forClass(listClass)
        verify(testObserver).onChanged(argumentCaptor.capture())
        assertTrue(argumentCaptor.value.size > 0)
    }

    @Test
    fun getAllAdvices_RetrievesData() {
        val advice1 = AdviceEntity(1, "test_1")
        val advice2 = AdviceEntity(2, "test_2")
        adviceDao.insertAdvice(advice1, advice2)

        val testObserver: Observer<List<AdviceEntity>> = mock()
        adviceDao.getAdvices().observeForever(testObserver)

        val listClass = ArrayList::class.java as Class<ArrayList<AdviceEntity>>
        val argumentCaptor = ArgumentCaptor.forClass(listClass)
        verify(testObserver).onChanged(argumentCaptor.capture())
        val capturedArgument = argumentCaptor.value
        assertTrue(capturedArgument.containsAll(listOf(advice1, advice2)))
    }

    @Test
    fun findById_RetrievesCorrectData() {
        val advice1 = AdviceEntity(1, "test_1")
        val advice2 = AdviceEntity(2, "test_2")
        adviceDao.insertAdvice(advice1, advice2)

        val testObserver: Observer<AdviceEntity> = mock()
        adviceDao.getAdviceById(advice2.id).observeForever(testObserver)
        verify(testObserver).onChanged(advice2)
    }

}