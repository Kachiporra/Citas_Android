package com.example.julioa.ui.home

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.julioa.HomeActivity
import com.example.julioa.R
import com.example.julioa.databinding.FragmentNuevoPacienteBinding
import com.example.julioa.extras.Conexion.Companion.url
import com.example.julioa.extras.Models
import com.google.gson.Gson
import okhttp3.*
import java.io.IOException


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "json_paciente"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [NuevoPacienteFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class NuevoPacienteFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var json_paciente: String? = null
    private var param2: String? = null

    private var _binding:FragmentNuevoPacienteBinding?=null
    private val binding get() = _binding!!

    private var id:Int=0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            json_paciente = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_nuevo_paciente, container, false)
        _binding= FragmentNuevoPacienteBinding.inflate(inflater,container,false)
        val view=binding.root


        if(json_paciente!=null) {
            var gson = Gson()
            var objPaciente = gson.fromJson(json_paciente, Models.Paciente::class.java)

            id = objPaciente.id
            binding.txtNombre.setText(objPaciente.nombre)
            binding.txtNss.setText(objPaciente.nss)
            binding.txtTiposan.setText(objPaciente.tipo_sangre)
            binding.txtAlerg.setText(objPaciente.alergias)
            binding.txtTelefono.setText(objPaciente.telefono)
            binding.txtDomici.setText(objPaciente.domicilio)

        }
            binding.btnGuardar.setOnClickListener {
                guardarDatos()
            }
        binding.btnBorrar.setOnClickListener {
            borrarDatos()
        }
        return view
    }

    fun borrarDatos() {
        var client = OkHttpClient()

        var formBody: RequestBody = FormBody.Builder()
            .add("id", id.toString())
            .build()

        var request = Request.Builder()
            .url(url+"borrar/paciente")
            .post(formBody)
            .build()

        client.newCall(request).enqueue(object : Callback {

            override fun onFailure(call: Call, e: IOException) {
                activity?.runOnUiThread {
                    Toast.makeText(
                        context, "Ocurrio un error " + e.message,Toast.LENGTH_SHORT
                    ).show();

                }

            }

            override fun onResponse(call: Call, response: Response) {
                //println(response.body?.string())
                activity?.runOnUiThread{
                    activity?.onBackPressed()

                }
            }

        })

    }


    fun guardarDatos(){

        var client = OkHttpClient()

        var formBody: RequestBody = FormBody.Builder()
            .add("id", id.toString())
            .add("nombre", binding.txtNombre.text.toString())
            .add("nss", binding.txtNss.text.toString())
            .add("tipo_sangre", binding.txtTiposan.text.toString())
            .add("alergias", binding.txtAlerg.text.toString())
            .add("telefono", binding.txtTelefono.text.toString())
            .add("domicilio", binding.txtDomici.text.toString())
            .build()

        var request = Request.Builder()
            .url(url+"guardar/paciente")
            .post(formBody)
            .build()


        client.newCall(request).enqueue(object : Callback {

            override fun onFailure(call: Call, e: IOException) {
                activity?.runOnUiThread {
                    Toast.makeText(
                        context, "Ocurrio un error " + e.message,Toast.LENGTH_SHORT
                    ).show();

                }

            }


            override fun onResponse(call: Call, response: Response) {
                //println(response.body?.string())
                activity?.runOnUiThread{
                    activity?.onBackPressed()

                }
            }

        })
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment NuevoPacienteFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            NuevoPacienteFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}