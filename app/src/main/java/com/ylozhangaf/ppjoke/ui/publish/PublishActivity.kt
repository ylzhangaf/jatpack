package com.ylozhangaf.ppjoke.ui.publish

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ylozhangaf.libnavannotation.ActivityDestination
import com.ylozhangaf.ppjoke.R

@ActivityDestination(pageUrl = "main/tabs/publish", needLogin = true)
class PublishActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_publish)
    }

}