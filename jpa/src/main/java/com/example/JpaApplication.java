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
		m.setName("sunny");
		repository.save(m);
		
		Member m2  = new Member();
		m2.setAge(25);
		m2.setName("jun");
		repository.save(m2);
		
		Iterable<Member> findAll = repository.findAll();
		for (Member member : findAll) {
			System.out.println(member.getAge() + " - "+member.getName()+ " - "+ member.getId());
		}
		
		m.setAge(30);
		repository.save(m);
		
		Iterable<Member> findAll_2 = repository.findAll();
		for (Member member : findAll_2) {
			System.out.println(member.getAge() + " - "+member.getName()+ " - "+ member.getId());
		}
		
	}
}
