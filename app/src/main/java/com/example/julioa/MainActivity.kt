package com.example.julioa

import android.widget.Toast
import com.example.julioa.extras.Models
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.gson.Gson
import okhttp3.*

import com.example.julioa.databinding.ActivityMainBinding
import com.example.julioa.extras.Conexion.Companion.url
import java.io.IOException

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root

        binding.btnAccesar.setOnClickListener {
            fnLogin();
        }


        setContentView(view)
    }

    fun fnLogin() {
        Toast.makeText(baseContext, "Bienvenido", Toast.LENGTH_SHORT).show();

        var client = OkHttpClient()

        var formBody: RequestBody = FormBody.Builder()
            .add("email", binding.txtUsername.text.toString())
            .add("password", binding.txtPassword.text.toString())
            .build()

        var request = Request.Builder()
            .url(url+"login")
            .post(formBody)
            .build()


        client.newCall(request).enqueue(object : Callback {

            override fun onFailure(call: Call, e: IOException) {
                runOnUiThread {
                    Toast.makeText(
                        baseContext,
                        "Ocurrio un error " + e.message.toString(),
                        Toast.LENGTH_SHORT
                    ).show();

                }
                //
            }

            override fun onResponse(call: Call, response: Response) {
                //println(response.body?.string())


                var objGson = Gson();
                var respuesta = response.body?.string()


                var objResp = objGson.fromJson(respuesta, Models.RespLogin::class.java)

                if (objResp.token == "") {
                    runOnUiThread {
                        Toast.makeText(baseContext, objResp.error, Toast.LENGTH_SHORT).show()
                    }
                } else {
/*
                    runOnUiThread {

                        Toast.makeText(baseContext, "Acceso correceto", Toast.LENGTH_SHORT).show();
                    }
 */
                    val intent = Intent(baseContext, HomeActivity::class.java)
                    startActivity(intent)

                }
            }
        })


    }
}