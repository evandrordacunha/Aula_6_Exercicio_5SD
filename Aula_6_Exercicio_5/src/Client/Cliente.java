package Client;

 
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.util.Scanner;

import org.omg.CORBA.Environment;

public class Cliente {

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

	public static void main(String[] args) {

		Cliente cliente = new Cliente();
		try {
			cliente.enviarMensagem();
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void enviarMensagem() throws InterruptedException, SocketException {

		System.out.println("Informe o número escolhido: ");
		Scanner sc = new Scanner(System.in);
		// LENDO RESPOSTA DO CLIENTE
		String numero = sc.nextLine();
		System.out.println("Resposta armazenada...");
		Thread.sleep(3000);

		try {
			System.out.println("Criando Socket para envio de mensagem...");
			Thread.sleep(3000);
			// CRIANDO O SOCKET PARA A CONEXÃO COM O SERVER
			DatagramSocket socket = new DatagramSocket(45000);
			// BUFFER PARA ARMAZENAMENTO DA RESPOSTA DO USUÁRIO EM BYTES
			System.out.println("Socket criado com sucesso!");
			Thread.sleep(3000);
			System.out.println("Criando buffer de dados para envio...");
			Thread.sleep(3000);
			byte[] dadosEnvio = new byte[1024];
			System.out.println("Convertendo resposta do client em bytes");
			Thread.sleep(3000);
			// TRANSFORAMANDO EM BYTES A RESPOSTA DO CLIENTE
			dadosEnvio = numero.getBytes();
			System.out.println("Conversão realizada com sucesso!");
			Thread.sleep(3000);
			System.out.println("Informando IP e PORTA do server para envio...");
			Thread.sleep(3000);
			// INFORMANDO IP DE ENVIO DO SERVER
			InetAddress serv_ip = InetAddress.getByName("localhost");
			// INFORMANDO A PORTA DE ENVIO PARA O SERVER
			int serv_port = 30000;
			System.out.println("Criando pacote para envio...");
			Thread.sleep(3000);

			// MONTANDO O PACOTE DE ENVIO
			DatagramPacket PacoteEnvio = new DatagramPacket(dadosEnvio, dadosEnvio.length, serv_ip, serv_port);
			System.out.println("pacote criado com sucesso!");
			Thread.sleep(3000);
			// ENVIANDO PACOTE
			System.out.println("Enviando pacote...");
			Thread.sleep(3000);
			socket.send(PacoteEnvio);
			//CONFIRMAÇÃO DE ENVIO
			System.out.println("Pacote enviado com sucesso!");
			Thread.sleep(3000);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Aguardando resposta do server...");
		Thread.sleep(3000);
		System.out.println("Criando Socket para recebimento de mensagem...");
		Thread.sleep(3000);
		// CRIANDO O SOCKET PARA A CONEXÃO COM O SERVER NA PORTA 30000
		DatagramSocket socket = new DatagramSocket(30000);
		// RECEBENDO RESPOSTA DO SERVER
		System.out.println("Criando buffer para resposta...");
		Thread.sleep(3000);
		//BUFFER PARA ARMAZENAMENTO DA RESPOSTA RECEBIDA VINDA DO SERVER
		byte[] respostaServer = new byte[1024];
		System.out.println("Buffer criado!");
		Thread.sleep(3000);
		try {
			System.out.println("Criando pacote para recebimento...");
			Thread.sleep(3000);
			//CRIANDO PACOTE DE RECEBIMENTO
			DatagramPacket pacoteRecebido = new DatagramPacket(respostaServer, respostaServer.length);
			System.out.println("Pacote criado com sucesso!");
			Thread.sleep(3000);

			//RECEBENDO O PACOTE OU ENCERRANDO COM TIME OUT A APLICAÇÃO
			socket.setSoTimeout(1000);
			System.out.println("Recebendo pacote...");
			Thread.sleep(3000);

			socket.receive(pacoteRecebido);
			System.out.println("Pacote transferido com sucesso!");
			Thread.sleep(3000);

			// CONVERTENDO EM INTEIRO A A RESPOSTA
			String respostaRecebida = ByteBuffer.wrap(respostaServer).toString();
			System.out.println("Resposta" + " = " + respostaRecebida);
		} catch (IOException e) {
			System.out.println("Servidor offline.");
			// fecha o socket
			System.out.println("Fechando conexão...");
			Thread.sleep(3000);
			//FECHANDO A CONEXÃO
			socket.close();
			System.out.println("Conexão finalizada!");
		}

	}

}
