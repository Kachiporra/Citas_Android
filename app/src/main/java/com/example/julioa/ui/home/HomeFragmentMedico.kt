package com.example.julioa.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.julioa.HomeActivity
import com.example.julioa.R
import com.example.julioa.databinding.FragmentHomeBinding
import com.example.julioa.databinding.FragmentHomeMedicoBinding
import com.example.julioa.extras.Conexion.Companion.url
import com.example.julioa.extras.MedicoAdapter
import com.example.julioa.extras.Models
import com.example.julioa.extras.PacienteAdapter
import com.google.gson.Gson
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttp
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

class HomeFragmentMedico : Fragment() {

    private var _binding: FragmentHomeMedicoBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeMedicoBinding.inflate(inflater, container, false)
        val root: View = binding.root


       binding.fab.setOnClickListener{
           var navController=findNavController()
           navController.navigate(R.id.nav_nuevo_medico)

       }

        obtenerdatos()
        return root
    }

    fun obtenerdatos() {
        var url = url+"medicos"
        var request = Request.Builder()
            .url(url)
            .header("Accept", "application/json")
            //.header("Authorizacion","bearer"+token)
            .get()
            .build()
        val client = OkHttpClient()
        var gson = Gson()

        client.newCall(request).enqueue(object : Callback {

            override fun onFailure(call: Call, e: IOException) {
                activity?.runOnUiThread {
                    Toast.makeText(
                        context,
                        "Ocurrio un error " + e.message.toString(),
                        Toast.LENGTH_SHORT
                    ).show();

                }
                //
            }

            override fun onResponse(call: Call, response: Response) {

                var respuesta = response.body?.string()

                println("Respuesta" + respuesta)
                activity?.runOnUiThread {
                    var listItems = gson.fromJson(respuesta, Array<Models.Medico>::class.java)
                    val adapter = MedicoAdapter(listItems.toMutableList())
                    binding.rvDatosMedico.layoutManager = LinearLayoutManager(context)
                    binding.rvDatosMedico.adapter = adapter
                }

            }
        })

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}