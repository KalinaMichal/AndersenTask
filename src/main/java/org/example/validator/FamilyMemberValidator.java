package org.example.validator;

import lombok.RequiredArgsConstructor;
import org.example.dto.FamilyMemberDto;
import org.example.exception.FakeFatherException;
import org.example.exception.FakeMotherException;
import org.example.exception.IdAlreadyUsedException;
import org.example.repository.FamilyMemberRepository;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class FamilyMemberValidator {


    private final FamilyMemberRepository familyMemberRepository;

    public void validateNewGraph(List<FamilyMemberDto> persons){

        Set<Long> ids = persons.stream().map(FamilyMemberDto::getId).collect(Collectors.toSet());
        persons.forEach(e->validateNode(ids, e));
    }

    private void validateNode(Set<Long> ids, FamilyMemberDto person){

        if(isAllreadyInDb(person.getId())){
            throw new IdAlreadyUsedException(person.getId());
        }
        Long fatherId = person.getFatherId();
        if(fatherId != null && !ids.contains(fatherId) && !isAllreadyInDb(fatherId)){
            throw new FakeFatherException();
        }
        Long motherId = person.getMotherId();
        if(motherId != null && !ids.contains(motherId) && !isAllreadyInDb(motherId)){
            throw new FakeMotherException();
        }
    }

    public void validate(FamilyMemberDto person){

        validateNode(Collections.emptySet(), person);
    }

    private boolean isAllreadyInDb(Long id){

        return familyMemberRepository.getFamilyMembers().stream().anyMatch(e -> id.equals(e.getId()));
    }
}
