package com.dhanajitkapali.spacex.shared


import com.dhanajitkapali.spacex.shared.cache.Database
import com.dhanajitkapali.spacex.shared.cache.DatabaseDriverFactory
import com.dhanajitkapali.spacex.shared.entity.RocketLaunch
import com.dhanajitkapali.spacex.shared.network.SpaceXApi

class SpaceXSDK (databaseDriverFactory: DatabaseDriverFactory) {
    private val database = Database(databaseDriverFactory)
    private val api = SpaceXApi()

    @Throws(Exception::class)
    suspend fun getLaunches(forceReload: Boolean): List<RocketLaunch> {
        val cachedLaunches = database.getAllLaunches()
        return if (cachedLaunches.isNotEmpty() && !forceReload) {
            cachedLaunches
        } else {
            api.getAllLaunches().also {
                database.clearDatabase()
                database.createLaunches(it)
            }
        }
    }
}