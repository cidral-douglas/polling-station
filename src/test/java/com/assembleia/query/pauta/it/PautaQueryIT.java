package com.assembleia.query.pauta.it;

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
public class PautaQueryIT {

  @Autowired
  private WebApplicationContext webApplicationContext;

  @Autowired
  private MockMvc mockMvc;

  @BeforeEach
  public void setup() {
    mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
  }

  @Test
  @Sql(statements = "INSERT INTO pauta (id, descricao, pergunta, data_limite, status) VALUES ('bff04fb2-7363-11ed-a1eb-0242ac120002', 'Descrição Teste', 'Pergunta Teste', '2022-12-03T21:30:00.00', 'ABERTA')")
  @Sql(statements = "DELETE FROM pauta WHERE id='bff04fb2-7363-11ed-a1eb-0242ac120002'", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
  public void deveRetornarPautaPeloId() throws Exception {

    mockMvc.perform(MockMvcRequestBuilders
            .get("/api/v1/pautas/bff04fb2-7363-11ed-a1eb-0242ac120002")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.descricao", is("Descrição Teste")))
        .andExpect(jsonPath("$.pergunta", is("Pergunta Teste")))
        .andExpect(jsonPath("$.dataLimite", is("2022-12-03T21:30:00")))
        .andExpect(jsonPath("$.status", is("ABERTA")));
  }

  @Test
  @Sql(statements = "INSERT INTO pauta (id, descricao, pergunta, data_limite, status) VALUES ('bff04fb2-7363-11ed-a1eb-0242ac120002', 'Descrição Teste', 'Pergunta Teste', '2022-12-03T21:30:00.00', 'ABERTA')")
  @Sql(statements = "DELETE FROM pauta WHERE id='bff04fb2-7363-11ed-a1eb-0242ac120002'", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
  public void deveRetornarPautas() throws Exception {

    mockMvc.perform(MockMvcRequestBuilders
            .get("/api/v1/pautas")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[*].descricao", hasItem("Descrição Teste")))
        .andExpect(jsonPath("$[*].pergunta", hasItem("Pergunta Teste")))
        .andExpect(jsonPath("$[*].dataLimite", hasItem("2022-12-03T21:30:00")))
        .andExpect(jsonPath("$[*].status", hasItem("ABERTA")));
  }

}
