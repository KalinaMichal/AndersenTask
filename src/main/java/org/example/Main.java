package org.example;

import java.util.Set;

public class Main {

    public static void main(String[] args) {

        System.out.println("Hello world!");

        FamilyMember a1 = new FamilyMember("AAA1");
        FamilyMember a2 = new FamilyMember("AAA2");
        FamilyMember a3 = new FamilyMember("AAA3");
        FamilyMember a4 = new FamilyMember("AAA4");
        FamilyMember a5 = new FamilyMember("AAA5", a2, a1);
        FamilyMember a6 = new FamilyMember("A6", a4, a3);
        FamilyMember a7 = new FamilyMember("asdsda7", a6, a5);

        System.out.println(a7.getAncestors());
        Set<FamilyMember> aaa= a7.getOldestAncestors();
        System.out.println(a7);

    }

    private boolean hasSharedAncestors(FamilyMember person1, FamilyMember person2){
        Set<FamilyMember> commonAncestors = person1.getOldestAncestors();
        commonAncestors.retainAll(person2.getOldestAncestors());
        return !commonAncestors.isEmpty();
    }

    private boolean areCosines1(FamilyMember person1, FamilyMember person2){
        if(shareParents(person1,person2)) {
            return false;
        }
        return hasSharedAncestors(person1, person2);
    }

    private boolean areCosines2(FamilyMember person1, FamilyMember person2){
        if(shareParents(person1,person2) || areAncestors(person1, person2)) {
            return false;
        }
        return hasSharedAncestors(person1, person2);
    }

    private boolean shareParents(FamilyMember person1, FamilyMember person2){
        return person1.getFather() == person2.getFather() ||
                person1.getMother() == person2.getMother();
    }
    private boolean areAncestors(FamilyMember person1, FamilyMember person2){
        return person1.isAncestor(person2) ||
                person2.isAncestor(person1);
    }
}