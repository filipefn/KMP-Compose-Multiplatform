package data

import com.aula6.cache.AppDatabase
import network.model.RocketLaunchDTO

class Database(databaseDriverFactory: DatabaseDriverFactory) {

    private val database = AppDatabase(databaseDriverFactory.createDriver())
    private val dbQuery = database.appDatabaseQueries

    fun getAllLaunches(): List<RocketLaunchDTO> {
        return dbQuery.selectAllLaunchesInfo().executeAsList().map {
            RocketLaunchDTO(
                flightNumber = it.flightNumber.toInt(),
                missionName = it.missionName,
                details = it.details,
                launchSuccess = it.launchSuccess == 1L,
            )
        }
    }

    suspend fun clearCache() {
        dbQuery.removeAllLaunches()
    }

    suspend fun insertLaunches(launches: List<RocketLaunchDTO>) {
        launches.forEach { item ->
            item.flightNumber?.let {
                dbQuery.insertLaunch(
                    flightNumber = it.toLong(),
                    missionName = item.missionName.orEmpty(),
                    details = item.details,
                    launchSuccess = if (item.launchSuccess == true) 1 else 0
                )
            }
        }
    }
}