package com.tecsup.petclinic.webs;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import com.tecsup.petclinic.dtos.OwnerDTO;
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
public class OwnerControllerTest {

    private static final ObjectMapper om = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testFindAllOwners() throws Exception {
        this.mockMvc.perform(get("/owners"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE));
    }

    @Test
    public void testCreateOwner() throws Exception {
        OwnerDTO newOwner = OwnerDTO.builder()
                .firstName("Juan")
                .lastName("Perez")
                .address("Av. Lima 123")
                .city("Lima")
                .telephone("999888777")
                .build();

        this.mockMvc.perform(post("/owners")
                        .content(om.writeValueAsString(newOwner))
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName", is("Juan")))
                .andExpect(jsonPath("$.lastName", is("Perez")))
                .andExpect(jsonPath("$.address", is("Av. Lima 123")))
                .andExpect(jsonPath("$.city", is("Lima")))
                .andExpect(jsonPath("$.telephone", is("999888777")));
    }

    @Test
    public void testFindOwnerOK() throws Exception {
        OwnerDTO newOwner = OwnerDTO.builder()
                .firstName("Ana")
                .lastName("Torres")
                .address("Calle Sol 456")
                .city("Arequipa")
                .telephone("987654321")
                .build();

        ResultActions mvcActions = mockMvc.perform(post("/owners")
                        .content(om.writeValueAsString(newOwner))
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        String response = mvcActions.andReturn().getResponse().getContentAsString();
        Integer id = JsonPath.parse(response).read("$.id");

        mockMvc.perform(get("/owners/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(id)))
                .andExpect(jsonPath("$.firstName", is("Ana")))
                .andExpect(jsonPath("$.lastName", is("Torres")));
    }

    @Test
    public void testFindOwnerKO() throws Exception {
        mockMvc.perform(get("/owners/99999"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testUpdateOwner() throws Exception {
        OwnerDTO newOwner = OwnerDTO.builder()
                .firstName("Luis")
                .lastName("Lopez")
                .address("Jr. Norte 100")
                .city("Cusco")
                .telephone("900111222")
                .build();

        ResultActions mvcActions = mockMvc.perform(post("/owners")
                        .content(om.writeValueAsString(newOwner))
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        String response = mvcActions.andReturn().getResponse().getContentAsString();
        Integer id = JsonPath.parse(response).read("$.id");

        OwnerDTO updateOwner = OwnerDTO.builder()
                .id(id.longValue())
                .firstName("Luis Alberto")
                .lastName("Lopez Perez")
                .address("Jr. Sur 200")
                .city("Trujillo")
                .telephone("911222333")
                .build();

        mockMvc.perform(put("/owners/" + id)
                        .content(om.writeValueAsString(updateOwner))
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(id)))
                .andExpect(jsonPath("$.firstName", is("Luis Alberto")))
                .andExpect(jsonPath("$.lastName", is("Lopez Perez")))
                .andExpect(jsonPath("$.address", is("Jr. Sur 200")))
                .andExpect(jsonPath("$.city", is("Trujillo")))
                .andExpect(jsonPath("$.telephone", is("911222333")));
    }

    @Test
    public void testDeleteOwner() throws Exception {
        OwnerDTO newOwner = OwnerDTO.builder()
                .firstName("Mario")
                .lastName("Salas")
                .address("Av. Peru 500")
                .city("Lima")
                .telephone("922333444")
                .build();

        ResultActions mvcActions = mockMvc.perform(post("/owners")
                        .content(om.writeValueAsString(newOwner))
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        String response = mvcActions.andReturn().getResponse().getContentAsString();
        Integer id = JsonPath.parse(response).read("$.id");

        mockMvc.perform(delete("/owners/" + id))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteOwnerKO() throws Exception {
        mockMvc.perform(delete("/owners/99999"))
                .andExpect(status().isNotFound());
    }
}