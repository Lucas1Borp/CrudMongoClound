package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.domain.Sites;
import com.example.repo.CustomRepository;
import com.example.repo.SiteRepository;
import com.mongodb.MongoSocketReadException;
import com.mongodb.MongoTimeoutException;

@SpringBootApplication
public class RoboApplication implements CommandLineRunner {

	@Autowired
	SiteRepository repository;

	@Autowired
	CustomRepository crepo;

	public static void main(String[] args) {
		try {
			SpringApplication.run(RoboApplication.class, args);

		} catch (MongoSocketReadException m) {
			System.out.println("Não foi possivel receber a informação do mongodb");
		} catch (MongoTimeoutException n) {
			System.out.println("Limite de tempo resposta excedido");
		}

	}

	@Override
	public void run(String... args) throws Exception {
		// deleteAll();
		deleteById("5d7a57f53695e41843c6236c");
		// addSampleData();
		listAll();
		findFirst();
		findByRegex();
	}

	public void deleteAll() {
		System.out.println("Deleting all records..");
		repository.deleteAll();
	}

	public void deleteById(String id) {
		System.out.println("Deleting record");
		repository.deleteById(id);
	}

	public void addSampleData() {
		System.out.println("Adding sample data");
		repository.save(new Sites("Gmail", "www.gmail.com"));

	}

	public void listAll() {

		try {

			System.out.println("Listing sample data");
			while (repository.findAll().size() == 0) {
				Thread.sleep(1000);
				System.out.println("Aguardando resposta");
			}

			repository.findAll().forEach(u -> System.out.println(u));

		} catch (InterruptedException e) {

			e.printStackTrace();
		} catch (MongoSocketReadException m) {
			System.out.println("Não foi possivel receber a informação do mongodb");
		} catch (MongoTimeoutException n) {
			System.out.println("Limite de tempo resposta excedido");
		}

	}

	public void findFirst() {
		System.out.println("Finding first by Name");
		Sites u = repository.findFirstByName("Google");
		System.out.println(u);
	}

	public void findByRegex() {
		System.out.println("Finding by Regex - All with address starting with ^New");
		repository.findCustomByRegExAddress("^New").forEach(u -> System.out.println(u));
	}
}
