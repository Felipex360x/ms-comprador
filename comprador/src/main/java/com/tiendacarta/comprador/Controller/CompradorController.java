package com.tiendacarta.comprador.Controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tiendacarta.comprador.DTO.CompradorCreateDTO;
import com.tiendacarta.comprador.DTO.CompradorDTO;
import com.tiendacarta.comprador.Service.CompradorService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name="Compradores",description = "Operaciones de gestion de Compradores")
@RestController
@RequestMapping("api/v2/compradores")
public class CompradorController {
    
    @Autowired
    private CompradorService compradorService;

    @Operation(
        summary = "Listar todas los Compradores",
        description = "Retorna la lista completa de Compradores registradas en el sistema."
    )
    @ApiResponse(responseCode = "200", description = "Lista obtenida exitosamente")
    @GetMapping
    public ResponseEntity<List<CompradorDTO>> getAll(){
        return ResponseEntity.ok(compradorService.findall());
    }

    @Operation(summary = "Buscar Compradores por ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Compradores encontrada"),
        @ApiResponse(responseCode = "404", description = "Compradores no encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<CompradorDTO> getById(@Parameter(description = "Id unico de la Compradores",required = true)@PathVariable Long id){
        return ResponseEntity.ok(compradorService.findById(id));
    }

    @Operation(summary = "Registrar nueva Compradores")
    @ApiResponse(responseCode = "201", description = "Compradores creada exitosamente")
    @PostMapping
    public ResponseEntity<CompradorDTO> crear(@Valid @RequestBody CompradorCreateDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(compradorService.crear(dto));
    }

    @Operation(summary = "Eliminar Compradores")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Eliminación exitosa"),
        @ApiResponse(responseCode = "404", description = "Compradores no encontrada")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@Parameter(description = "Id unico de la Compradores",required = true)@PathVariable Long id) {
        compradorService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Actualizar Compradores existente")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Actualización exitosa"),
        @ApiResponse(responseCode = "404", description = "Compradores no encontrada")
    })
    
    @PutMapping("/{id}")
    public ResponseEntity<CompradorDTO> actualizar(@Parameter(description = "Ide de la vendedores a actualizar")@PathVariable Long id, @RequestBody CompradorCreateDTO dto){
        CompradorDTO compradorActualizado = compradorService.actualizar(id, dto);
        return new ResponseEntity<>(compradorActualizado,HttpStatus.OK);

    }



}
