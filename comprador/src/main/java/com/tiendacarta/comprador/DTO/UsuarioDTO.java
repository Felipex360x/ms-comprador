package com.tiendacarta.comprador.DTO;

import lombok.Data;

@Data
public class UsuarioDTO {
    
    private Long id;
    private String nombre;
    private String apellidoP;
    private String apellidoM;
    private String nombreUser;
    private String correo;
    private String password;
    private Integer edad;
    private String genero;
    private String tipoUser;

}
