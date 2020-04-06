package com.businesstinder.tinder

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Button

class ChooseLoginRegistrationActivity : AppCompatActivity() {
    private var mLogin: Button? = null
    private var mRegister: Button? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_login_registration)
        mLogin = findViewById<View>(R.id.login) as Button
        mRegister = findViewById<View>(R.id.register) as Button
        mLogin!!.setOnClickListener(View.OnClickListener {
            val intent = Intent(this@ChooseLoginRegistrationActivity, LoginActivity::class.java)
            startActivity(intent)
            finish()
            return@OnClickListener
        })
        mRegister!!.setOnClickListener(View.OnClickListener {
            val intent = Intent(this@ChooseLoginRegistrationActivity, RegistrationActivity::class.java)
            startActivity(intent)
            finish()
            return@OnClickListener
        })
    }
}