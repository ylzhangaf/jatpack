package com.ylozhangaf.ppjoke

import android.os.Bundle
import android.text.TextUtils
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.ylozhangaf.ppjoke.utils.NavGraphBuilder

class MainActivity : AppCompatActivity() {

    lateinit var navController : NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)


        navController = findNavController(R.id.nav_host_fragment)
        NavGraphBuilder.build(navController)


        navView.setOnNavigationItemSelectedListener { menuItem ->
            navController.navigate(menuItem.itemId)
            !TextUtils.isEmpty(menuItem.title)
        }

    }
}