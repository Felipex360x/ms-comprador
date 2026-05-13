package com.tiendacarta.comprador.DTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompradorDTO {

    private Long id;
    /*parte de usuario */
    private Long UsuarioId;
    private String nombreUsuario;
    private String correoUsuario;
    private Integer cantidadCompra;
    private String historialCompra;
    private Integer mayorCompra;
    /*el detalle de cual fue la compra mayor y menor  */
    private String detalleMayor;
    private Integer menorCompra;
    private String detallemenor;

    
}
