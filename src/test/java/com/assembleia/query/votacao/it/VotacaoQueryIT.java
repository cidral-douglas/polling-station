package com.assembleia.query.votacao.it;

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

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class VotacaoQueryIT {

  @Autowired
  private WebApplicationContext webApplicationContext;

  @Autowired
  private MockMvc mockMvc;

  private final String insertAssociado = "INSERT INTO associado (id, nome, cpf, deleted) VALUES ('bff04fb2-7363-11ed-a1eb-0242ac120002', 'Charles', '14396759029', false); ";
  private final String insertAssociados = "INSERT INTO associado (id, nome, cpf, deleted) VALUES ('bff04fb2-7363-11ed-a1eb-0242ac120002', 'Charles', '14396759029', false),('bff04fb2-7363-11ed-a1eb-0242ac120001', 'Roberta', '41115189077', false); ";
  private final String insertPautaAberta = "INSERT INTO pauta (id, descricao, pergunta, data_limite, status) VALUES ('f5bb5ab3-1432-4064-8599-34d328112ef6', 'Descrição Teste', 'Pergunta Teste', '2022-12-03T21:30:00.00', 'ABERTA'); ";
  private final String insertPautaFechada = "INSERT INTO pauta (id, descricao, pergunta, data_limite, status) VALUES ('f5bb5ab3-1432-4064-8599-34d328112ef6', 'Descrição Teste', 'Pergunta Teste', '2022-12-03T21:30:00.00', 'FECHADA'); ";
  private final String insertVotacoes = "INSERT INTO votacao (id, associado_id, pauta_id, voto) VALUES ('94f582fd-4600-44bc-9957-916295396327', 'bff04fb2-7363-11ed-a1eb-0242ac120002', 'f5bb5ab3-1432-4064-8599-34d328112ef6', 'NAO'),('95f582fd-4600-44bc-9957-916295396327', 'bff04fb2-7363-11ed-a1eb-0242ac120001', 'f5bb5ab3-1432-4064-8599-34d328112ef6', 'SIM')";
  private final String insertVotacaoSIM = "INSERT INTO votacao (id, associado_id, pauta_id, voto) VALUES ('94f582fd-4600-44bc-9957-916295396327', 'bff04fb2-7363-11ed-a1eb-0242ac120002', 'f5bb5ab3-1432-4064-8599-34d328112ef6', 'SIM')";
  private final String insertVotacaoNAO = "INSERT INTO votacao (id, associado_id, pauta_id, voto) VALUES ('94f582fd-4600-44bc-9957-916295396327', 'bff04fb2-7363-11ed-a1eb-0242ac120002', 'f5bb5ab3-1432-4064-8599-34d328112ef6', 'NAO')";
  private final String deleteAssociados = "DELETE FROM votacao WHERE id='94f582fd-4600-44bc-9957-916295396327'; DELETE FROM votacao WHERE id='95f582fd-4600-44bc-9957-916295396327'; ";
  private final String deleteAssociado = "DELETE FROM votacao WHERE id='94f582fd-4600-44bc-9957-916295396327'; ";
  private final String deletePauta = "DELETE FROM pauta WHERE id='f5bb5ab3-1432-4064-8599-34d328112ef6'; ";
  private final String deleteVotacoes = "DELETE FROM associado WHERE id='bff04fb2-7363-11ed-a1eb-0242ac120002'; DELETE FROM associado WHERE id='bff04fb2-7363-11ed-a1eb-0242ac120001' ";
  private final String deleteVotacao = "DELETE FROM associado WHERE id='bff04fb2-7363-11ed-a1eb-0242ac120002' ";

  @BeforeEach
  public void setup() {
    mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
  }

  @Test
  @Sql(statements = insertAssociado + insertPautaFechada + insertVotacaoSIM)
  @Sql(statements = deleteAssociado + deletePauta + deleteVotacao, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
  public void deveRetornarContagemDeVotosPelaPauta() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders
            .get("/api/v1/votacoes/contagem-de-votos/f5bb5ab3-1432-4064-8599-34d328112ef6")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.votosSim", is(1)))
        .andExpect(jsonPath("$.votosNao", is(0)));
  }

  @Test
  @Sql(statements = insertAssociado + insertPautaFechada + insertVotacaoSIM)
  @Sql(statements = deleteAssociado + deletePauta + deleteVotacao, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
  public void deveRetornarResultadoSIMDaVotacaoPelaPauta() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders
            .get("/api/v1/votacoes/resultado-votacao/f5bb5ab3-1432-4064-8599-34d328112ef6")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.resultado", is("Há mais votos SIM")));
  }

  @Test
  @Sql(statements = insertAssociado + insertPautaFechada + insertVotacaoNAO)
  @Sql(statements = deleteAssociado + deletePauta + deleteVotacao, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
  public void deveRetornarResultadoNAODaVotacaoPelaPauta() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders
            .get("/api/v1/votacoes/resultado-votacao/f5bb5ab3-1432-4064-8599-34d328112ef6")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.resultado", is("Há mais votos NÃO")));
  }

  @Test
  @Sql(statements = insertAssociado + insertPautaAberta + insertVotacaoSIM)
  @Sql(statements = deleteAssociado + deletePauta + deleteVotacao, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
  public void deveRetornarQueAPautaAindaEstaAberta() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders
            .get("/api/v1/votacoes/resultado-votacao/f5bb5ab3-1432-4064-8599-34d328112ef6")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.resultado", is("A votação ainda está aberta")));
  }

  @Test
  @Sql(statements = insertAssociados + insertPautaFechada + insertVotacoes)
  @Sql(statements = deleteAssociados + deletePauta + deleteVotacoes, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
  public void deveRetornarResultadoEmpatadoDaVotacaoPelaPauta() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders
            .get("/api/v1/votacoes/resultado-votacao/f5bb5ab3-1432-4064-8599-34d328112ef6")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.resultado", is("Houve um EMPATE")));
  }

}
