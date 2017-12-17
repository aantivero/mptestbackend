package com.aantivero.debinapp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

import com.aantivero.debinapp.domain.enumeration.EstadoMensaje;

import com.aantivero.debinapp.domain.enumeration.TipoMensaje;

/**
 * A Mensaje.
 */
@Entity
@Table(name = "mensaje")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Mensaje implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false)
    private EstadoMensaje estado;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "monto", precision=10, scale=2)
    private BigDecimal monto;

    @Column(name = "comentario")
    private String comentario;

    @Column(name = "motivo")
    private String motivo;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo", nullable = false)
    private TipoMensaje tipo;

    @ManyToOne
    private Cuenta cuentaEmisor;

    @ManyToOne
    private Cuenta cuentaReceptor;

    @ManyToOne
    private User emisor;

    @ManyToOne
    private User receptor;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EstadoMensaje getEstado() {
        return estado;
    }

    public Mensaje estado(EstadoMensaje estado) {
        this.estado = estado;
        return this;
    }

    public void setEstado(EstadoMensaje estado) {
        this.estado = estado;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Mensaje descripcion(String descripcion) {
        this.descripcion = descripcion;
        return this;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public Mensaje monto(BigDecimal monto) {
        this.monto = monto;
        return this;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }

    public String getComentario() {
        return comentario;
    }

    public Mensaje comentario(String comentario) {
        this.comentario = comentario;
        return this;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public String getMotivo() {
        return motivo;
    }

    public Mensaje motivo(String motivo) {
        this.motivo = motivo;
        return this;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public TipoMensaje getTipo() {
        return tipo;
    }

    public Mensaje tipo(TipoMensaje tipo) {
        this.tipo = tipo;
        return this;
    }

    public void setTipo(TipoMensaje tipo) {
        this.tipo = tipo;
    }

    public Cuenta getCuentaEmisor() {
        return cuentaEmisor;
    }

    public Mensaje cuentaEmisor(Cuenta cuenta) {
        this.cuentaEmisor = cuenta;
        return this;
    }

    public void setCuentaEmisor(Cuenta cuenta) {
        this.cuentaEmisor = cuenta;
    }

    public Cuenta getCuentaReceptor() {
        return cuentaReceptor;
    }

    public Mensaje cuentaReceptor(Cuenta cuenta) {
        this.cuentaReceptor = cuenta;
        return this;
    }

    public void setCuentaReceptor(Cuenta cuenta) {
        this.cuentaReceptor = cuenta;
    }

    public User getEmisor() {
        return emisor;
    }

    public Mensaje emisor(User user) {
        this.emisor = user;
        return this;
    }

    public void setEmisor(User user) {
        this.emisor = user;
    }

    public User getReceptor() {
        return receptor;
    }

    public Mensaje receptor(User user) {
        this.receptor = user;
        return this;
    }

    public void setReceptor(User user) {
        this.receptor = user;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Mensaje mensaje = (Mensaje) o;
        if (mensaje.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), mensaje.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Mensaje{" +
            "id=" + getId() +
            ", estado='" + getEstado() + "'" +
            ", descripcion='" + getDescripcion() + "'" +
            ", monto=" + getMonto() +
            ", comentario='" + getComentario() + "'" +
            ", motivo='" + getMotivo() + "'" +
            ", tipo='" + getTipo() + "'" +
            "}";
    }
}
