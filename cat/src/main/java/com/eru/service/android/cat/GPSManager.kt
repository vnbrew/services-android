package com.eru.service.android.cat

import java.util.Random

interface IGPSManager {
    fun getCatCurrentGPS(): Pair<Int, Int>
}

class GPSManager : IGPSManager {

    private val random: Random = Random()
    private var posX: Int = 0
    private var posY: Int = 0

    override fun getCatCurrentGPS(): Pair<Int, Int> {
        posX = random.nextInt(100)
        posY = random.nextInt(100)
        return Pair(posX, posY)
    }
}