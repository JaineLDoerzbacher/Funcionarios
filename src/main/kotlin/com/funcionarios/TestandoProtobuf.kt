package com.funcionarios

import java.io.FileInputStream
import java.io.FileOutputStream


fun main (args: Array<String> ){
    val request = FuncionariosRequest.newBuilder()
        .setNome("Jaine")
        .setCpf("000.000.000.-00")
        .setIdade(22)
        .setSalario(2345.54)
        .setAtivo(true)
        .setCargo(Cargo.DEV)
        .addEnderecos(Endereco.newBuilder()
            .setLogradouro("rua xxxx")
            .setCep("00000-000")
            .setComplemento("hsauhsa")
            .build()
        )
        .build()

    println(request)
    request.writeTo(FileOutputStream("funcionario-request.bin"))

    val r2 = FuncionariosRequest.newBuilder()
        .mergeFrom(FileInputStream("funcionario-request.bin"))

    r2.setCargo(Cargo.GERENTE).build()

    println(r2)
}