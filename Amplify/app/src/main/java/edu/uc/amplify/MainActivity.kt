package edu.uc.amplify

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.spotify.android.appremote.api.ConnectionParams
import com.spotify.android.appremote.api.Connector
import com.spotify.android.appremote.api.SpotifyAppRemote
import edu.uc.amplify.dao.SpotifyFunctionality
import edu.uc.amplify.dto.AppConstants
import edu.uc.amplify.ui.main.MainFragment


class MainActivity : AppCompatActivity() {

    private var mSpotifyAppRemote: SpotifyAppRemote? = null
    val spotifyFunctionality = SpotifyFunctionality()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.newInstance())
                .commitNow()
        }
    }

    override fun onStart() {
        super.onStart()
        val connectionParams: ConnectionParams = ConnectionParams.Builder(AppConstants.APP_CLIENT_ID)
            .setRedirectUri(AppConstants.APP_REDIRECT_URI)
            .showAuthView(true)
            .build()

        SpotifyAppRemote.connect(this, connectionParams,
            object : Connector.ConnectionListener {
                override fun onConnected(spotifyAppRemote: SpotifyAppRemote) {
                    mSpotifyAppRemote = spotifyAppRemote
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

    override fun onStop() {
        super.onStop()
        SpotifyAppRemote.disconnect(mSpotifyAppRemote);
    }

    private fun connected() {
        // below is just an example, but a starting point for playing a playlist
        mSpotifyAppRemote?.getPlayerApi()?.play("spotify:playlist:37i9dQZF1DX2sUQwD7tbmL")


    }

    private fun logon() {
        /*var providers =_arrayListOf(
            AuthUI.IdpConfig.EmailBuilder().build
        )
        startActivityForResult(
            AuthUI.getInstance().createSignInIntentBuilder().setAvailableProviders(providers).build()
        )*/
    }
}
