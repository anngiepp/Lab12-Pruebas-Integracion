package com.tecsup.petclinic.webs;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import com.tecsup.petclinic.dtos.VisitDTO;
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
public class VisitControllerTest {

    private static final ObjectMapper om = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testFindAllVisits() throws Exception {
        this.mockMvc.perform(get("/visits"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE));
    }

    @Test
    public void testCreateVisit() throws Exception {
        VisitDTO newVisit = VisitDTO.builder()
                .visitDate("2026-06-03")
                .description("Revision general")
                .petId(1)
                .build();

        this.mockMvc.perform(post("/visits")
                        .content(om.writeValueAsString(newVisit))
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.visitDate", is("2026-06-03")))
                .andExpect(jsonPath("$.description", is("Revision general")))
                .andExpect(jsonPath("$.petId", is(1)));
    }

    @Test
    public void testFindVisitOK() throws Exception {
        VisitDTO newVisit = VisitDTO.builder()
                .visitDate("2026-06-04")
                .description("Consulta veterinaria")
                .petId(1)
                .build();

        ResultActions mvcActions = mockMvc.perform(post("/visits")
                        .content(om.writeValueAsString(newVisit))
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        String response = mvcActions.andReturn().getResponse().getContentAsString();
        Integer id = JsonPath.parse(response).read("$.id");

        mockMvc.perform(get("/visits/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(id)))
                .andExpect(jsonPath("$.visitDate", is("2026-06-04")))
                .andExpect(jsonPath("$.description", is("Consulta veterinaria")))
                .andExpect(jsonPath("$.petId", is(1)));
    }

    @Test
    public void testFindVisitKO() throws Exception {
        mockMvc.perform(get("/visits/99999"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testUpdateVisit() throws Exception {
        VisitDTO newVisit = VisitDTO.builder()
                .visitDate("2026-06-05")
                .description("Vacunacion")
                .petId(1)
                .build();

        ResultActions mvcActions = mockMvc.perform(post("/visits")
                        .content(om.writeValueAsString(newVisit))
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        String response = mvcActions.andReturn().getResponse().getContentAsString();
        Integer id = JsonPath.parse(response).read("$.id");

        VisitDTO updateVisit = VisitDTO.builder()
                .id(id.longValue())
                .visitDate("2026-06-06")
                .description("Vacunacion actualizada")
                .petId(2)
                .build();

        mockMvc.perform(put("/visits/" + id)
                        .content(om.writeValueAsString(updateVisit))
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(id)))
                .andExpect(jsonPath("$.visitDate", is("2026-06-06")))
                .andExpect(jsonPath("$.description", is("Vacunacion actualizada")))
                .andExpect(jsonPath("$.petId", is(2)));
    }

    @Test
    public void testDeleteVisit() throws Exception {
        VisitDTO newVisit = VisitDTO.builder()
                .visitDate("2026-06-07")
                .description("Control medico")
                .petId(1)
                .build();

        ResultActions mvcActions = mockMvc.perform(post("/visits")
                        .content(om.writeValueAsString(newVisit))
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        String response = mvcActions.andReturn().getResponse().getContentAsString();
        Integer id = JsonPath.parse(response).read("$.id");

        mockMvc.perform(delete("/visits/" + id))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteVisitKO() throws Exception {
        mockMvc.perform(delete("/visits/99999"))
                .andExpect(status().isNotFound());
    }
}