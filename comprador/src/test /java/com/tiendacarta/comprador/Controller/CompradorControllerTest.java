package com.tiendacarta.comprador.Controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.tiendacarta.comprador.DTO.CompradorDTO;
import com.tiendacarta.comprador.Excepction.GlobalExceptionHandler;
import com.tiendacarta.comprador.Excepction.RecursoNoEncontradoException;
import com.tiendacarta.comprador.Service.CompradorService;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
@DataJpaTest
@ActiveProfiles("test")
class CompradorControllerTest {
    
    @Mock
    private CompradorService compradorService;

    @InjectMocks
    private CompradorController compradorController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(compradorController).setControllerAdvice(new GlobalExceptionHandler()).build();
    }
    // ── GET /api/v2/compradores ────────────────────────────────────────────────
        @Test
        @DisplayName("GET /api/v2/compradores - debe retornar 200 con la lista de compradores")
        void debeRetornar200CuandoSePidencompradores() throws Exception {
                // Given
                when(compradorService.findall()).thenReturn(List.of(
                        new CompradorDTO(
                                1L,
                                100L,
                                "Felipe",
                                "felipe@test.cl",
                                50,
                                "Historial",
                                20,
                                "Producto Mayor",
                                1,
                                "Producto Menor"
                        ),
                        new CompradorDTO(
                                2L,
                                200L,
                                "Juan",
                                "juan@test.cl",
                                80,
                                "Historial 2",
                                40,
                                "Producto X",
                                2,
                                "Producto Y"
                        )));
                // When & Then
                 mockMvc.perform(get("/api/v2/compradores")).andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].nombreUsuario").value("Felipe"))
                .andExpect(jsonPath("$[1].id").value(2));
            
        }

        @Test
        @DisplayName("GET /api/v2/compradores - debe retornar 200 con lista vacía")
        void debeRetornar200ConListaVacia() throws Exception {
                // Given
                when(compradorService.findall()).thenReturn(List.of());
                // When & Then
                mockMvc.perform(get("/api/v2/compradores")).andExpect(status().isOk()).andExpect(jsonPath("$.length()").value(0));
        }    

    // ── GET /api/v2/compradores/{id} ───────────────────────────────────────────
        @Test
        @DisplayName("GET /api/v2/compradores/{id} - debe retornar compradores existente")
        void debeRetornarcompradoresPorId() throws Exception {
                // Given
                when(compradorService.findById(1L)).thenReturn(
                        new CompradorDTO(
                                1L,
                                100L,
                                "Felipe",
                                "felipe@test.cl",
                                50,
                                "Historial",
                                20,
                                "Producto Mayor",
                                1,
                                "Producto Menor"
                ));
                // When & Then
                mockMvc.perform(get("/api/v2/compradores/1"))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.id").value(1))
                        .andExpect(jsonPath("$.usuarioId").value(100))
                        .andExpect(jsonPath("$.nombreUsuario").value("Felipe"));
            }

        @Test
        @DisplayName("GET /api/v2/compradores/{id} - debe retornar 404 cuando no existe")
        void debeRetornar404CuandocompradorsNoExiste() throws Exception {
                // Given
                when(compradorService.findById(999L))
                        .thenThrow(new RecursoNoEncontradoException("compradores no encontrado 999"));
                // When & Then
                mockMvc.perform(get("/api/v2/compradores/999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("compradores no encontrado 999"));
        }
  // ── POST /api/v2/vendedores ───────────────────────────────────────────────
        @Test
        @DisplayName("POST /api/v2/compradores - debe retornar 201 al crear compradores válido")
        void debeRetornar201AlCrearcomprador() throws Exception {
                // Given
                String json = """
                {
                       "usuarioId": 100,
                        "cantidadCompra": 50,
                        "historialCompra": "Historial",
                        "mayorCompra": 20,
                        "detalleMayor": "Producto Mayor",
                        "menorCompra": 1,
                        "detallemenor": "Producto Menor"
                }
                """;
                when(compradorService.crear(any())).thenReturn(
                        new CompradorDTO(
                                1L,
                                100L,
                                "Felipe",
                                "felipe@test.cl",
                                50,
                                "Historial",
                                20,
                                "Producto Mayor",
                                1,
                                "Producto Menor"
                ));
            // When & Then
            mockMvc.perform(post("/api/v2/compradores")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(json))
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.id").value(1))
                    .andExpect(jsonPath("$.nombreUsuario").value("Felipe"));
        }
        @Test
        @DisplayName("POST /api/v2/compradores - debe retornar 400 cuando faltan campos obligatorios")
        void debeRetornar400CuandoFaltanCamposObligatorios() throws Exception {
                // Given
                String json = """
                {
                        "usuarioId": null,
                        "cantidadVendido": null
                }
                """;
                // When & Then
                mockMvc.perform(post("/api/v2/compradores")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isBadRequest());
        }
      // ── PUT /api/v2/compradores/{id} ───────────────────────────────────────────

        @Test
        @DisplayName("PUT /api/v2/compradores/{id} - debe actualizar compradores")
        void debeActualizarcomprador() throws Exception {
                // Given
                String json = """
                {
                        "usuarioId": 100,
                        "cantidadCompra": 30,
                        "historialCompra": "Historial",
                        "mayorCompra": 20,
                        "detalleMayor": "Producto Mayor",
                        "menorCompra": 1,
                        "detallemenor": "Producto Menor"
                }
                """;
        when(compradorService.actualizar(anyLong(), any()))
                .thenReturn(
                        new CompradorDTO(
                                1L,
                                100L,
                                "Felipe",
                                "felipe@test.cl",
                                80,
                                "Actualizado",
                                40,
                                "Producto Mayor",
                                2,
                                "Producto Menor"
                        ));
                // When & Then
                mockMvc.perform(put("/api/v2/compradores/1").contentType(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isOk()).andExpect(jsonPath("$.cantidadCompra").value(80));
        }
     // ── DELETE /api/v2/vendedores/{id} ────────────────────────────────────────
        @Test
        @DisplayName("DELETE /api/v2/compradores/{id} - debe retornar 204")
        void debeEliminarComprador() throws Exception {
                // Given
                // When & Then
                mockMvc.perform(delete("/api/v2/compradores/1")).andExpect(status().isNoContent());
        }
        @Test
        @DisplayName("DELETE /api/v2/compradores/{id} - debe retornar 404 cuando no existe")
        void debeRetornar404AlEliminarCompradorInexistente() throws Exception {
                // Given
                doThrow(new RecursoNoEncontradoException("Vendedor no encontrado 999"))
                        .when(compradorService)
                        .eliminar(999L);
                // When & Then
                mockMvc.perform(delete("/api/v2/compradores/999")).andExpect(status().isNotFound()).andExpect(jsonPath("$.error").value("Vendedor no encontrado 999"));
        }
  

}
