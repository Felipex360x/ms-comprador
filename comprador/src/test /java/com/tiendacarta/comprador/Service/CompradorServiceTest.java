package com.tiendacarta.comprador.Service;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.tiendacarta.comprador.Client.UsuarioClient;
import com.tiendacarta.comprador.DTO.CompradorCreateDTO;
import com.tiendacarta.comprador.DTO.CompradorDTO;
import com.tiendacarta.comprador.DTO.UsuarioDTO;
import com.tiendacarta.comprador.Excepction.RecursoNoEncontradoException;
import com.tiendacarta.comprador.Model.Comprador;
import com.tiendacarta.comprador.Repository.CompradorRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CompradorServiceTest {

    @Mock
    private CompradorRepository compradorRepository;

    @Mock
    private UsuarioClient usuarioClient;

    @InjectMocks
    private CompradorService compradorService;

    // ── findAll ────────────────────────────────────────────────────────────────
    @Test
    @DisplayName("findAll - debe retornar lista de Compradores cuando existen registros")
    void debeRetornarListaDeCompradores() {
        // Given
        List<Comprador> compradoresSimulados = List.of(
                new Comprador(
                        1L, 100L, 
                        50,
                        "Historial A",
                         20,
                        "Producto A", 
                        1,
                        "Producto B"
                ));
        when(compradorRepository.findAll()).thenReturn(compradoresSimulados);
        // When
        List<CompradorDTO> resultado = compradorService.findall();
        // Then
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals(100L, resultado.get(0).getUsuarioId());
        verify(compradorRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("findAll - debe retornar lista vacía cuando no existen Compradores")
    void debeRetornarListaVacia() {
        // Given
        when(compradorRepository.findAll()).thenReturn(List.of());
        // When
        List<CompradorDTO> resultado = compradorService.findall();
        // Then
        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
    }

     // ── findById ───────────────────────────────────────────────────────────────
    @Test
    @DisplayName("findById - debe retornar el Comprador cuando existe")
    void debeRetornarCompradorPorId() {
        // Given
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
        when(compradorRepository.findById(1L)).thenReturn(Optional.of(comprador));
        // When
        CompradorDTO resultado = compradorService.findById(1L);
        // Then
        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals(100L, resultado.getUsuarioId());
    }
    @Test
    @DisplayName("findById - debe lanzar excepción cuando el Comprador no existe")
    void debeLanzarExcepcionCuandoCompradorNoExiste() {
        // Given
        when(compradorRepository.findById(999L)).thenReturn(Optional.empty());
        // When & Then
        assertThrows( RecursoNoEncontradoException.class,() -> compradorService.findById(999L)
        );
    }
    // ── crear ──────────────────────────────────────────────────────────────────
    @Test
    @DisplayName("crear - debe persistir y retornar el Comprador creado")
    void debeCrearCompradorCorrectamente() {
        // Given
        CompradorCreateDTO dto = new CompradorCreateDTO();
        dto.setUsuarioId(100L);
        dto.setCantidadCompra(50);
        dto.setHistorialCompra("Historial");
        dto.setMayorCompra(20);
        dto.setDetalleMayor("Producto Mayor");
        dto.setMenorCompra(1);
        dto.setDetallemenor("Producto Menor");
        UsuarioDTO usuario = new UsuarioDTO();
        usuario.setNombre("Felipe");
        usuario.setCorreo("felipe@test.cl");

        Comprador compradorGuardado = new Comprador(
                1L,
                100L,
                50,
                "Historial",
                20,
                "Producto Mayor",
                1,
                "Producto Menor"
        );
        when(usuarioClient.obtenerUsuarios(100L)).thenReturn(usuario);
        when(compradorRepository.save(any(Comprador.class))).thenReturn(compradorGuardado);
        // When
        CompradorDTO resultado = compradorService.crear(dto);
        // Then
        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals(100L, resultado.getUsuarioId());
        verify(compradorRepository, times(1)).save(any(Comprador.class));
    }
    // ── actualizar ─────────────────────────────────────────────────────────────
    @Test
    @DisplayName("actualizar - debe actualizar y retornar Comprador existente")
    void debeActualizarCompradorCorrectamente() {
        // Given
        Comprador CompadorExistente = new Comprador(
                1L,
                100L,
                10,
                "Historial Antiguo",
                5,
                "Mayor",
                1,
                "Menor"
        );
        CompradorCreateDTO dto = new CompradorCreateDTO();
        dto.setUsuarioId(100L);
        dto.setCantidadCompra(99);
        dto.setHistorialCompra("Historial Nuevo");
        dto.setMayorCompra(50);
        dto.setDetalleMayor("Mayor Nuevo");
        dto.setMenorCompra(2);
        dto.setDetallemenor("Menor Nuevo");

        UsuarioDTO usuario = new UsuarioDTO();
        usuario.setNombre("Felipe");
        usuario.setCorreo("felipe@test.cl");

        when(compradorRepository.findById(1L)).thenReturn(Optional.of(CompadorExistente));
        when(usuarioClient.obtenerUsuarios(100L)).thenReturn(usuario);
        when(compradorRepository.save(any(Comprador.class))).thenReturn(CompadorExistente);
        // When
        CompradorDTO resultado = compradorService.actualizar(1L, dto);
        // Then
        assertNotNull(resultado);
        verify(compradorRepository, times(1)).save(any(Comprador.class));
    }
    @Test
    @DisplayName("actualizar - debe lanzar excepción cuando el vendedor no existe")
    void debeLanzarExcepcionAlActualizarVendedorInexistente() {
        // Given
        CompradorCreateDTO dto = new CompradorCreateDTO();
        when(compradorRepository.findById(999L))
                .thenReturn(Optional.empty());
        // When & Then
        assertThrows(RecursoNoEncontradoException.class,() -> compradorService.actualizar(999L, dto));
    }
}
