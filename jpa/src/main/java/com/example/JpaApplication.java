package com.example;

import java.util.Scanner;

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
		int select = 0;
		
		while(select != 9){
			Scanner sc = new Scanner(System.in);
			System.out.print("select number insert(1), update(2), exit(9)?");
			select = sc.nextInt();
			
			if(select == 1){
				insert();
				
			} else if(select == 2){
				update();
			}
			
			result();
		}
		
		if(select == 9){
			System.out.print("Exit!! \n");
			System.exit(0);
		}

		
	}

	private void result() {
		Iterable<Member> findAll = repository.findAll();
		System.out.println("-------------------------");
		System.out.println("|  id  |  name  |  age  |");
		System.out.println("-------------------------");
		for (Member member : findAll) {
			System.out.printf("|  %d  |  %s  |  %d  |\n", member.getId(), member.getName(), member.getAge());
			System.out.println("-------------------------");
		}
	}

	private void update() {
		Scanner sc_update = new Scanner(System.in);
		System.out.print("write id, name, age ?");
		String[] temp = sc_update.nextLine().split(",");
		
		Member m  = new Member();
		m.setId(Long.parseLong(temp[0].trim()));
		m.setName(temp[1].trim());
		m.setAge(Integer.parseInt(temp[2].trim()));
		repository.save(m);
	}

	private void insert() {
		Scanner sc_insert = new Scanner(System.in);
		System.out.print("write name, age ?");
		String[] temp = sc_insert.nextLine().split(",");

		Member m  = new Member();
		m.setName(temp[0].trim());
		m.setAge(Integer.parseInt(temp[1].trim()));
		repository.save(m);
	}
}
