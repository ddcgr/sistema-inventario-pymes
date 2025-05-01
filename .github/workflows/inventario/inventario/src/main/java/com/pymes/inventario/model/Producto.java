package com.pymes.inventario.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Entity
@Table(name = "producto")
@Data
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "El nombre no puede estar vacío")
    private String nombre;

    private String descripcion;

    @PositiveOrZero(message = "La cantidad no puede ser negativa")
    private Integer cantidad;

    @PositiveOrZero(message = "El precio no puede ser negativo")
    private Double precio;

    @PositiveOrZero(message = "El lote completo no puede ser negativo")
    private Integer loteCompleto;
}
