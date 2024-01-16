package controller;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.example.FamilyMember;
import org.example.FamilyTreeApiApplication;
import org.example.repository.FamilyMemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = FamilyTreeApiApplication.class)
public class FamilyMemberControllerTest {

    private static final String BASE_URL = "http://localhost:8080/api/family";

    @Autowired
    private FamilyMemberRepository familyMemberRepository;

    @Test
    public void testGetUserEndpoint() {

        //TODO: saveToRepo
        // Definiuj ścieżkę endpointu
        String endpointPath = "/find-ancestors/1";
        familyMemberRepository.addFamilyMembers(createFirstFamily());

        // Wykonaj zapytanie HTTP GET
        Response response = RestAssured.post("http://localhost:8080/api/family/find-ancestors/2");

        // Sprawdź status HTTP
        assertEquals(200, response.getStatusCode());

        // Sprawdź treść odpowiedzi (przykładowo, że zawiera oczekiwane dane)
        assertTrue(response.getBody().asString().contains("John Doe"));
    }

    @Test
    public void testCreateUserEndpoint() {
        // Definiuj ścieżkę endpointu
        String endpointPath = "/add-family-member";

        // Przykładowe dane do wysłania
        //Todo: dostosuj
        String requestBody = "{ \"username\": \"newuser\", \"email\": \"newuser@example.com\" }";

        // Wykonaj zapytanie HTTP POST
        Response response = RestAssured.given()
                .contentType("application/json")
                .body(requestBody)
                .post(BASE_URL + endpointPath);

        // Sprawdź status HTTP
        assertEquals(201, response.getStatusCode());

        // Sprawdź, czy odpowiedź zawiera oczekiwane dane
        assertTrue(response.getBody().asString().contains("User created successfully"));
    }

    private Set<FamilyMember> createFirstFamily(){
        FamilyMember grFather = new FamilyMember(1L, "Miroslaw", null, null);
        FamilyMember grMother = new FamilyMember(2L, "Halina", null, null);
        FamilyMember dad = new FamilyMember(3L, "Slawomir", grFather, grMother);
        FamilyMember mom = new FamilyMember(4L, "Valentina", null, null);
        FamilyMember me = new FamilyMember(5L, "Michal", dad, mom);
        FamilyMember random1 = new FamilyMember(6L, "Ivan", null, null);
        FamilyMember random2 = new FamilyMember(7L, "Kazimierz", random1, null);
        return new HashSet<>(Set.of(grFather, grMother, dad, mom, me, random1, random2));
    }
}
