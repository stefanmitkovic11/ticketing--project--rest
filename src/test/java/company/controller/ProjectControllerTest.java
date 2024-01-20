package company.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import company.dto.ProjectDTO;
import company.dto.RoleDTO;
import company.dto.UserDTO;
import company.enums.Gender;
import company.enums.Status;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ProjectControllerTest {

    @Autowired
    private MockMvc mockMvc;

    static UserDTO userDTO;
    static ProjectDTO projectDTO;

    static String token;

    @BeforeAll
    static void setUp() {

        token = "Bearer eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJOYWlHY0UtVE5OdzhvTWZ1TUFXSERPSnhwSWdtNnVTRHhMLVRVTGNEN0tZIn0.eyJleHAiOjE3MDU3NzQyNzMsImlhdCI6MTcwNTc1NjI3MywianRpIjoiMTU5ODNhYjYtMGE0OC00ODI1LWJkOWMtNTZhYmM2NzQyNTkwIiwiaXNzIjoiaHR0cDovL2xvY2FsaG9zdDo4MDgwL2F1dGgvcmVhbG1zL3NjaG9vbC1kZXYiLCJhdWQiOiJhY2NvdW50Iiwic3ViIjoiNjg3MGM0Y2YtNDI3NC00MWFlLWFkOGEtYzgxZWQwYThiZWNiIiwidHlwIjoiQmVhcmVyIiwiYXpwIjoidGlja2V0aW5nLWFwcCIsInNlc3Npb25fc3RhdGUiOiIzNDZlNTA4Ny0zYWRlLTRkMDEtYjQwOC05NjdjZjAyMjg4YjkiLCJhY3IiOiIxIiwiYWxsb3dlZC1vcmlnaW5zIjpbImh0dHA6Ly9sb2NhbGhvc3Q6ODE4MSJdLCJyZWFsbV9hY2Nlc3MiOnsicm9sZXMiOlsiZGVmYXVsdC1yb2xlcy1zY2hvb2wgZGV2Iiwib2ZmbGluZV9hY2Nlc3MiLCJ1bWFfYXV0aG9yaXphdGlvbiJdfSwicmVzb3VyY2VfYWNjZXNzIjp7InRpY2tldGluZy1hcHAiOnsicm9sZXMiOlsiTWFuYWdlciJdfSwiYWNjb3VudCI6eyJyb2xlcyI6WyJtYW5hZ2UtYWNjb3VudCIsIm1hbmFnZS1hY2NvdW50LWxpbmtzIiwidmlldy1wcm9maWxlIl19fSwic2NvcGUiOiJvcGVuaWQgcHJvZmlsZSBlbWFpbCIsInNpZCI6IjM0NmU1MDg3LTNhZGUtNGQwMS1iNDA4LTk2N2NmMDIyODhiOSIsImVtYWlsX3ZlcmlmaWVkIjp0cnVlLCJwcmVmZXJyZWRfdXNlcm5hbWUiOiJtaWtlIn0.YoLb5WQTwO5d2g7BFeVG7IAl4h82qZKwrJMDjvWrQ7LhpTMIAJsWtIG903f1YniHE_TvBwSOxrIjE0hRQ0YAghBITdSi23y6nmpfaZpweBjlM5yYjvWztXWEDHv8Pzb3cQdqnYcaRikGRrn7WPS3esJfuoOwAfa-R-Hcrc16KTAz1E5nm7cTUS0jEFOR_OjLe3S4SyPNQde--U-DbyXtCtiPJGTTC3L1JIhkOMsRsZKWQd2M2JxImxX6abSpK7PUjMS46Ge0BstLxjRnWkRS0ccOlc28UZG1HAAixZqVDNzxzVPDztJM1-JOUl1m9mgcIbOppB9WaIMihszFy-S2pA";

        userDTO = UserDTO.builder()
                .id(1L)
                .firstName("mike")
                .lastName("mike")
                .userName("mike")
                .passWord("abc1")
                .confirmPassWord("abc1")
                .role(new RoleDTO(2L, "Manager"))
                .gender(Gender.MALE)
                .build();


        projectDTO = ProjectDTO.builder()
                .projectCode("Api1")
                .projectName("Api-ozzy")
                .assignedManager(userDTO)
                .projectDetail("Project Details")
                .startDate(LocalDate.now())
                .endDate(LocalDate.now().plusDays(5))
                .projectStatus(Status.OPEN)
                .build();

    }


    @Test
    public void givenNoToken_whenGetRequest() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/project")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());

    }

    @Test
    public void givenToken_whenGetRequest() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/project")
                        .header("Authorization", token)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$.data[0].projectCode").exists())
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$.data[0].assignedManager.userName").isNotEmpty());
    }


    @Test
    public void givenToken_createProject() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/project")
                        .header("Authorization", token)
                        .content(toJsonString(projectDTO))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful());
    }


    @Test
    public void givenToken_deleteProject() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/api/v1/project/" + projectDTO.getProjectCode())
                        .header("Authorization", token)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }


    private static String toJsonString(final Object obj) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
            objectMapper.registerModule(new JavaTimeModule());
            return objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}