package com.tiendacarta.comprador.Repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import java.util.List;
import java.util.Optional;
import com.tiendacarta.comprador.Model.Comprador;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class CompradorRepositoryTest {
    
    @Autowired
    private CompradorRepository compradorRepository;

    @Test
    @DisplayName("save - debe guardar Comprador y generar ID")
    void debeGuardarCompradorYGenerarId() {
        Comprador comprador = new Comprador(
                null,
                100L,
                50,
                "Historial",
                20,
                "Mayor",
                1,
                "Menor"
        );
        Comprador guardado = compradorRepository.save(comprador);
        assertNotNull(guardado.getId());
        assertTrue(guardado.getId() > 0);
    }
    @Test
    @DisplayName("findAll - debe retornar todos los Comprador")
    void debeRetornarTodosLosComprador() {

        compradorRepository.save(new Comprador(
                null, 
                101L, 
                10, 
                "Historial1",
                5, 
                "Mayor1", 
                1, 
                "Menor1"));

        compradorRepository.save(new Comprador(
                null,
                102L, 
                20,
                "Historial2",
                8, 
                "Mayor2",
                2, 
                "Menor2"));

        List<Comprador> compradores = compradorRepository.findAll();

        assertNotNull(compradores);
        assertEquals(2, compradores.size());
    }

    @Test
    @DisplayName("findById - debe encontrar Comprador existente")
    void debeEncontrarCompradorPorId() {
        Comprador guardado = compradorRepository.save(
                new Comprador(
                        null,
                        200L,
                        30,
                        "Historial",
                        15,
                        "Mayor",
                        2,
                        "Menor"
                ));
        Optional<Comprador> resultado = compradorRepository.findById(guardado.getId());
        assertTrue(resultado.isPresent());
        assertEquals(200L, resultado.get().getUsuarioId());
    }

    @Test
    @DisplayName("findById - debe retornar vacío cuando no existe")
    void debeRetornarVacioCuandoNoExiste() {
        Optional<Comprador> resultado = compradorRepository.findById(999L);
        assertFalse(resultado.isPresent());
    }
    
     @Test
    @DisplayName("deleteById - debe eliminar Comprador")
    void debeEliminarComprador() {
        Comprador guardado = compradorRepository.save(
                new Comprador(
                        null,
                        300L,
                        40,
                        "Historial",
                        18,
                        "Mayor",
                        1,
                        "Menor"    
                ));
        Long id = guardado.getId();
        compradorRepository.deleteById(id);
        assertFalse(compradorRepository.findById(id).isPresent());
    }
    
}
