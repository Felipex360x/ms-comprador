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

import jakarta.validation.Valid;


@RestController
@RequestMapping("api/v2/compradores")
public class CompradorController {
    
    @Autowired
    private CompradorService compradorService;

    
    @GetMapping
    public ResponseEntity<List<CompradorDTO>> getAll(){
        return ResponseEntity.ok(compradorService.findall());
    }
    @GetMapping("/{id}")
    public ResponseEntity<CompradorDTO> getById(@PathVariable Long id){
        return ResponseEntity.ok(compradorService.findById(id));
    }

    @PostMapping
    public ResponseEntity<CompradorDTO> crear(@Valid @RequestBody CompradorCreateDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(compradorService.crear(dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        compradorService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<CompradorDTO> actualizar(@PathVariable Long id, @RequestBody CompradorCreateDTO dto){
        CompradorDTO compradorActualizado = compradorService.actualizar(id, dto);
        return new ResponseEntity<>(compradorActualizado,HttpStatus.OK);

    }



}
