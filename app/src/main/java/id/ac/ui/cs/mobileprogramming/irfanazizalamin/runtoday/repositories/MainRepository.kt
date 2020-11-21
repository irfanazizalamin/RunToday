package id.ac.ui.cs.mobileprogramming.irfanazizalamin.runtoday.repositories

import id.ac.ui.cs.mobileprogramming.irfanazizalamin.runtoday.db.Run
import id.ac.ui.cs.mobileprogramming.irfanazizalamin.runtoday.db.RunDAO
import javax.inject.Inject

class MainRepository @Inject constructor(
    val runDao: RunDAO
) {
    suspend fun insertRun(run: Run) = runDao.insertRun(run)

    suspend fun deleteRun(run: Run) = runDao.deleteRun(run)

    fun getAllRunsSortedByDate() = runDao.getAllRunsSortedByDate()

    fun getAllRunsSortedByAvgSpeed() = runDao.getAllRunsSortedByAvgSpeed()

    fun getAllRunsSortedByCaloriesBurned() = runDao.getAllRunsSortedByCaloriesBurned()

    fun getAllRunsSortedByDistance() = runDao.getAllRunsSortedByDistance()

    fun getAllRunsSortedByTimeInMillis() = runDao.getAllRunsSortedByTimeInMillis()

    fun getTotalAvgSpeed() = runDao.getTotalAvgSpeed()

    fun getTotalCaloriesBurned() = runDao.getTotalCaloriesBurned()

    fun getTotalDistanceMeters() = runDao.getTotalDistanceMeters()

    fun getTotalTimeInMillis() = runDao.getTotalTimeInMillis()
}