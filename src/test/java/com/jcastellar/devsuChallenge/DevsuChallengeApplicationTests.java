package com.jcastellar.devsuChallenge;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import com.jcastellar.devsuChallenge.dto.ClienteDTO;
import com.jcastellar.devsuChallenge.service.ClienteService;
import com.jcastellar.devsuChallenge.service.MovimientoService;
import com.jcastellar.devsuChallenge.service.ReporteService;
import com.jcastellar.devsuChallenge.service.impl.ReporteServiceImpl;
import com.jcastellar.devsuChallenge.utility.enumerador.Genero;
import com.jcastellar.devsuChallenge.utility.mapper.CuentaMapper;
import com.jcastellar.devsuChallenge.utility.mapper.MovimientoMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@SpringBootTest
@AutoConfigureMockMvc
class DevsuChallengeApplicationTests {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ClienteService clienteService;

  @Autowired
  private ReporteService reporteService;

  @Autowired
  private MovimientoService movimientoService;

  @Autowired
  private MovimientoMapper movimientoMapper;

  @Autowired
  private CuentaMapper cuentaMapper;

  @Test
  void contextLoads() {
  }

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    reporteService = new ReporteServiceImpl(clienteService, movimientoService, movimientoMapper);
  }

  @Test
  public void createNewClient() throws Exception {
    mockMvc.perform(
        post("/clientes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(
                "{\r\n    \"nombre\": \"Katterin\",\r\n    \"genero\": \"Femenino\",\r\n    \"edad\": 34,\r\n    \"identificacion\": \"1143328859\",\r\n    \"direccion\": \"Cra 55 #12sur-14\",\r\n    \"telefono\": \"3003209919\",\r\n    \"password\": \"123456!\",\r\n    \"estado\": true\r\n}")
    ).andExpect(status().isCreated());
  }

  @Test
  public void createClientWrongName() throws Exception {
    mockMvc.perform(
        post("/clientes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(
                "{\r\n    \"nombre\": \"Ju@n Castellar\",\r\n    \"genero\": \"Masculino\",\r\n    \"edad\": 37,\r\n    \"identificacion\": \"1047378873\",\r\n    \"direccion\": \"Cra 55 #12sur-14\",\r\n    \"telefono\": \"3183030931\",\r\n    \"password\": \"123456!\",\r\n    \"estado\": true\r\n}")
    ).andExpect(status().isBadRequest());
  }

  @Test
  public void createClientWrongGenre() throws Exception {
    mockMvc.perform(
        post("/clientes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(
                "{\r\n    \"nombre\": \"Juan Castellar\",\r\n    \"genero\": \"Pansexual\",\r\n    \"edad\": 37,\r\n    \"identificacion\": \"1047378873\",\r\n    \"direccion\": \"Cra 55 #12sur-14\",\r\n    \"telefono\": \"3183030931\",\r\n    \"password\": \"123456!\",\r\n    \"estado\": true\r\n}")
    ).andExpect(status().isBadRequest());
  }

  @Test
  public void createClientWrongAge() throws Exception {
    mockMvc.perform(
        post("/clientes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(
                "{\r\n    \"nombre\": \"Ju@n Castellar\",\r\n    \"genero\": \"Masculino\",\r\n    \"edad\": 121,\r\n    \"identificacion\": \"1047378873\",\r\n    \"direccion\": \"Cra 55 #12sur-14\",\r\n    \"telefono\": \"3183030931\",\r\n    \"password\": \"123456!\",\r\n    \"estado\": true\r\n}")
    ).andExpect(status().isBadRequest());
  }

  @Test
  public void createClientWrongIdentification() throws Exception {
    mockMvc.perform(
        post("/clientes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(
                "{\r\n    \"nombre\": \"Ju@n Castellar\",\r\n    \"genero\": \"Masculino\",\r\n    \"edad\": 37,\r\n    \"identificacion\": \"1047378AAA\",\r\n    \"direccion\": \"Cra 55 #12sur-14\",\r\n    \"telefono\": \"3183030931\",\r\n    \"password\": \"123456!\",\r\n    \"estado\": true\r\n}")
    ).andExpect(status().isBadRequest());
  }

  @Test
  public void createClientWrongStatus() throws Exception {
    mockMvc.perform(
        post("/clientes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(
                "{\r\n    \"nombre\": \"Ju@n Castellar\",\r\n    \"genero\": \"Masculino\",\r\n    \"edad\": 37,\r\n    \"identificacion\": \"1047378873\",\r\n    \"direccion\": \"Cra 55 #12sur-14\",\r\n    \"telefono\": \"3183030931\",\r\n    \"password\": \"123456!\",\r\n    \"estado\": tru\r\n}")
    ).andExpect(status().isBadRequest());
  }


  @Test
  public void createExistingClient() throws Exception {
    // Crear un cliente existente en la base de datos
    ClienteDTO existingCliente = new ClienteDTO();
    existingCliente.setNombre("Juan Castellar");
    existingCliente.setGenero(Genero.MASCULINO);
    existingCliente.setEdad(37);
    existingCliente.setIdentificacion("1047378873");
    existingCliente.setDireccion("Cra 55 #12sur-14");
    existingCliente.setTelefono("3183030931");
    existingCliente.setPassword("123456!");
    existingCliente.setEstado(true);
    clienteService.createCliente(existingCliente);

    // Crear un cliente con los mismos datos existentes
    ClienteDTO newCliente = new ClienteDTO();
    newCliente.setNombre("Juan Castellar");
    newCliente.setGenero(Genero.MASCULINO);
    newCliente.setEdad(37);
    newCliente.setIdentificacion("1047378873");
    newCliente.setDireccion("Cra 55 #12sur-14");
    newCliente.setTelefono("3183030931");
    newCliente.setPassword("123456!");
    newCliente.setEstado(true);

    // Realizar la solicitud POST para crear el cliente
    ResultActions result = mockMvc.perform(
        post("/clientes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(
                "{\r\n    \"nombre\": \"Juan Castellar\",\r\n    \"genero\": \"Masculino\",\r\n    \"edad\": 37,\r\n    \"identificacion\": \"1047378873\",\r\n    \"direccion\": \"Cra 55 #12sur-14\",\r\n    \"telefono\": \"3183030931\",\r\n    \"password\": \"123456!\",\r\n    \"estado\": true\r\n}")
    ).andExpect(status().isConflict());
  }

  @Test
  public void getClients() throws Exception {
    mockMvc.perform(
        get("/clientes")
    ).andExpect(status().isOk());
  }

  @Test
  public void getClientById() throws Exception {
    mockMvc.perform(
        get("/clientes/1")
    ).andExpect(status().isOk());
  }

  @Test
  public void getInexistentClientById() throws Exception {
    mockMvc.perform(
        get("/clientes/100")
    ).andExpect(status().isNotFound());
  }

  @Test
  public void updateClient() throws Exception {
    mockMvc.perform(
        put("/clientes/1")
            .contentType(MediaType.APPLICATION_JSON)
            .content(
                "{\r\n    \"nombre\": \"Juan David Castellar Roa\",\r\n    \"genero\": \"Masculino\",\r\n    \"edad\": 38,\r\n    \"identificacion\": \"1047378873\",\r\n    \"direccion\": \"Cra 55 #12sur-14\",\r\n    \"telefono\": \"3183030931\",\r\n    \"password\": \"123456!\",\r\n    \"estado\": true\r\n}")
    ).andExpect(status().isOk());
  }

  @Test
  public void updateUnexistingClient() throws Exception {
    mockMvc.perform(
        put("/clientes/100")
            .contentType(MediaType.APPLICATION_JSON)
            .content(
                "{\r\n    \"nombre\": \"Juan David Castellar Roa\",\r\n    \"genero\": \"Masculino\",\r\n    \"edad\": 38,\r\n    \"identificacion\": \"1047378873\",\r\n    \"direccion\": \"Cra 55 #12sur-14\",\r\n    \"telefono\": \"3183030931\",\r\n    \"password\": \"123456!\",\r\n    \"estado\": true\r\n}")
    ).andExpect(status().isNotFound());
  }

  @Test
  public void updateClientByFields() throws Exception {
    mockMvc.perform(
        patch("/clientes/1")
            .contentType(MediaType.APPLICATION_JSON)
            .content(
                "{\r\n    \"edad\": 37,\r\n   \"estado\": false\r\n}")
    ).andExpect(status().isOk());
  }

  @Test
  public void createNewAccount() throws Exception {
    mockMvc.perform(
        post("/cuentas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(
                "{\r\n" +
                    "  \"numeroCuenta\": \"54200000562\",\r\n" +
                    "  \"tipoCuenta\": \"Ahorro\",\r\n" +
                    "  \"saldoInicial\": 100.5,\r\n" +
                    "  \"estado\":\"True\",\r\n" +
                    "  \"cliente\" : {\r\n" +
                    "      \"identificacion\":\"1047378873\"\r\n" +
                    "  }\r\n" +
                    "}")).andExpect(status().isCreated());
  }

  @Test
  public void createNewDepositTransaction() throws Exception {
    mockMvc.perform(
        post("/movimientos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(
                "{\r\n" +
                    "  \"tipoMovimiento\": \"Deposito\",\r\n" +
                    "  \"valor\": 1000.5,\r\n" +
                    "  \"cuenta\" : {\r\n" +
                    "      \"numeroCuenta\":\"54200000562\"\r\n" +
                    "  }\r\n" +
                    "}")).andExpect(status().isCreated());
  }
}