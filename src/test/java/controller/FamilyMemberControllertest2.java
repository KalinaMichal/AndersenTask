package controller;


import org.example.FamilyMember;
import org.example.FamilyTreeApiApplication;
import org.example.repository.FamilyMemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = FamilyTreeApiApplication.class)
@AutoConfigureMockMvc
//@TestInstance(TestInstance.Lifecycle.PER_CLASS)
//@TestPropertySource(locations="classpath:test.properties")
public class FamilyMemberControllertest2{

    @Autowired
    MockMvc mvc;

    @Autowired
    FamilyMemberRepository familyMemberRepository;

    @BeforeEach
    void init() {

        this.familyMemberRepository.deleteAll();
    }

    @Test
    void shouldFindAncestors() throws Exception {

        //given
        familyMemberRepository.addFamilyMember(new FamilyMember(2L, "Adam", new FamilyMember(1L, "Bob", null, null), null));
        String jsonRequest = "{\"name\":\"Adam\",\"currency\":\"USD\"}";

        //when
        ResultActions result = mvc.perform(post("http://localhost:8080/api/family/find-ancestors/2")
                .contentType(MediaType.APPLICATION_JSON));
                //.content(jsonRequest));

        //then
        result.andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void shouldAddPerson() throws Exception {

        //given
        //familyMemberRepository.addFamilyMember(new FamilyMember(2L, "Adam", new FamilyMember(1L, "Bob", null, null), null));
        String jsonRequest = "{\"id\":\"1\",\"name\":\"Adam\",\"fatherId\":null,\"motherId\":null}";

        //when
        ResultActions result = mvc.perform(post("http://localhost:8080/api/family/add-family-member")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest));

        //then
        result.andExpect(status().isOk())
                .andDo(print());
        assertThat(familyMemberRepository.getFamilyMembers()).hasSize(1);
    }

}
