package com.example.julioa.ui.citas

import android.os.Build
import android.os.Bundle
import android.view.Display.Mode
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.DatePicker
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.view.get
import com.example.julioa.R
import com.example.julioa.databinding.FragmentCitaBinding
import com.example.julioa.databinding.FragmentNuevoCitaBinding
import com.example.julioa.databinding.FragmentNuevoPacienteBinding
import com.example.julioa.extras.Conexion
import com.example.julioa.extras.Models
import com.example.julioa.ui.home.NuevoPacienteFragment
import com.google.gson.Gson
import okhttp3.Call
import okhttp3.Callback
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.Response
import java.io.IOException
import java.util.Date

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "json_cita"

private const val ARG_PARAM2 = "param4"


/**
 * A simple [Fragment] subclass.
 * Use the [NuevoFragmentCita.newInstance] factory method to
 * create an instance of this fragment.
 */
class NuevoFragmentCita : Fragment() {
    // TODO: Rename and change types of parameters

    private var json_cita: String? = null

    private var param2: String? = null
    private var _binding: FragmentNuevoCitaBinding? = null
    private val binding get() = _binding!!
    private var idPaciente: Int = 0
    private var idMedico: Int = 0
    private var idEnfermedad: Int = 0
    private var id:Int=0
    var idC:Int=0

    var gson = Gson()

    var objCita:Models.Cita?=null;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            json_cita = it.getString(com.example.julioa.ui.citas.ARG_PARAM1)
            param2 = it.getString(com.example.julioa.ui.citas.ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_nuevo_paciente, container, false)
        _binding = FragmentNuevoCitaBinding.inflate(inflater, container, false)
        val view = binding.root

        if(json_cita!=null) {

           objCita = gson.fromJson(json_cita, Models.Cita::class.java )
            idC = objCita?.id!!
        }

        binding.btnGuardar.setOnClickListener {
            guardarDatos()
        }
        binding.btnBorrar.setOnClickListener {
            borrarDatos()
        }
        obtenerPacientes()
        obtenerMedicos()
        obtenerEnfermedades()

        return view
    }

    private fun obtenerPacientes() {
        val url = Conexion.url + "pacientes"
        val request = Request.Builder().url(url).get().build()
        val objGson = Gson()

        val client = OkHttpClient()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                TODO("Not yet implemented")
            }

            override fun onResponse(call: Call, response: Response) {
                val respuesta = response.body?.string()
                var listaPacientes = objGson.fromJson(respuesta, Array<Models.Paciente>::class.java)
                if (listaPacientes != null) {
                    val adapter: ArrayAdapter<Models.Paciente> = ArrayAdapter<Models.Paciente>(
                        requireActivity().baseContext,
                        android.R.layout.simple_dropdown_item_1line,
                        listaPacientes
                    )
                    activity?.runOnUiThread {

                        listaPacientes.forEach {

                        if (it.id == objCita?.id_paciente) {

                            binding.cbpaciente.setText(it.nombre)

                        }
                    }
                        binding.cbpaciente.setAdapter(adapter)
                        binding.cbpaciente.setOnItemClickListener { adapterView, view, i, l ->
                            idPaciente = listaPacientes?.get(i)?.id!!;
                        }



                    }
                }
            }
        })

    }

    private fun obtenerMedicos() {
        val url = Conexion.url + "medicos"
        val request = Request.Builder().url(url).get().build()
        val objGson = Gson()

        val client = OkHttpClient()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                TODO("Not yet implemented")
            }

            override fun onResponse(call: Call, response: Response) {
                val respuesta = response.body?.string()
                var listaMedicos = objGson.fromJson(respuesta, Array<Models.Medico>::class.java)
                if (listaMedicos != null) {
                    val adapter: ArrayAdapter<Models.Medico> = ArrayAdapter<Models.Medico>(
                        requireActivity().baseContext,
                        android.R.layout.simple_dropdown_item_1line,
                        listaMedicos
                    )
                    activity?.runOnUiThread {


                        listaMedicos.forEach {
                        if (it.id == objCita?.id_medico) {
                            binding.cbmedico.setText(it.nombre)

                        }
                    }
                        binding.cbmedico.setAdapter(adapter)
                        binding.cbmedico.setOnItemClickListener { adapterView, view, i, l ->
                            idMedico = listaMedicos?.get(i)?.id!!;



                        }

                    }//for each


                }//if listamedicos

            }//onresponse
        })

    }//obtener medicos


    private fun obtenerEnfermedades() {
        val url = Conexion.url + "enfermedades"
        val request = Request.Builder().url(url).get().build()
        val objGson = Gson()

        val client = OkHttpClient()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                TODO("Not yet implemented")
            }

            override fun onResponse(call: Call, response: Response) {
                val respuesta = response.body?.string()
                var listaEnfermedad =
                    objGson.fromJson(respuesta, Array<Models.Enfermedad>::class.java)
                if (listaEnfermedad != null) {
                    val adapter: ArrayAdapter<Models.Enfermedad> = ArrayAdapter<Models.Enfermedad>(
                        requireActivity().baseContext,
                        android.R.layout.simple_dropdown_item_1line,
                        listaEnfermedad
                    )
                    activity?.runOnUiThread {

                        listaEnfermedad.forEach {
                        if (it.id == objCita?.id_enfermedad ) {
                            binding.cbenfermedad.setText(it.nombre)
                        }
                    }
                        binding.cbenfermedad.setAdapter(adapter)
                        binding.cbenfermedad.setOnItemClickListener { adapterView, view, i, l ->
                            idEnfermedad = listaEnfermedad?.get(i)?.id!!;
                        }

                    }
                }
            }
        })

    }

    fun guardarDatos(){

        var client = OkHttpClient()

var value = binding.txtFecha


        var formBody: RequestBody = FormBody.Builder()
            .add("id", idC.toString())
            .add("id_medico", idMedico.toString())
            .add("id_paciente", idPaciente.toString())
            .add("id_enfermedad", idEnfermedad.toString())
            .add("consultorio", binding.txtConsultorio.text.toString())
            .add("fecha",value.year.toString()+"/"+ value.month.toString() +"/"+ value.dayOfMonth.toString())

            .build()

        var request = Request.Builder()
            .url(Conexion.url +"guardarcita")
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


    fun borrarDatos() {
        var client = OkHttpClient()

        var formBody: RequestBody = FormBody.Builder()
            .add("id", idC.toString())
            .build()

        var request = Request.Builder()
            .url(Conexion.url +"borrar/cita")
            .post(formBody)
            .build()

        client.newCall(request).enqueue(object : Callback {

            override fun onFailure(call: Call, e: IOException) {
                activity?.runOnUiThread {
                    Toast.makeText(
                        context, "Ocurrio un error " + e.message, Toast.LENGTH_SHORT
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
         * @return A new instance of fragment NuevoFragmentCita.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            NuevoPacienteFragment().apply {
                arguments = Bundle().apply {
                    putString(com.example.julioa.ui.citas.ARG_PARAM1, param1)
                    putString(com.example.julioa.ui.citas.ARG_PARAM2, param2)
                }
            }
    }




}

private operator fun Int.plus(toString: String) {

}

