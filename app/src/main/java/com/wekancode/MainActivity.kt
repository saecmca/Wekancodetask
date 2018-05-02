package com.wekancode

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Handler().postDelayed({
            if(Localstroage.isAlreadyLoggedIn(this)) {
                val intent: Intent = Intent(applicationContext, Dotcreate::class.java)
                startActivity(intent)
                finish()
            }else{
                val intent: Intent = Intent(applicationContext, Loginscreen::class.java)
                startActivity(intent)
                finish()
            }
        }, 1500)
    }
}
