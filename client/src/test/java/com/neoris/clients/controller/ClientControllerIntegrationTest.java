package com.neoris.clients.controller;

import com.neoris.clients.client.entity.Client;
import com.neoris.clients.client.repository.IClientRepository;
import com.neoris.clients.vo.ClientVo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Random;

import static com.neoris.clients.client.common.ClientConstants.V1_API_VERSION;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Random;

/**
 * @author jyepez on 27/10/2024
 */
@ExtendWith(SpringExtension.class)
@Testcontainers
@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class ClientControllerIntegrationTest {

    private static final MySQLContainer<?> mysqlContainer = new MySQLContainer<>("mysql:latest");
    private static final Random RANDOM = new Random();
    private static final String IDENTIFICATION1 = "ID00"+RANDOM.nextInt(100);
    private static final String IDENTIFICATION2 = "ID00001";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private IClientRepository clientRepository;

    ClientVo client1;

    @BeforeAll
    public static void startContainer() {
        mysqlContainer.start();
    }

    @AfterAll
    public static void stopContainer() {
        mysqlContainer.stop();
    }

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
        System.setProperty("spring.datasource.url", mysqlContainer.getJdbcUrl());
        System.setProperty("spring.datasource.username", mysqlContainer.getUsername());
        System.setProperty("spring.datasource.password", mysqlContainer.getPassword());

        client1 = ClientVo.builder()
                .name("Jonathan Yépez")
                .address("656 Main St")
                .phone("123-456-7890")
                .password("password1@")
                .identification(IDENTIFICATION1)
                .createdBy("admin")
                .build();
    }

    @AfterEach
    void tearDown() {
        Client client = this.clientRepository.findById(1).orElse(null);
        assert client != null;
        client.setStatus(Boolean.TRUE);
        this.clientRepository.save(client);
    }

    @Order(1)
    @Test
    public void testFindAll() throws Exception {
        mockMvc.perform(get("/client"+V1_API_VERSION))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Order(2)
    @Test
    public void testCreateClient() throws Exception {
        mockMvc.perform(post("/client"+V1_API_VERSION)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(client1)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value(true));
    }

    @Order(3)
    @Test
    public void testFindById() throws Exception {
        mockMvc.perform(get("/client"+V1_API_VERSION+"/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Order(4)
    @Test
    public void testFindByIdFailed() throws Exception {
        mockMvc.perform(get("/client"+V1_API_VERSION+"/{id}", 99))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Order(5)
    @Test
    public void testFindByIdentification() throws Exception {
        mockMvc.perform(get("/client"+V1_API_VERSION+"/findByIdentification").param("identification", IDENTIFICATION2))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Order(6)
    @Test
    public void testUpdateClient() throws Exception {
        ClientVo clientVo = ClientVo.builder()
                .name("Jonathan Andres Yépez")
                .address("6456 Main St")
                .phone("987-654-3210")
                .password("newpassword@")
                .identification(IDENTIFICATION2)
                .createdBy("admin")
                .status(true)
                .build();

        mockMvc.perform(put("/client"+V1_API_VERSION)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(clientVo)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Order(7)
    @Test
    public void testDeleteClient() throws Exception {
        mockMvc.perform(delete("/client"+V1_API_VERSION)
                        .param("identification", IDENTIFICATION2))
                .andExpect(status().isOk());
    }
}
