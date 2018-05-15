package br.com.estacio.projeto.pontocerto.model;

import java.util.Date;

public class Evento {

    private Long id;
    private Date dataHorarioEvento;
    private Double latitude;
    private Double longitude;
    private Long idUsuario;
    private Integer tipoEvento;

    public Date getDataEvento() {
        return dataEvento;
    }

    public void setDataEvento(Date dataEvento) {
        this.dataEvento = dataEvento;
    }

    private Date dataEvento;

    public Integer getTipoEvento() {
        return tipoEvento;
    }

    public void setTipoEvento(Integer tipoEvento) {
        this.tipoEvento = tipoEvento;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDataHorarioEvento() {
        return dataHorarioEvento;
    }

    public void setDataHorarioEvento(Date dataHorarioEvento) {
        this.dataHorarioEvento = dataHorarioEvento;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }
}
