package com.ylozhangaf.ppjoke.utils

import android.content.ComponentName
import androidx.navigation.ActivityNavigator
import androidx.navigation.NavController

import androidx.navigation.NavGraph
import androidx.navigation.NavGraphNavigator
import androidx.navigation.fragment.FragmentNavigator
import com.ylozhangaf.libcommon.AppGlobals

class NavGraphBuilder {

    companion object {
        fun build(controller: NavController) {
            val provider = controller.navigatorProvider


            val fragmentNavigator = provider.getNavigator(FragmentNavigator::class.java)
            val activityNavigator = provider.getNavigator(ActivityNavigator::class.java)

            val navGraph = NavGraph(NavGraphNavigator(provider))

            val destConfig = AppConfig.getDestConfig()
            destConfig.values.forEach {
                if (it.isFragment) {
                    val destination = fragmentNavigator.createDestination().apply {
                        className = it.clazName
                        id = it.id
                        addDeepLink(it.pageUrl)
                    }
                    navGraph.addDestination(destination)
                } else {
                    val destination = activityNavigator.createDestination().apply {
                        setComponentName(
                            ComponentName(
                                AppGlobals.getApplication().packageName,
                                it.clazName
                            )
                        )
                        id = it.id
                        addDeepLink(it.pageUrl)
                    }
                    navGraph.addDestination(destination)
                }

                if (it.asStarter) {
                    navGraph.startDestination = it.id
                }
            }
            controller.graph = navGraph
        }
    }

}