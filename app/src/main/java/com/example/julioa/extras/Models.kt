package com.example.julioa.extras

import java.text.DateFormat

class Models {

    data class RespLogin(
        var idUsr:Int,
        var token:String,
        var nombre:String,
        var error:String
    )
    data class Cita(
        var id:Int,
        var id_enfermedad:Int,
        var id_paciente:Int,
        var id_medico:Int,
        var consultorio:String,
        var nombre_paciente:String,
        var nombre_medico:String,
        var nombre_enfermedad:String,
        var fecha:String,
        )

    data class Paciente(
        var id:Int,
        var nombre:String,
        var nss:String,
        var tipo_sangre:String,
        var alergias:String,
        var telefono:String,
        var domicilio:String,

    ){
        override fun toString(): String {
            return nombre
        }
    }


    data class Medico(
        var id:Int,
        var nombre:String,
        var cedula:String,
        var especialidad:String,
        var turno:String,
        var telefono:String,
        var email:String,
    )
    {
        override fun toString(): String {
            return nombre
        }
    }

    data class Enfermedad(
        var id:Int,
        var nombre:String,
        var tipo:String,
        var descripcion:String,
    ){
        override fun toString(): String {
            return nombre
        }
    }
}
