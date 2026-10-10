package br.com.sistemamotiva.model;

/* Esta interface foi criada para definir um "Contrato de Comportamento" puro, permitindo que classes 
   que não possuem qualquer parentesco genealógico compartilhem uma capacidade em comum de transmitir 
   dados de telemetria.*/

public interface MonitoravelViaIoT {
    double transmitirDadosSensor();
}