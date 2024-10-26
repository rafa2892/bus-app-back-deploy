package com.bus.app.modelo;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name= "carros")
@Setter
@Getter
public class Carro {



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String modelo;

    private String marca;

    @Column(name = "anyo")
    private Long anyo;

    @Column(name = "consumo")
    private Long consumo;

    @Column(name = "numeroUnidad", unique = true)
    private Long numeroUnidad;

    @Column(name = "tipo_vehiculo")
    private String tipoDeVehiculo;


    @JsonManagedReference
    @OneToMany(mappedBy = "carro", cascade = CascadeType.ALL)
    private List<Imagen> imagenes;


    @OneToMany(mappedBy = "carro", cascade = CascadeType.ALL)
    private List<Historial> registroHistorial;








}