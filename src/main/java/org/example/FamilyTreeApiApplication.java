package org.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FamilyTreeApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(FamilyTreeApiApplication.class, args);
    }

    /*public static void main(String[] args) {

        System.out.println("Hello world!");

        FamilyMember a1 = new FamilyMember(id, "AAA1");
        FamilyMember a2 = new FamilyMember(id, "AAA2");
        FamilyMember a3 = new FamilyMember(id, "AAA3");
        FamilyMember a4 = new FamilyMember(id, "AAA4");
        FamilyMember a5 = new FamilyMember("AAA5", a2, a1);
        FamilyMember a6 = new FamilyMember("A6", a4, a3);
        FamilyMember a7 = new FamilyMember("asdsda7", a6, a5);

        System.out.println(a7.getAncestors());
        Set<FamilyMember> aaa= a7.getOldestAncestors();
        System.out.println(a7);

    }*/
}