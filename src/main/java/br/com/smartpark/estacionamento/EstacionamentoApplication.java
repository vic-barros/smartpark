package br.com.smartpark.estacionamento;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EstacionamentoApplication {

	public static void main(String[] args) {
		SpringApplication.run(EstacionamentoApplication.class, args);
	}
    // Spring execute nossa classe EstacionamentoApplication (inicialize o meu projeto)
	// estou passando meu projeto para dentro do Spring
}
