package org.example.repository;

import org.example.FamilyMember;
import org.example.exception.BadIdException;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/***
 * normally it would extend JpaRepository
 */
@Service
public class FamilyMemberRepository {

    private final Set<FamilyMember> kids = new HashSet<>();
    private final Set<FamilyMember> familyMembers = new HashSet<>();

    public Set<FamilyMember> getFamilyMembers(){

        return Set.copyOf(familyMembers);
    }

    public void deleteAll(){

        this.kids.clear();
        this.familyMembers.clear();
    }

    public void addFamilyMembers(Set<FamilyMember> fms){

        fms.forEach(this::addFamilyMember);
    }

    public void addFamilyMember(FamilyMember fm){

        kids.removeAll(Arrays.asList(fm.getMother(), fm.getFather()));
        kids.add(fm);
        familyMembers.add(fm);
    }

    public FamilyMember getById(Long id) {

        Optional<FamilyMember> fm = familyMembers.stream().filter(e->e.getId() == id).findFirst();
        if(fm.isEmpty()){
            throw new BadIdException(id);
        }
        return fm.get();
    }

    public Set<FamilyMember> getAllRelatives(FamilyMember fm) {

        List<FamilyMember> cousins = kids.stream().filter(e->hasSharedAncestors(e,fm)).toList();
        return cousins.stream().flatMap(e->e.getAncestors().stream()).collect(Collectors.toSet());
    }

    public boolean hasSharedAncestors(FamilyMember person1, FamilyMember person2) {

        Set<FamilyMember> commonAncestors = person1.getOldestAncestors();
        commonAncestors.retainAll(person2.getOldestAncestors());
        return !commonAncestors.isEmpty();
    }
}
