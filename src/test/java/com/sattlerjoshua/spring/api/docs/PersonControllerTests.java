package com.sattlerjoshua.spring.api.docs;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.HashMap;
import java.util.Map;

import static com.sattlerjoshua.spring.api.docs.PersonController.*;
import static org.hamcrest.Matchers.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
public class PersonControllerTests {

    private final ObjectMapper mapper = new ObjectMapper();

    private Map<Long, Person> mockPersonRepository;
    private MockMvc mockMvc;


    @BeforeEach
    public void setUp(RestDocumentationContextProvider restDocumentation) {
        this.mockPersonRepository = new HashMap<>();
        PersonController personController = new PersonController(mockPersonRepository);
        this.mockMvc = MockMvcBuilders.standaloneSetup(personController)
                .apply(documentationConfiguration(restDocumentation))
                .build();
    }

    @Test
    void create() throws Exception {
        this.mockPersonRepository.clear();
        Person p = new Person(null, "Joshua", "Sattler");
        this.mockMvc.perform(
                post(PERSON_API_PATH)
                        .content(toJson(p))
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("firstName", is(p.firstName())))
                .andExpect(jsonPath("lastName", is(p.lastName())))
                .andExpect(jsonPath("id", is(notNullValue())))
                .andDo(document("persons/{method-name}",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        responseFields(
                                fieldWithPath("firstName").description("The first name of the newly created person"),
                                fieldWithPath("lastName").description("The last name of the newly created person"),
                                fieldWithPath("id").description("The id of the newly created person")
                        )));
    }

    @Test
    void retrieveById() throws Exception {
        this.mockPersonRepository.clear();
        Person p = new Person(1L, "Max", "Mustermann");
        this.mockPersonRepository.put(p.id(), p);

        this.mockMvc.perform(
                get(PERSON_API_PATH + "/{id}", p.id())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("firstName", is(p.firstName())))
                .andExpect(jsonPath("lastName", is(p.lastName())))
                .andExpect(jsonPath("id", is(notNullValue())))
                .andDo(document("persons/{method-name}",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        responseFields(
                                fieldWithPath("firstName").description("The first name of the newly created person"),
                                fieldWithPath("lastName").description("The last name of the newly created person"),
                                fieldWithPath("id").description("The id of the newly created person")
                        )));
    }

    @Test
    void retrieveAll() throws Exception {
        this.mockPersonRepository.clear();
        Person p1 = new Person(1L, "Max", "Mustermann");
        Person p2 = new Person(2L, "Erika", "Musterfrau");
        this.mockPersonRepository.put(p1.id(), p1);
        this.mockPersonRepository.put(p2.id(), p2);

        this.mockMvc.perform(
                get(PERSON_API_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", hasSize(2)))
                .andDo(document("persons/{method-name}",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        responseFields(
                                fieldWithPath("[].firstName").description("The first name of the newly created person"),
                                fieldWithPath("[].lastName").description("The last name of the newly created person"),
                                fieldWithPath("[].id").description("The id of the newly created person")
                        ))
                );
    }

    @Test
    void update() throws Exception {
        this.mockPersonRepository.clear();
        Person p = new Person(1L, "Max", "Mustermann");
        this.mockPersonRepository.put(p.id(), p);
        Person update = new Person(1L, "Erika", "Musterfrau");
        this.mockMvc.perform(
                put(PERSON_API_PATH + "/{id}", update.id())
                        .content(toJson(update))
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("firstName", is(update.firstName())))
                .andExpect(jsonPath("lastName", is(update.lastName())))
                .andExpect(jsonPath("id", is(notNullValue())))
                .andDo(document("persons/{method-name}",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        responseFields(
                                fieldWithPath("firstName").description("The first name of the newly created person"),
                                fieldWithPath("lastName").description("The last name of the newly created person"),
                                fieldWithPath("id").description("The id of the newly created person")
                        )));
    }

    @Test
    void delete() throws Exception {
        this.mockPersonRepository.clear();
        Person p = new Person(1L, "Max", "Mustermann");
        this.mockPersonRepository.put(p.id(), p);
        this.mockMvc.perform(
                MockMvcRequestBuilders.delete(PERSON_API_PATH + "/{id}", p.id())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andDo(document("persons/{method-name}",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())
                ));
    }

    private String toJson(Person person) throws JsonProcessingException {
        return mapper.writeValueAsString(person);
    }
}
