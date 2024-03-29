package app.edgar.pelis.ui.home

import android.app.SearchManager
import android.content.Context
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.viewpager2.widget.ViewPager2
import app.edgar.pelis.R
import app.edgar.pelis.adapter.HomeFragmentStateAdapter
import app.edgar.pelis.model.MovieType
import kotlinx.android.synthetic.main.tmdb_activity_main.*
import kotlinx.android.synthetic.main.tmdb_toolbar.*



class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        super.onCreate(savedInstanceState)
        setContentView(R.layout.tmdb_activity_main)
        setupToolbar()
        setupUI()
    }

    override fun onBackPressed() {
        if(viewPager.currentItem == 0) {
            super.onBackPressed()
        } else {
            viewPager.currentItem = viewPager.currentItem - 1
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.tmdb_menu_home, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        (menu.findItem(R.id.action_search).actionView as SearchView).apply {
            setSearchableInfo(searchManager.getSearchableInfo(componentName))
            maxWidth = Integer.MAX_VALUE
        }

        return true
    }

    private fun setupToolbar(){
        setSupportActionBar(toolbar as Toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
    }

    private fun setupUI(){
        val adapter = HomeFragmentStateAdapter(this)
        MovieType.values().forEach {
            adapter.addFragment(HomeFragmentSlide(it))
        }

        viewPager.adapter = adapter
        viewPager.setPageTransformer(ZoomOutPageTransformer())
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                tabLayout.setScrollPosition(position, 0F, true)
            }
        })

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                tab.text = adapter.getFragmentTitle(position)
                tab.setIcon(adapter.getFragmentIcon(position))
        }.attach()
    }
}