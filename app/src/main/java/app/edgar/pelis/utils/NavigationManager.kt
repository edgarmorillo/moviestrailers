package app.edgar.pelis.utils

import android.app.Activity
import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import app.edgar.pelis.model.Movie
import app.edgar.pelis.model.Video
import app.edgar.pelis.ui.landing.MovieLandingActivity
import app.edgar.pelis.ui.player.PlayerActivity
import app.edgar.pelis.ui.player.PlayerActivity.Companion.PARAM_URL

object NavigationManager {

    fun handle(context: Context?, item: Video){
        if(context != null && item.urlPlayback?.isNotBlank() == true) {
            val intent = Intent(context, PlayerActivity::class.java)
            intent.putExtra(PARAM_URL, item.urlPlayback)
            context.startActivity(intent)
        }
    }

    fun handle(context: Activity?, movie: Movie, activityOptions: ActivityOptions = ActivityOptions.makeSceneTransitionAnimation(context)){
        val intent = Intent(context, MovieLandingActivity::class.java)
        intent.putExtra(PARAM_CONTENT, movie)
        context?.startActivity(intent, activityOptions.toBundle())
    }

    const val PARAM_CONTENT = "param_content"
}