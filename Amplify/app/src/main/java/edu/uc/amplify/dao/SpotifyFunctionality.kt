package edu.uc.amplify.dao

import android.content.Context
import android.util.Log
import com.spotify.android.appremote.api.ConnectionParams
import com.spotify.android.appremote.api.Connector
import com.spotify.android.appremote.api.SpotifyAppRemote
import edu.uc.amplify.dto.AppConstants

class SpotifyFunctionality {
    private var spotifyAppRemote: SpotifyAppRemote? = null

    fun connect(context: Context){
        val connectionParams = ConnectionParams.Builder(AppConstants.APP_CLIENT_ID).setRedirectUri(AppConstants.APP_REDIRECT_URI).showAuthView(true).build()

        SpotifyAppRemote.connect(context, connectionParams, object : Connector.ConnectionListener {
            override fun onConnected(appRemote: SpotifyAppRemote) {
                spotifyAppRemote = appRemote
                Log.d("MainActivity", "Connected! Yay!")
                // Now you can start interacting with App Remote
                connected()
            }

            override fun onFailure(throwable: Throwable) {
                Log.e("MainActivity", throwable.message, throwable)
                // Something went wrong when attempting to connect! Handle errors here
            }
        })
    }

    fun connected(){
        // Play a playlist
        spotifyAppRemote?.playerApi?.play("spotify:playlist:37i9dQZF1DX2sUQwD7tbmL")
    }
}