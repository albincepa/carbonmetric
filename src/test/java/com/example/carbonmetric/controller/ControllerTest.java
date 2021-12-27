package com.example.carbonmetric.controller;

import com.example.carbonmetric.service.IMetricService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.UUID;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ControllerTest {

    @MockBean
    private IMetricService service;

    @Autowired
    private MockMvc mvc;

    @Test
    void saveMeasurements() throws Exception {
        UUID uuid = UUID.randomUUID();
        mvc.perform(MockMvcRequestBuilders.post("/api/v1/sensors/" + uuid + "/mesurements")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                        "co2" : 2000,
                        "time" : "2019-02-01T18:55:47+00:00"
                        }""")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }
}