package com.aantivero.debinapp.web.rest;

import com.aantivero.debinapp.DebinappApp;

import com.aantivero.debinapp.domain.Mensaje;
import com.aantivero.debinapp.repository.CuentaRepository;
import com.aantivero.debinapp.repository.MensajeRepository;
import com.aantivero.debinapp.repository.UserRepository;
import com.aantivero.debinapp.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.List;

import static com.aantivero.debinapp.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.aantivero.debinapp.domain.enumeration.EstadoMensaje;
import com.aantivero.debinapp.domain.enumeration.TipoMensaje;
/**
 * Test class for the MensajeResource REST controller.
 *
 * @see MensajeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DebinappApp.class)
public class MensajeResourceIntTest {

    private static final EstadoMensaje DEFAULT_ESTADO = EstadoMensaje.CREADO;
    private static final EstadoMensaje UPDATED_ESTADO = EstadoMensaje.ENVIADO;

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_MONTO = new BigDecimal(1);
    private static final BigDecimal UPDATED_MONTO = new BigDecimal(2);

    private static final String DEFAULT_COMENTARIO = "AAAAAAAAAA";
    private static final String UPDATED_COMENTARIO = "BBBBBBBBBB";

    private static final String DEFAULT_MOTIVO = "AAAAAAAAAA";
    private static final String UPDATED_MOTIVO = "BBBBBBBBBB";

    private static final TipoMensaje DEFAULT_TIPO = TipoMensaje.COBRO;
    private static final TipoMensaje UPDATED_TIPO = TipoMensaje.PAGO;

    @Autowired
    private MensajeRepository mensajeRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restMensajeMockMvc;

    private Mensaje mensaje;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CuentaRepository cuentaRepository;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MensajeResource mensajeResource = new MensajeResource(mensajeRepository, userRepository, cuentaRepository);
        this.restMensajeMockMvc = MockMvcBuilders.standaloneSetup(mensajeResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Mensaje createEntity(EntityManager em) {
        Mensaje mensaje = new Mensaje()
            .estado(DEFAULT_ESTADO)
            .descripcion(DEFAULT_DESCRIPCION)
            .monto(DEFAULT_MONTO)
            .comentario(DEFAULT_COMENTARIO)
            .motivo(DEFAULT_MOTIVO)
            .tipo(DEFAULT_TIPO);
        return mensaje;
    }

    @Before
    public void initTest() {
        mensaje = createEntity(em);
    }

    @Test
    @Transactional
    public void createMensaje() throws Exception {
        int databaseSizeBeforeCreate = mensajeRepository.findAll().size();

        // Create the Mensaje
        restMensajeMockMvc.perform(post("/api/mensajes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mensaje)))
            .andExpect(status().isCreated());

        // Validate the Mensaje in the database
        List<Mensaje> mensajeList = mensajeRepository.findAll();
        assertThat(mensajeList).hasSize(databaseSizeBeforeCreate + 1);
        Mensaje testMensaje = mensajeList.get(mensajeList.size() - 1);
        assertThat(testMensaje.getEstado()).isEqualTo(DEFAULT_ESTADO);
        assertThat(testMensaje.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
        assertThat(testMensaje.getMonto()).isEqualTo(DEFAULT_MONTO);
        assertThat(testMensaje.getComentario()).isEqualTo(DEFAULT_COMENTARIO);
        assertThat(testMensaje.getMotivo()).isEqualTo(DEFAULT_MOTIVO);
        assertThat(testMensaje.getTipo()).isEqualTo(DEFAULT_TIPO);
    }

    @Test
    @Transactional
    public void createMensajeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = mensajeRepository.findAll().size();

        // Create the Mensaje with an existing ID
        mensaje.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMensajeMockMvc.perform(post("/api/mensajes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mensaje)))
            .andExpect(status().isBadRequest());

        // Validate the Mensaje in the database
        List<Mensaje> mensajeList = mensajeRepository.findAll();
        assertThat(mensajeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkEstadoIsRequired() throws Exception {
        int databaseSizeBeforeTest = mensajeRepository.findAll().size();
        // set the field null
        mensaje.setEstado(null);

        // Create the Mensaje, which fails.

        restMensajeMockMvc.perform(post("/api/mensajes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mensaje)))
            .andExpect(status().isBadRequest());

        List<Mensaje> mensajeList = mensajeRepository.findAll();
        assertThat(mensajeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTipoIsRequired() throws Exception {
        int databaseSizeBeforeTest = mensajeRepository.findAll().size();
        // set the field null
        mensaje.setTipo(null);

        // Create the Mensaje, which fails.

        restMensajeMockMvc.perform(post("/api/mensajes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mensaje)))
            .andExpect(status().isBadRequest());

        List<Mensaje> mensajeList = mensajeRepository.findAll();
        assertThat(mensajeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMensajes() throws Exception {
        // Initialize the database
        mensajeRepository.saveAndFlush(mensaje);

        // Get all the mensajeList
        restMensajeMockMvc.perform(get("/api/mensajes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mensaje.getId().intValue())))
            .andExpect(jsonPath("$.[*].estado").value(hasItem(DEFAULT_ESTADO.toString())))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION.toString())))
            .andExpect(jsonPath("$.[*].monto").value(hasItem(DEFAULT_MONTO.intValue())))
            .andExpect(jsonPath("$.[*].comentario").value(hasItem(DEFAULT_COMENTARIO.toString())))
            .andExpect(jsonPath("$.[*].motivo").value(hasItem(DEFAULT_MOTIVO.toString())))
            .andExpect(jsonPath("$.[*].tipo").value(hasItem(DEFAULT_TIPO.toString())));
    }

    @Test
    @Transactional
    public void getMensaje() throws Exception {
        // Initialize the database
        mensajeRepository.saveAndFlush(mensaje);

        // Get the mensaje
        restMensajeMockMvc.perform(get("/api/mensajes/{id}", mensaje.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(mensaje.getId().intValue()))
            .andExpect(jsonPath("$.estado").value(DEFAULT_ESTADO.toString()))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION.toString()))
            .andExpect(jsonPath("$.monto").value(DEFAULT_MONTO.intValue()))
            .andExpect(jsonPath("$.comentario").value(DEFAULT_COMENTARIO.toString()))
            .andExpect(jsonPath("$.motivo").value(DEFAULT_MOTIVO.toString()))
            .andExpect(jsonPath("$.tipo").value(DEFAULT_TIPO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingMensaje() throws Exception {
        // Get the mensaje
        restMensajeMockMvc.perform(get("/api/mensajes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMensaje() throws Exception {
        // Initialize the database
        mensajeRepository.saveAndFlush(mensaje);
        int databaseSizeBeforeUpdate = mensajeRepository.findAll().size();

        // Update the mensaje
        Mensaje updatedMensaje = mensajeRepository.findOne(mensaje.getId());
        // Disconnect from session so that the updates on updatedMensaje are not directly saved in db
        em.detach(updatedMensaje);
        updatedMensaje
            .estado(UPDATED_ESTADO)
            .descripcion(UPDATED_DESCRIPCION)
            .monto(UPDATED_MONTO)
            .comentario(UPDATED_COMENTARIO)
            .motivo(UPDATED_MOTIVO)
            .tipo(UPDATED_TIPO);

        restMensajeMockMvc.perform(put("/api/mensajes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedMensaje)))
            .andExpect(status().isOk());

        // Validate the Mensaje in the database
        List<Mensaje> mensajeList = mensajeRepository.findAll();
        assertThat(mensajeList).hasSize(databaseSizeBeforeUpdate);
        Mensaje testMensaje = mensajeList.get(mensajeList.size() - 1);
        assertThat(testMensaje.getEstado()).isEqualTo(UPDATED_ESTADO);
        assertThat(testMensaje.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
        assertThat(testMensaje.getMonto()).isEqualTo(UPDATED_MONTO);
        assertThat(testMensaje.getComentario()).isEqualTo(UPDATED_COMENTARIO);
        assertThat(testMensaje.getMotivo()).isEqualTo(UPDATED_MOTIVO);
        assertThat(testMensaje.getTipo()).isEqualTo(UPDATED_TIPO);
    }

    @Test
    @Transactional
    public void updateNonExistingMensaje() throws Exception {
        int databaseSizeBeforeUpdate = mensajeRepository.findAll().size();

        // Create the Mensaje

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restMensajeMockMvc.perform(put("/api/mensajes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mensaje)))
            .andExpect(status().isCreated());

        // Validate the Mensaje in the database
        List<Mensaje> mensajeList = mensajeRepository.findAll();
        assertThat(mensajeList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteMensaje() throws Exception {
        // Initialize the database
        mensajeRepository.saveAndFlush(mensaje);
        int databaseSizeBeforeDelete = mensajeRepository.findAll().size();

        // Get the mensaje
        restMensajeMockMvc.perform(delete("/api/mensajes/{id}", mensaje.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Mensaje> mensajeList = mensajeRepository.findAll();
        assertThat(mensajeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Mensaje.class);
        Mensaje mensaje1 = new Mensaje();
        mensaje1.setId(1L);
        Mensaje mensaje2 = new Mensaje();
        mensaje2.setId(mensaje1.getId());
        assertThat(mensaje1).isEqualTo(mensaje2);
        mensaje2.setId(2L);
        assertThat(mensaje1).isNotEqualTo(mensaje2);
        mensaje1.setId(null);
        assertThat(mensaje1).isNotEqualTo(mensaje2);
    }
}
