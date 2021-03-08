package com.shabinder.common.di

import co.touchlab.kermit.Kermit
import com.shabinder.common.database.TokenDBQueries
import com.shabinder.common.di.spotify.authenticateSpotify
import com.shabinder.common.models.spotify.TokenData
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock

class TokenStore(
    private val dir: Dir,
    private val logger: Kermit,
) {
    private val db: TokenDBQueries?
        get() = dir.db?.tokenDBQueries

    private fun save(token: TokenData){
        if(!token.access_token.isNullOrBlank() && token.expiry != null)
            db?.add(token.access_token!!, token.expiry!! + Clock.System.now().epochSeconds)
    }

    suspend fun getToken(): TokenData? {
        var token: TokenData? = db?.select()?.executeAsOneOrNull()?.let {
            TokenData(it.accessToken,null,it.expiry)
        }
        logger.d{"System Time:${Clock.System.now().epochSeconds} , Token Expiry:${token?.expiry}"}
        if(Clock.System.now().epochSeconds > token?.expiry ?:0 || token == null){
            logger.d{"Requesting New Token"}
            token = authenticateSpotify()
            GlobalScope.launch { token?.access_token?.let { save(token) } }
        }
        return token
    }
}