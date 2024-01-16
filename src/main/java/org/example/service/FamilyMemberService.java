package org.example.service;

import lombok.AllArgsConstructor;
import org.example.FamilyMember;
import org.example.dto.FamilyMemberDto;
import org.example.mapper.FamilyMemberMapper;
import org.example.repository.FamilyMemberRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class FamilyMemberService {

    private final FamilyMemberRepository familyMemberRepository;

    private final FamilyMemberMapper familyMemberMapper;

    public List<FamilyMemberDto> displayGraph(Long id) {

        FamilyMember person = familyMemberRepository.getById(id);
        Set<FamilyMember> allRelatives = familyMemberRepository.getAllRelatives(person);
        /*StringBuilder result = new StringBuilder();
        for (FamilyMember e:allRelatives){
            String branch = String.format("id:%d, name:%s, m:%s, f:%s \n", e.getId(), e.getName(), e.getMother() == null ? null : e.getMother().getName(), e.getFather() == null ? null : e.getFather().getName());
            result.append(branch);
        }
        return result.toString();*/
        return familyMemberMapper.toDto(allRelatives);
    }

    public String findAncestors(Long id){

        FamilyMember person = familyMemberRepository.getById(id);
        return person.printAncestors();
    }

    public void addFamilyMember(FamilyMemberDto person){

        FamilyMember newFm = familyMemberMapper.toEntity(person);
        familyMemberRepository.addFamilyMember(newFm);
    }

    public boolean hasSharedAncestors(Long id1, Long id2) {

        FamilyMember person1 = this.familyMemberRepository.getById(id1);
        FamilyMember person2 = this.familyMemberRepository.getById(id2);

        Set<FamilyMember> commonAncestors = person1.getOldestAncestors();
        commonAncestors.retainAll(person2.getOldestAncestors());
        return !commonAncestors.isEmpty();
    }

    public void addGraph(List<FamilyMemberDto> newFamilyMembers) {

        newFamilyMembers.forEach(this::addFamilyMember);
        //old technique. For future use with db
        //List<FamilyMember> fms = newFamilyMembers.stream().map(this.familyMemberMapper::toEntity).toList();
        //first to Entity and then to repo bcs we add all or nothing / transaction
        //fms.forEach(this.familyMemberRepository::addFamilyMember);
    }

    public boolean areCosinesWithoutAncCheck(Long id1, Long id2){

        FamilyMember person1 = this.familyMemberRepository.getById(id1);
        FamilyMember person2 = this.familyMemberRepository.getById(id2);
        if(shareParents(person1,person2)) {
            return false;
        }
        return hasSharedAncestors(id1, id2);
    }

    public boolean areCosines(Long id1, Long id2){

        FamilyMember person1 = familyMemberRepository.getById(id1);
        FamilyMember person2 = familyMemberRepository.getById(id2);
        if(shareParents(person1,person2) || areAncestors(person1, person2)) {
            return false;
        }
        return hasSharedAncestors(id1, id2);
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
