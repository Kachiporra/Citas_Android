package com.example.julioa.ui.citas

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.julioa.R
import com.example.julioa.databinding.FragmentCitaBinding
import com.example.julioa.databinding.FragmentHomeEnfermedadBinding
import com.example.julioa.extras.CitaAdapter
import com.example.julioa.extras.Conexion
import com.example.julioa.extras.EnfermedadAdapter
import com.example.julioa.extras.Models
import com.example.julioa.ui.home.HomeViewModel
import com.google.gson.Gson
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException


class CitaFragment : Fragment() {


    private var _binding: FragmentCitaBinding? = null

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

        _binding = FragmentCitaBinding.inflate(inflater, container, false)
        val root: View = binding.root


        binding.fab.setOnClickListener{
            var navController=findNavController()
            navController.navigate(R.id.nav_nuevo_cita)

        }

        obtenerdatos()
        return root
    }

    fun obtenerdatos() {
        var url = Conexion.url +"citas"
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
                    var listItems = gson.fromJson(respuesta, Array<Models.Cita>::class.java)
                    val adapter = CitaAdapter(listItems.toMutableList())
                    binding.rvDatosCita.layoutManager = LinearLayoutManager(context)
                    binding.rvDatosCita.adapter = adapter
                }

            }
        })

    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}