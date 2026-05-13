package com.tiendacarta.comprador.DTO;
import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompradorCreateDTO {

    @NotNull(message = "ingrea el id del usuario")
    private Long UsuarioId;
    @NotNull(message = "ingrese la cantidad de compras realizdas")
    private Integer cantidadCompra;
    @NotNull(message = "ingrese el historial de las compras")
    private String historialCompra;
    @NotNull(message = "ingrese el valor de la mayor compra")
    private Integer mayorCompra;
    /*el detalle de cual fue la compra mayor y menor  */
    @NotNull(message = "ingrese el detalle del producto mayor comprado")
    private String detalleMayor;
    @NotNull(message = "ingrese el valor de la menor compra")
    private Integer menorCompra;
    @NotNull(message = "ingrese el detalle del porducto con la menor compra")
    private String detallemenor;
}
