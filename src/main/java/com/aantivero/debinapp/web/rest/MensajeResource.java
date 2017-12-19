package com.aantivero.debinapp.web.rest;

import com.aantivero.debinapp.domain.Cuenta;
import com.aantivero.debinapp.domain.User;
import com.aantivero.debinapp.domain.enumeration.EstadoMensaje;
import com.aantivero.debinapp.repository.CuentaRepository;
import com.aantivero.debinapp.repository.UserRepository;
import com.aantivero.debinapp.security.SecurityUtils;
import com.aantivero.debinapp.web.rest.errors.InternalServerErrorException;
import com.codahale.metrics.annotation.Timed;
import com.aantivero.debinapp.domain.Mensaje;

import com.aantivero.debinapp.repository.MensajeRepository;
import com.aantivero.debinapp.web.rest.errors.BadRequestAlertException;
import com.aantivero.debinapp.web.rest.util.HeaderUtil;
import com.aantivero.debinapp.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Mensaje.
 */
@RestController
@RequestMapping("/api")
public class MensajeResource {

    private final Logger log = LoggerFactory.getLogger(MensajeResource.class);

    private static final String ENTITY_NAME = "mensaje";

    private final MensajeRepository mensajeRepository;

    private final UserRepository userRepository;

    private final CuentaRepository cuentaRepository;

    public MensajeResource(MensajeRepository mensajeRepository, UserRepository userRepository, CuentaRepository cuentaRepository) {
        this.mensajeRepository = mensajeRepository;
        this.userRepository = userRepository;
        this.cuentaRepository = cuentaRepository;
    }

    /**
     * POST  /mensajes : Create a new mensaje.
     *
     * @param mensaje the mensaje to create
     * @return the ResponseEntity with status 201 (Created) and with body the new mensaje, or with status 400 (Bad Request) if the mensaje has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/mensajes")
    @Timed
    public ResponseEntity<Mensaje> createMensaje(@Valid @RequestBody Mensaje mensaje) throws URISyntaxException {
        log.debug("REST request to save Mensaje : {}", mensaje);
        if (mensaje.getId() != null) {
            throw new BadRequestAlertException("A new mensaje cannot already have an ID", ENTITY_NAME, "idexists");
        }
        final String userLogin = SecurityUtils.getCurrentUserLogin().orElseThrow(() -> new InternalServerErrorException("Current user login not found"));
        Optional<User> user = userRepository.findOneByLogin(userLogin);
        if (!user.isPresent()) {
            throw new InternalServerErrorException("User could not be found");
        }
        mensaje.setEmisor(user.get());
        Cuenta cuentaEmisor = cuentaRepository.findByUserIsCurrentUser().get(0);
        mensaje.setCuentaEmisor(cuentaEmisor);
        User receptor = mensaje.getReceptor();
        Cuenta cuentaReceptor = null;
        if (receptor.getId() == null) {
            Optional<User> receptorByLogin = userRepository.findOneByLogin(receptor.getLogin());
            cuentaReceptor = cuentaRepository.findCuentasByUser(receptorByLogin.get()).get(0);
        } else {
            cuentaReceptor = cuentaRepository.findCuentasByUser(receptor).get(0);
        }

        mensaje.setCuentaReceptor(cuentaReceptor);
        mensaje.setEstado(EstadoMensaje.ENVIADO);
        Mensaje result = mensajeRepository.save(mensaje);
        return ResponseEntity.created(new URI("/api/mensajes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /mensajes : Updates an existing mensaje.
     *
     * @param mensaje the mensaje to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated mensaje,
     * or with status 400 (Bad Request) if the mensaje is not valid,
     * or with status 500 (Internal Server Error) if the mensaje couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/mensajes")
    @Timed
    public ResponseEntity<Mensaje> updateMensaje(@Valid @RequestBody Mensaje mensaje) throws URISyntaxException {
        log.debug("REST request to update Mensaje : {}", mensaje);
        if (mensaje.getId() == null) {
            return createMensaje(mensaje);
        }
        Mensaje result = mensajeRepository.save(mensaje);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, mensaje.getId().toString()))
            .body(result);
    }

    /**
     * GET  /mensajes : get all the mensajes.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of mensajes in body
     */
    @GetMapping("/mensajes")
    @Timed
    public ResponseEntity<List<Mensaje>> getAllMensajes(Pageable pageable) {
        log.debug("REST request to get a page of Mensajes");
        Page<Mensaje> page = mensajeRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/mensajes");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /mensajes/:id : get the "id" mensaje.
     *
     * @param id the id of the mensaje to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the mensaje, or with status 404 (Not Found)
     */
    @GetMapping("/mensajes/{id}")
    @Timed
    public ResponseEntity<Mensaje> getMensaje(@PathVariable Long id) {
        log.debug("REST request to get Mensaje : {}", id);
        Mensaje mensaje = mensajeRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(mensaje));
    }

    /**
     * DELETE  /mensajes/:id : delete the "id" mensaje.
     *
     * @param id the id of the mensaje to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/mensajes/{id}")
    @Timed
    public ResponseEntity<Void> deleteMensaje(@PathVariable Long id) {
        log.debug("REST request to delete Mensaje : {}", id);
        mensajeRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
