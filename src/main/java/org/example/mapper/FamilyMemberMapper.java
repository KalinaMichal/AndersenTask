package org.example.mapper;
import lombok.AllArgsConstructor;
import org.example.FamilyMember;
import org.example.dto.FamilyMemberDto;
import org.example.repository.FamilyMemberRepository;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class FamilyMemberMapper {

    private final FamilyMemberRepository familyMemberRepository;

    public FamilyMember toEntity(FamilyMemberDto fm){

        Long fatherId = fm.getFatherId();
        Long motherId = fm.getMotherId();

        FamilyMember father = fatherId == null ? null : familyMemberRepository.getById(fatherId);
        FamilyMember mother = motherId == null ? null : familyMemberRepository.getById(motherId);
        return new FamilyMember(fm.getId(), fm.getName(), father, mother);
    }

    public List<FamilyMemberDto> toDto(Set<FamilyMember> fms){

        return fms.stream().map(this::toDto).toList();
    }

    public FamilyMemberDto toDto(FamilyMember fm){

        FamilyMember father = fm.getFather();
        FamilyMember mother = fm.getMother();
        Long fatherId = father == null ? null : father.getId();
        Long motherId = mother == null ? null : mother.getId();

        return new FamilyMemberDto(fm.getId(), fm.getName(), fatherId, motherId);
    }
}
