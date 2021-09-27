package com.funcionarios

import com.google.protobuf.Timestamp
import io.grpc.ServerBuilder
import io.grpc.stub.StreamObserver
import java.time.LocalDateTime
import java.time.ZoneId

fun main() {

    val server = ServerBuilder
        .forPort(50059)
        .addService(FuncionariosEndpoint())
        .build()
    server.start()
    server.awaitTermination()

}

class FuncionariosEndpoint : FuncionarioServiceGrpc.FuncionarioServiceImplBase() {
    override fun cadastrar(request: FuncionariosRequest?, responseObserver: StreamObserver<FuncionariosResponse>?) {

        val instant = LocalDateTime.now().atZone(ZoneId.of("UTC")).toInstant()
        val criadoEm = Timestamp.newBuilder()
            .setSeconds(instant.epochSecond)
            .setNanos(instant.nano)
            .build()

        var nome = request?.nome

        if (request != null) {
            if (!request.hasField(FuncionariosRequest.getDescriptor().findFieldByName("nome"))) {
                nome = "[????]"
            }
        }

        val response = FuncionariosResponse.newBuilder()
            .setNome(nome)
            .setCriadoEm(criadoEm)
            .build()

        println(response)

        responseObserver?.onNext(response)
        responseObserver?.onCompleted()
    }
}