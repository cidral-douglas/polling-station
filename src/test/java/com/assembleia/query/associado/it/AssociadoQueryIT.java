package com.assembleia.query.associado.it;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AssociadoQueryIT {

  @Autowired
  private WebApplicationContext webApplicationContext;

  @Autowired
  private MockMvc mockMvc;

  @BeforeEach
  public void setup() {
    mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
  }

  @Test
  @Sql(statements = "INSERT INTO associado (id, nome, cpf, deleted) VALUES ('bff04fb2-7363-11ed-a1eb-0242ac120002', 'Charles', '14396759029', false)")
  @Sql(statements = "DELETE FROM associado WHERE id='bff04fb2-7363-11ed-a1eb-0242ac120002'", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
  public void deveRetornarAssociadoPeloId() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders
            .get("/api/v1/associados/bff04fb2-7363-11ed-a1eb-0242ac120002")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.nome", is("Charles")))
        .andExpect(jsonPath("$.cpf", is("14396759029")));
  }

  @Test
  @Sql(statements = "INSERT INTO associado (id, nome, cpf, deleted) VALUES ('bff04fb2-7363-11ed-a1eb-0242ac120002', 'Charles', '14396759029', false)")
  @Sql(statements = "DELETE FROM associado WHERE id='bff04fb2-7363-11ed-a1eb-0242ac120002'", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
  public void deveRetornarAssociados() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders
            .get("/api/v1/associados")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[*].nome", hasItem("Charles")))
        .andExpect(jsonPath("$[*].cpf", hasItem("14396759029")));
  }
}
