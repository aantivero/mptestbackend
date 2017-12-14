package com.aantivero.debinapp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * A Cuenta.
 */
@Entity
@Table(name = "cuenta")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Cuenta implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "nombre")
    private String nombre;

    @NotNull
    @Size(min = 22, max = 22)
    @Pattern(regexp = "(^[0-9]*$)")
    @Column(name = "cbu", length = 22, nullable = false)
    private String cbu;

    @Column(name = "alias_cbu")
    private String aliasCbu;

    @Column(name = "banco")
    private String banco;

    @NotNull
    @Column(name = "saldo", precision=10, scale=2, nullable = false)
    private BigDecimal saldo;

    @ManyToOne(optional = false)
    @NotNull
    private User user;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public Cuenta nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCbu() {
        return cbu;
    }

    public Cuenta cbu(String cbu) {
        this.cbu = cbu;
        return this;
    }

    public void setCbu(String cbu) {
        this.cbu = cbu;
    }

    public String getAliasCbu() {
        return aliasCbu;
    }

    public Cuenta aliasCbu(String aliasCbu) {
        this.aliasCbu = aliasCbu;
        return this;
    }

    public void setAliasCbu(String aliasCbu) {
        this.aliasCbu = aliasCbu;
    }

    public String getBanco() {
        return banco;
    }

    public Cuenta banco(String banco) {
        this.banco = banco;
        return this;
    }

    public void setBanco(String banco) {
        this.banco = banco;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public Cuenta saldo(BigDecimal saldo) {
        this.saldo = saldo;
        return this;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }

    public User getUser() {
        return user;
    }

    public Cuenta user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
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
        Cuenta cuenta = (Cuenta) o;
        if (cuenta.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), cuenta.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Cuenta{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", cbu='" + getCbu() + "'" +
            ", aliasCbu='" + getAliasCbu() + "'" +
            ", banco='" + getBanco() + "'" +
            ", saldo=" + getSaldo() +
            "}";
    }
}
