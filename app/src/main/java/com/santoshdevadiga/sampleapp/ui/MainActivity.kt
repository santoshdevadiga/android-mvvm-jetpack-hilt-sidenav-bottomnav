package com.santoshdevadiga.sampleapp.ui

import android.annotation.SuppressLint
import android.app.SearchManager
import android.database.Cursor
import android.database.MatrixCursor
import android.os.Bundle
import android.provider.BaseColumns
import android.util.Log
import android.view.Menu
import android.view.View
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appsearch.app.SearchResult
import androidx.cursoradapter.widget.CursorAdapter
import androidx.cursoradapter.widget.SimpleCursorAdapter
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import com.santoshdevadiga.sampleapp.R
import com.santoshdevadiga.sampleapp.databinding.ActivityMainBinding
import com.santoshdevadiga.sampleapp.repository.local.AppData
import com.santoshdevadiga.sampleapp.utils.AppSearchData
import com.santoshdevadiga.sampleapp.utils.AppSearchManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.UUID

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var searchView: SearchView
    private lateinit var mainActivityViewModel: MainActivityViewModel

    private lateinit var appSearchManager: AppSearchManager

    val suggestions = listOf("Fragment 1", "Fragment 2", "Fragment 3", "Fragment 4")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        binding.appBarMain.bottomNavBar.background = null

        setContentView(binding.root)
        setSupportActionBar(binding.appBarMain.toolbar)

        appSearchManager = AppSearchManager(application, lifecycleScope)

        val drawerLayout: DrawerLayout = binding.drawerLayout
        navController = findNavController(R.id.nav_host_fragment_content_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        //Pass the ids of fragments from nav_graph which you d'ont want to show back button in toolbar
        appBarConfiguration = AppBarConfiguration(
            setOf(
                /*R.id.nav_home,
                R.id.nav_gallery,
                R.id.nav_slideshow,*/
                R.id.dashboardFragment,
                R.id.listFragment,
                R.id.settingFragment,
                R.id.splashFragment,
                R.id.loginFragment,
                R.id.registrationFragment
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        visibilityNavElements(navController)

        binding.appBarMain.fab.setOnClickListener {
            Log.i("TAG", "Fab Button Called")
            navController.navigate(R.id.bottomSheetDialogFragment)
            /*Snackbar.make(it, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()*/
        }

        //ViewModel
        mainActivityViewModel = ViewModelProvider(
            this
        )[MainActivityViewModel::class.java]

        val addDBData=AppData(12,false,false,"CA78")
        mainActivityViewModel.insertAppData(addDBData)

        mainActivityViewModel.getAppData().observe(this, Observer {
            Log.i("TAG", "onCreate: ${it?.userID}")
        })

        mainActivityViewModel.userPost.observe(this, Observer {
            Log.i("TAG", "User Post Data :: $it")
        })

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        searchView = menu.findItem(R.id.app_bar_search).actionView as SearchView
        val from = arrayOf(SearchManager.SUGGEST_COLUMN_TEXT_1)
        val to = intArrayOf(R.id.item_label)
        val cursorAdapter = SimpleCursorAdapter(
            applicationContext,
            R.layout.search_item,
            null,
            from,
            to,
            CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER
        )

        searchView.suggestionsAdapter = cursorAdapter

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(query: String?): Boolean {
                val cursor =
                    MatrixCursor(arrayOf(BaseColumns._ID, SearchManager.SUGGEST_COLUMN_TEXT_1))
                query?.let {
                    suggestions.forEachIndexed { index, suggestion ->
                        if (suggestion.contains(query, true))
                            cursor.addRow(arrayOf(index, suggestion))
                    }
                }

                cursorAdapter.changeCursor(cursor)
                return true
            }
        })

        searchView.setOnSuggestionListener(object : SearchView.OnSuggestionListener {
            override fun onSuggestionSelect(position: Int): Boolean {
                return false
            }

            @SuppressLint("Range")
            override fun onSuggestionClick(position: Int): Boolean {

                val cursor = searchView.suggestionsAdapter.getItem(position) as Cursor
                val selection =
                    cursor.getString(cursor.getColumnIndex(SearchManager.SUGGEST_COLUMN_TEXT_1))
                searchView.setQuery(selection, false)

                // Do something with selection
                return true
            }

        })
        //initQueryListener()
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        Log.i("TAG", "onSupportNavigateUp")
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    private fun visibilityNavElements(navController: NavController) {

        //Listen for the change in fragment (navigation) and hide or show drawer or bottom navigation accordingly if required
        //Modify this according to your need
        //If you want you can implement logic to deselect(styling) the bottom navigation menu item when drawer item selected using listener

        navController.addOnDestinationChangedListener { _, destination, _ ->
            Log.i("TAG", "Destination ID : ${destination.id}")
            Log.i("TAG", "Destination ID : ${destination.label}")
            /*when (destination.id) {
                R.id.dashboardFragment -> showBothNavigation()
                R.id.listFragment -> showBothNavigation()
                R.id.settingFragment -> showBothNavigation()
                R.id.splashFragment -> hideBothNavigation()
                R.id.loginFragment -> hideBothNavigation()
                R.id.registrationFragment -> hideBothNavigation()
                else -> hideBottomNavigation()
            }*/

            when (destination.id) {
                /*R.id.dashboardFragment -> showBothNavigation()
                R.id.listFragment -> showBothNavigation()
                R.id.settingFragment -> showBothNavigation()*/
                R.id.splashFragment -> hideBothNavigation()
                R.id.loginFragment -> hideBothNavigation()
                R.id.registrationFragment -> hideBothNavigation()
                else -> showBothNavigation()
            }


            //To manage continouse selection of bottom navigation bar menu selection on back press.
            if (destination.label?.toString().equals(getString(R.string.label_dashboard))) {
                navController.popBackStack(R.id.listFragment, false)
                navController.popBackStack(R.id.settingFragment, false)
                navController.popBackStack(R.id.dashboardFragment, false)
                Log.i("TAG", "PopBackStack Called")
            }

            if (destination.id == R.id.dashboardFragment) {
                binding.appBarMain.fab.visibility = View.VISIBLE
            } else {
                binding.appBarMain.fab.visibility = View.GONE
            }
        }

    }

    private fun hideBothNavigation() { //Hide both drawer and bottom navigation bar
        binding.appBarMain.bottomAppBar.visibility = View.GONE
        binding.navView.visibility = View.GONE
        binding.appBarMain.toolbar.visibility = View.GONE
        binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED) //To lock navigation drawer so that it don't respond to swipe gesture
    }

    private fun hideBottomNavigation() { //Hide bottom navigation
        binding.appBarMain.bottomAppBar.visibility = View.GONE
        binding.navView.visibility = View.VISIBLE
        binding.appBarMain.toolbar.visibility = View.VISIBLE
        binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED) //To unlock navigation drawer
        binding.navView.setupWithNavController(navController) //Setup Drawer navigation with navController
    }

    private fun showBothNavigation() {
        binding.appBarMain.bottomAppBar.visibility = View.VISIBLE
        binding.navView.visibility = View.VISIBLE
        binding.appBarMain.toolbar.visibility = View.VISIBLE
        binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
        setupNavControl() //To configure navController with drawer and bottom navigation
    }

    private fun setupNavControl() {
        binding.appBarMain.bottomNavBar.setupWithNavController(navController) //Setup Drawer navigation with navController
        binding.navView.setupWithNavController(navController) //Setup Bottom navigation with navController
    }


    //jetpack AppSearch with local storage
    /** Initializes listeners for query input. */
    private fun initQueryListener() {
        searchView.queryHint = getString(R.string.search_bar_hint)
        addSearchableText()
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                Log.i("TAG", "Search Query Text : $query")
                queryNotes(query)
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                // This resets the notes list to display all notes if the query is
                // cleared.
                if (newText.isEmpty()) queryNotes()
                return false
            }
        })
    }

    private fun addSearchableText() {
        addAppSearchString("Sample Text")
        addAppSearchString("Text")
        addAppSearchString("Hello")
        addAppSearchString("World")
    }

    private fun addAppSearchString(text: String) {
        val id = UUID.randomUUID().toString()
        val note = AppSearchData(id = id, text = text)

        lifecycleScope.launch {
            val result = appSearchManager.addAppSearchData(note)
            if (!result.isSuccess) {
                Log.i("TAG", "Failed to add note with id: $id and text: $text")
            }
            queryNotes()
        }

    }


    fun queryNotes(query: String = ""): List<SearchResult> {
        var queryResult: List<SearchResult> = emptyList()
        lifecycleScope.launch {
            queryResult = appSearchManager.queryLatestAppSearchDatas(query)
            //_noteLiveData.postValue(resultNotes)
            if (queryResult.isNotEmpty()) {
                Log.i(
                    "TAG",
                    "Query Search Result : ${
                        queryResult[0].genericDocument.toDocumentClass(AppSearchData::class.java)
                    }"
                )
            }
        }
        return queryResult
    }

    fun removeAppSearchData(namespace: String, id: String) {
        lifecycleScope.launch {
            val result = appSearchManager.removeAppSearchData(namespace, id)
            if (!result.isSuccess) {
                Log.i(
                    "TAG",
                    "Failed to remove note in namespace: $namespace with id: $id"
                )
            }
            queryNotes()
        }
    }
}