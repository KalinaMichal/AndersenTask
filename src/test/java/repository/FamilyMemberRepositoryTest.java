package repository;

import org.example.FamilyMember;
import org.example.FamilyTreeApiApplication;
import org.example.repository.FamilyMemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = FamilyTreeApiApplication.class)
public class FamilyMemberRepositoryTest{

    @Autowired
    private FamilyMemberRepository sut;

    @Test
    public void shouldAddFamilyMembers(){

        //given
        Set<FamilyMember> family = createFirstFamily();
        family.addAll(createSecondFamily());

        //when
        sut.addFamilyMembers(family);

        //then
        assertThat(sut.getFamilyMembers()).containsAll(family);
    }

    @Test
    public void shouldGetAllRelatives(){

        //given
        Set<FamilyMember> family = createSecondFamily();
        FamilyMember grFather = new FamilyMember(1L, "Miroslaw", null, null);
        FamilyMember grMother = new FamilyMember(2L, "Halina", null, null);
        FamilyMember dad = new FamilyMember(3L, "Slawomir", grFather, grMother);
        FamilyMember mom = new FamilyMember(4L, "Valentina", null, null);
        FamilyMember me = new FamilyMember(5L, "Michal", dad, mom);
        family.addAll(Set.of(grFather, grMother, dad, mom, me));

        //when
        sut.addFamilyMembers(family);
        Set<FamilyMember> result = sut.getAllRelatives(me);

        //then
        assertThat(result).containsExactlyInAnyOrderElementsOf(Set.of(grFather, grMother, dad, mom, me));
    }

    @Test
    public void shouldComputeHasSharedAncestors(){

        //given
        FamilyMember grFather = new FamilyMember(1L, "Miroslaw", null, null);
        FamilyMember grMother = new FamilyMember(2L, "Halina", null, null);
        FamilyMember dad = new FamilyMember(3L, "Slawomir", grFather, grMother);
        FamilyMember mom = new FamilyMember(4L, "Valentina", null, null);
        FamilyMember me = new FamilyMember(5L, "Michal", dad, mom);
        FamilyMember random1 = new FamilyMember(6L, "Ivan", null, null);
        FamilyMember random2 = new FamilyMember(7L, "Kazimierz", random1, null);

        //when
        boolean resultForDad = sut.hasSharedAncestors(me, dad);
        boolean resultForStranger = sut.hasSharedAncestors(me, random2);

        //then
        assertThat(resultForDad).isTrue();
        assertThat(resultForStranger).isFalse();

    }

    private Set<FamilyMember> createFirstFamily(){
        FamilyMember grFather = new FamilyMember(1L, "Miroslaw", null, null);
        FamilyMember grMother = new FamilyMember(2L, "Halina", null, null);
        FamilyMember dad = new FamilyMember(3L, "Slawomir", grFather, grMother);
        FamilyMember mom = new FamilyMember(4L, "Valentina", null, null);
        FamilyMember me = new FamilyMember(5L, "Michal", dad, mom);
        return new HashSet<>(Set.of(grFather, grMother, dad, mom, me));
    }

    private Set<FamilyMember> createSecondFamily(){
        FamilyMember random1 = new FamilyMember(6L, "Ivan", null, null);
        FamilyMember random2 = new FamilyMember(7L, "Kazimierz", random1, null);
        return new HashSet<>(Set.of(random1, random2));
    }
}
