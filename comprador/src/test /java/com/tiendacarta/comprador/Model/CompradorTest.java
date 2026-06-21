package com.tiendacarta.comprador.Model;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CompradorTest {

    @Test
    @DisplayName("Constructor vacío - debe crear una instancia no nula")
    void constructorVacioDebeCrearInstanciaNoNula() {
        Comprador comprador = new Comprador();

        assertNotNull(comprador);
    }

    @Test
    @DisplayName("Constructor completo - debe asignar todos los campos correctamente")
    void constructorCompletoDebeAsignarTodosLosCampos(){

        Comprador comprador = new Comprador(
            1L,
            100L,
            50,
            "producto 1, producto 2",
            20,
            "producto 1",
            1,
            "producto 2"
        );

        assertEquals(1L,comprador.getId());
        assertEquals(100L, comprador.getUsuarioId());
        assertEquals(50, comprador.getCantidadCompra());
        assertEquals("producto 1, producto 2", comprador.getHistorialCompra());
        assertEquals(20, comprador.getMayorCompra());
        assertEquals("producto 1", comprador.getDetalleMayor());
        assertEquals(1, comprador.getMenorCompra());
        assertEquals("producto 2", comprador.getDetallemenor());
    }
    @Test
    @DisplayName("Setters - debe permitir modificar todos los campos")
    void settersDebenModificarCampos(){
        Comprador comprador = new Comprador();
        comprador.setId(2L);
        comprador.setUsuarioId(200L);
        comprador.setCantidadCompra(100);
        comprador.setHistorialCompra("Producto 20");
        comprador.setMayorCompra(50);
        comprador.setDetalleMayor("producto premin carta");
        comprador.setMenorCompra(2);
        comprador.setDetallemenor("producto babico funda de carta");

        assertEquals(2L,comprador.getId());
        assertEquals(200L, comprador.getUsuarioId());
        assertEquals(100, comprador.getCantidadCompra());
        assertEquals("Producto 20", comprador.getHistorialCompra());
        assertEquals(50, comprador.getMayorCompra());
        assertEquals("producto premin carta", comprador.getDetalleMayor());
        assertEquals(2, comprador.getMenorCompra());
        assertEquals("producto babico funda de carta", comprador.getDetallemenor());
    }
    @Test
    @DisplayName("equals y hashCode - dos Compradores con los mismos datos deben ser iguales")
    void compradoresConMismosDatosDebenSerIguales(){

        Comprador v1 = new Comprador(
                1L,
                100L,
                50,
                "Historial",
                20,
                "Mayor",
                1,
                "Menor"
        );
        Comprador v2 = new Comprador(
                1L,
                100L,
                50,
                "Historial",
                20,
                "Mayor",
                1,
                "Menor"
        );
        assertEquals(v1, v2);
        assertEquals(v1.hashCode(), v2.hashCode());
    }
    @Test
    @DisplayName("toString - debe contener información del Comprador")
    void toStringDebeContenerInformacion(){
         Comprador comprador = new Comprador(
                1L,
                100L,
                50,
                "Historial",
                20,
                "Mayor",
                1,
                "Menor"
        );
        String texto = comprador.toString();

        assertNotNull(texto);
        assertTrue(texto.contains("100"));
        assertTrue(texto.contains("Historial"));
    }
}
