
package Server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.nio.ByteBuffer;

/**
 * 5. Escreva um programa distribuído onde um processo do tipo A (servidor) é
 * lançado e para esse é escolhido um número (passado como parâmetro). Processos
 * do tipo B (clientes) devem ser lançados e escolherem seus números (passados
 * como parâmetro). Os clientes devem enviar seus números ao servidor, que deve
 * responder “Acertou” aos clientes que adivinharem o número escolhido e “Errou”
 * aos outros clientes.
 * 
 * @author Evandro Rocha da Cunha - Miguel Luciano Benites da Silva
 *
 */

public class Servidor {

	public static void main(String[] args) {
		Servidor server = new Servidor();
		try {
			server.processarMensagem();
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private static void processarMensagem() throws SocketException, InterruptedException {

		// SORTEIA O NÚMERO de 1 a 100
		int numeroSorteado = (int) (Math.random() * 100);
		System.out.println("Número sorteado =  " + numeroSorteado);
		Thread.sleep(3000);
		byte[] buff_out = new byte[1024];
		byte[] buff_in = new byte[1024];

		System.out.println("Criando socket ...");
		Thread.sleep(3000);
		// SOCKET PARA CONEXÃO ENTRE CLIENT E SERVER
		DatagramSocket socket = new DatagramSocket(45000);
		System.out.println("Criando pacote para armazenar mensagem recebida...");
		Thread.sleep(3000);
		// CRIANDO ESTRUTURA DE ARMAZENAMENTO DE PACOTE
		DatagramPacket input = new DatagramPacket(buff_in, buff_in.length);
		System.out.println("Aguardando mensagem...");
		Thread.sleep(3000);

		while (true) {
			try {

				// RECEBENDO PACOTE
				socket.receive(input);
				System.out.println("Recebendo mensagem...");
				Thread.sleep(3000);
				// TRADUZINDO MENSAGEM RECEBIDA
				String numero = new String(input.getData(), 0, input.getLength());
				// TRANSFORMANDO EM INTEIRO
				int numeroCliente = Integer.parseInt(numero);
				// PEGANDO IP PARA RESPOSTA
				InetAddress resp_ip = input.getAddress();
				// PEGANDO PORTA PARA RESPOSTA
				int resp_port = input.getPort();
				System.out.println("Conectado a  " + resp_ip + "na porta  " + resp_port);
				Thread.sleep(3000);
				System.out.println("Número informado pelo cliente:  " + numeroCliente);
				Thread.sleep(3000);

				// VARIÁVEL QUE ARMAZENA A MENSAGEM DE RESPOSTA AO CLIENTE
				String mensagemResposta = "";

				// FILTRANDO A RESPOSTA
				if (numeroCliente == numeroSorteado) {
					mensagemResposta = "ACERTOU";

				} else {
					mensagemResposta = "ERROU";

				}
				System.out.println("Cliente  " + mensagemResposta + "!!!");
				Thread.sleep(3000);

				// CONVERTENDO EM BYTES A MENSAGEM
				byte[] resposta = mensagemResposta.getBytes();
				// GRAVANDO NO BUFFER A RESPOSTA
				ByteBuffer.wrap(buff_out).put(resposta);
				// MONTANDO O PACOTE COM A RESPOSTA
				System.out.println("Montando pacote para a resposta...");
				Thread.sleep(3000);
				DatagramPacket output = new DatagramPacket(buff_out, buff_out.length, resp_ip, resp_port);
				// ENVIANDO A RESPOSTA
				socket.send(output);
				System.out.println("Enviando a mensagem...");
				Thread.sleep(3000);
			} catch (IOException e) {
				e.printStackTrace();
				break;
			}
		}
		System.out.println("Mensagem enviada!!!");
		Thread.sleep(3000);
		// FECHANDO A CONEXÃO
		socket.close();
		System.out.println("Fechando conexão!");
		Thread.sleep(3000);
	}
}