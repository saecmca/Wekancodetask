package com.wekancode

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.util.Base64
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.wekancode.request.LoginReq
import com.wekancode.request.LoginResp
import com.wekancode.request.Utility
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.UnsupportedEncodingException
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.security.SecureRandom

class Loginscreen : AppCompatActivity(), View.OnClickListener {
    var btnLogin: Button?? = null
    var edUser: EditText?? = null
    var edPwd: EditText?? = null
    var strUsername: String = ""
    var strPwd: String = ""
    lateinit var progress: ProgressDialog
    override fun onClick(v: View?) {
        strUsername = edUser!!.getText().toString().trim()
        strPwd = edPwd!!.getText().toString().trim()
        if (TextUtils.isEmpty(strUsername)) {
            Toast.makeText(applicationContext, "Please enter Username", Toast.LENGTH_SHORT).show()
        } else if (TextUtils.isEmpty(strPwd)) {
            Toast.makeText(applicationContext, "Please enter Password", Toast.LENGTH_SHORT).show()
        } else {
            var has: String = HASH.sha256(strPwd)
            if (Utility.isNetworkStatusAvialable(this, "Login")) {
                callWebservice(has)
            }
        }
    }

    private fun callWebservice(has: String) {
        progress = ProgressDialog.show(this, "Loading", "")

        val apiInterface = RestClient.getapiclient(this)
        val getNowShowingMoviesResponseCall = apiInterface.getLoginResp(LoginReq(strUsername, has, "b4191ec717ee49f6", "1"))
        getNowShowingMoviesResponseCall.enqueue(object : Callback<LoginResp> {
            override fun onResponse(call: Call<LoginResp>, response: Response<LoginResp>) {
                try {
                    val getLoginResp = response.body()
                    val rescode = response.code()
                    progress.dismiss()
                    if (rescode == 200) {
                        if (getLoginResp!!.data != null) {
                            if (getLoginResp!!.data.message != null) {
                                Toast.makeText(applicationContext, getLoginResp!!.data.message, Toast.LENGTH_SHORT).show()
                            } else {
                                Localstroage.saveLoginPref(this@Loginscreen, true,
                                        getLoginResp.data.access_token, getLoginResp.data.refresh_token)
                                val intent: Intent = Intent(applicationContext, Dotcreate::class.java)
                                startActivity(intent)
                                finish()
                            }

                            //  getLoginResponseIlistener.toastmessage(getLoginResp.getStatus().getDescription());
                        } else {
                            Toast.makeText(applicationContext, "please try again", Toast.LENGTH_SHORT).show()
                        }
                    } else if (rescode == 400) {
                        Toast.makeText(applicationContext, "Please enter valid request", Toast.LENGTH_SHORT).show()
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loginscreen)

        edUser = findViewById(R.id.edUser)
        edPwd = findViewById(R.id.edPwd)
        btnLogin = findViewById(R.id.btnLogin)
        btnLogin!!.setOnClickListener(this)
    }


    private object Helper {

        fun getRandomString(): String = SecureRandom().nextLong().toString()

        fun getRandomBytes(size: Int): ByteArray {
            val random = SecureRandom()
            val bytes = ByteArray(size)
            random.nextBytes(bytes)
            return bytes
        }

        fun getRawBytes(text: String): ByteArray {
            try {
                return text.toByteArray(Charsets.UTF_8)
            } catch (e: UnsupportedEncodingException) {
                return text.toByteArray()
            }
        }

        fun getString(data: ByteArray): String {
            try {
                return String(data, Charsets.UTF_8)
            } catch (e: UnsupportedEncodingException) {
                return String(data)
            }

        }

        fun base64Decode(text: String): ByteArray {
            return Base64.decode(text, Base64.NO_WRAP)
        }

        fun base64Encode(data: ByteArray): String {
            return Base64.encodeToString(data, Base64.NO_WRAP)
        }
    }

    object HASH {
        private val MD5 = "MD5"
        private val SHA_1 = "SHA-1"
        private val SHA_256 = "SHA-256"
        private val DIGITS_LOWER = charArrayOf('0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f')
        private val DIGITS_UPPER = charArrayOf('0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F')

        fun md5(data: ByteArray): String {
            return String(encodeHex(md5Bytes(data)))
        }

        fun md5(text: String): String {
            return String(encodeHex(md5Bytes(Helper.getRawBytes(text))))
        }

        fun md5Bytes(data: ByteArray): ByteArray {
            return getDigest(MD5).digest(data)
        }

        fun sha1(data: ByteArray): String {
            return String(encodeHex(sha1Bytes(data)))
        }

        fun sha1(text: String): String {
            return String(encodeHex(sha1Bytes(Helper.getRawBytes(text))))
        }

        fun sha1Bytes(data: ByteArray): ByteArray {
            return getDigest(SHA_1).digest(data)
        }

        fun sha256(data: ByteArray): String {
            return String(encodeHex(sha256Bytes(data)))
        }

        fun sha256(text: String): String {
            return String(encodeHex(sha256Bytes(Helper.getRawBytes(text))))
        }

        fun sha256Bytes(data: ByteArray): ByteArray {
            return getDigest(SHA_256).digest(data)
        }

        fun getDigest(algorithm: String): MessageDigest {
            try {
                return MessageDigest.getInstance(algorithm)
            } catch (e: NoSuchAlgorithmException) {
                throw IllegalArgumentException(e)
            }

        }

        fun encodeHex(data: ByteArray, toLowerCase: Boolean = true): CharArray {
            return encodeHex(data, if (toLowerCase) DIGITS_LOWER else DIGITS_UPPER)
        }

        fun encodeHex(data: ByteArray, toDigits: CharArray): CharArray {
            val l = data.size
            val out = CharArray(l shl 1)
            var i = 0
            var j = 0
            while (i < l) {
                out[j++] = toDigits[(240 and data[i].toInt()).ushr(4)]
                out[j++] = toDigits[15 and data[i].toInt()]
                i++
            }
            return out
        }


    }
}
