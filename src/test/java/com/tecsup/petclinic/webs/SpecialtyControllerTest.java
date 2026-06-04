package com.tecsup.petclinic.webs;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import com.tecsup.petclinic.dtos.SpecialtyDTO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest
@Slf4j
public class SpecialtyControllerTest {

    private static final ObjectMapper om = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testFindAllSpecialties() throws Exception {
        this.mockMvc.perform(get("/specialties"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE));
    }

    @Test
    public void testCreateSpecialty() throws Exception {
        SpecialtyDTO newSpecialty = SpecialtyDTO.builder()
                .name("cardiology")
                .build();

        this.mockMvc.perform(post("/specialties")
                        .content(om.writeValueAsString(newSpecialty))
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is("cardiology")));
    }

    @Test
    public void testFindSpecialtyOK() throws Exception {
        SpecialtyDTO newSpecialty = SpecialtyDTO.builder()
                .name("neurology")
                .build();

        ResultActions mvcActions = mockMvc.perform(post("/specialties")
                        .content(om.writeValueAsString(newSpecialty))
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        String response = mvcActions.andReturn().getResponse().getContentAsString();
        Integer id = JsonPath.parse(response).read("$.id");

        mockMvc.perform(get("/specialties/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(id)))
                .andExpect(jsonPath("$.name", is("neurology")));
    }

    @Test
    public void testFindSpecialtyKO() throws Exception {
        mockMvc.perform(get("/specialties/99999"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testUpdateSpecialty() throws Exception {
        SpecialtyDTO newSpecialty = SpecialtyDTO.builder()
                .name("dermatology")
                .build();

        ResultActions mvcActions = mockMvc.perform(post("/specialties")
                        .content(om.writeValueAsString(newSpecialty))
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        String response = mvcActions.andReturn().getResponse().getContentAsString();
        Integer id = JsonPath.parse(response).read("$.id");

        SpecialtyDTO updateSpecialty = SpecialtyDTO.builder()
                .id(id)
                .name("radiology")
                .build();

        mockMvc.perform(put("/specialties/" + id)
                        .content(om.writeValueAsString(updateSpecialty))
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(id)))
                .andExpect(jsonPath("$.name", is("radiology")));
    }

    @Test
    public void testDeleteSpecialty() throws Exception {
        SpecialtyDTO newSpecialty = SpecialtyDTO.builder()
                .name("oncology")
                .build();

        ResultActions mvcActions = mockMvc.perform(post("/specialties")
                        .content(om.writeValueAsString(newSpecialty))
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        String response = mvcActions.andReturn().getResponse().getContentAsString();
        Integer id = JsonPath.parse(response).read("$.id");

        mockMvc.perform(delete("/specialties/" + id))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteSpecialtyKO() throws Exception {
        mockMvc.perform(delete("/specialties/99999"))
                .andExpect(status().isNotFound());
    }
}