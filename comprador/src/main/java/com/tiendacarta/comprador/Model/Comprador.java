package com.tiendacarta.comprador.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Comprador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long UsuarioId;
    private Integer cantidadCompra;
    private String historialCompra;
    private Integer mayorCompra;
    /*el detalle de cual fue la compra mayor y menor  */
    private String detalleMayor;
    private Integer menorCompra;
    private String detallemenor;
    
}
