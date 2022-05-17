package com.eru.service.android.cat

interface ICatManager {
    fun getName(): String
    fun getCatCurrentGPS(): Pair<Int, Int>
}

class CatManager(
    private val gpsManager: IGPSManager
) : ICatManager {

    companion object {
        private const val NAME = "TOMMY"
    }

    override fun getName(): String {
        return NAME
    }

    override fun getCatCurrentGPS(): Pair<Int, Int> {
        return gpsManager.getCatCurrentGPS()
    }
}