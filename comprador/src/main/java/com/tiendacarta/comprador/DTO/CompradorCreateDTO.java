package com.tiendacarta.comprador.DTO;
import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "Datos necesarios para crear o actualizar un Comprador")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompradorCreateDTO {
    
    @Schema(description = "id del Usuario", example = "1")
    @NotNull(message = "ingrea el id del usuario")
    private Long UsuarioId;
    @Schema(description = "cantidad de compra", example = "1")
    @NotNull(message = "ingrese la cantidad de compras realizdas")
    private Integer cantidadCompra;
    @Schema(description = "historial de compra ", example = "4 productos")
    @NotNull(message = "ingrese el historial de las compras")
    private String historialCompra;
    @Schema(description = "mayor compra", example = "20")
    @NotNull(message = "ingrese el valor de la mayor compra")
    private Integer mayorCompra;
    /*el detalle de cual fue la compra mayor y menor  */
    @Schema(description = "detalle de compra mayor ", example = "super carta delux1")
    @NotNull(message = "ingrese el detalle del producto mayor comprado")
    private String detalleMayor;
    @Schema(description = "ingrese el valor de menor compra ", example = "10")
    @NotNull(message = "ingrese el valor de la menor compra")
    private Integer menorCompra;
    @Schema(description = "detalle de menor compra ", example = "super carta normal")
    @NotNull(message = "ingrese el detalle del porducto con la menor compra")
    private String detallemenor;
}
