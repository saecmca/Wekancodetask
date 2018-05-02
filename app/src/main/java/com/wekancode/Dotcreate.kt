package com.wekancode

import android.app.ProgressDialog
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.wekancode.request.DotReq
import com.wekancode.request.LoginResp
import com.wekancode.request.Utility
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Dotcreate : AppCompatActivity(), View.OnClickListener {
    var btnCreate: Button?? = null
    var btnLogout: Button?? = null
    var edName: EditText?? = null
    lateinit var progress: ProgressDialog
    override fun onClick(p0: View?) {
        when (p0!!.id) {
            R.id.btnCreate -> {
                if (edName!!.getText().toString().equals("")) {
                    Toast.makeText(applicationContext, "Please enter name", Toast.LENGTH_SHORT).show()
                } else {
                    if (Utility.isNetworkStatusAvialable(this,"")) {
                        callWebservice()
                    }
                }
            }
            R.id.btnLogout -> {
                Toast.makeText(applicationContext, "Successfully logedout", Toast.LENGTH_SHORT).show()
                Localstroage.clearAllPreferences(this)
                finish()
            }

        }


    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dotcreate)
        edName = findViewById(R.id.edname)
        btnCreate = findViewById(R.id.btnCreate)
        btnCreate!!.setOnClickListener(this)

        btnLogout = findViewById(R.id.btnLogout)
        btnLogout!!.setOnClickListener(this)
    }

    private fun callWebservice() {
        progress = ProgressDialog.show(this, "Loading", "")

        val apiInterface = RestClient.getapiclient(this)
        val getNowShowingMoviesResponseCall = apiInterface.getDotResp("Bearer " + Localstroage.getAccesstoken(this), DotReq(edName!!.getText().toString() + "dot", "+91", "7305916574",
                "1", "No 7 shanmugam salai, Vadapalani", "Dot Full description", "13.054938",
                "80.208097", "600078", "600078", "Chennai", "Tamil Nadu", "India", "0"))
        getNowShowingMoviesResponseCall.enqueue(object : Callback<LoginResp> {
            override fun onResponse(call: Call<LoginResp>, response: Response<LoginResp>) {
                try {
                    val getLoginResp = response.body()
                    val rescode = response.code()
                    progress.dismiss()
                    if (rescode == 201) {
                        Toast.makeText(applicationContext, "Dot Created ID= "+getLoginResp.data.id , Toast.LENGTH_LONG).show()
                    } else if (rescode == 400) {
                        Toast.makeText(applicationContext, "Please enter valid request", Toast.LENGTH_SHORT).show()
                    }else if (rescode == 401) {
                        Toast.makeText(applicationContext, "Token unauthorized please logout and signin again", Toast.LENGTH_SHORT).show()
                    }


                } catch (e: Exception) {
                    e.printStackTrace()
                }

            }

            override fun onFailure(call: Call<LoginResp>, t: Throwable) {
                t.printStackTrace()
                progress.dismiss()
                Toast.makeText(applicationContext, "Please try again..", Toast.LENGTH_SHORT).show()

            }
        })
    }
}


