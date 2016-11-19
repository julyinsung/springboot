package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class JpaApplication implements CommandLineRunner {

	@Autowired Repository repository;
	
	public static void main(String[] args) {
		SpringApplication.run(JpaApplication.class, args);
	}
	
	public void run(String... args) throws Exception {
		Member m  = new Member();
		m.setAge(20);
		m.setName("aaa");
		repository.save(m);
		
		Iterable<Member> findAll = repository.findAll();
		for (Member member : findAll) {
			System.out.println(member.getAge() + " - "+member.getName()+ " - "+ member.getId());
		}
	}
}
