package com.tiendacarta.comprador.Service;

import feign.FeignException;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;

import com.tiendacarta.comprador.Client.UsuarioClient;
import com.tiendacarta.comprador.DTO.CompradorCreateDTO;
import com.tiendacarta.comprador.DTO.CompradorDTO;
import com.tiendacarta.comprador.DTO.UsuarioDTO;
import com.tiendacarta.comprador.Excepction.RecursoNoEncontradoException;
import com.tiendacarta.comprador.Excepction.ServicioNoDisponibleException;
import com.tiendacarta.comprador.Model.Comprador;
import com.tiendacarta.comprador.Repository.CompradorRepository;

import org.springframework.stereotype.Service;


@Service
public class CompradorService {
    
    private static final Logger Log = LoggerFactory.getLogger(CompradorService.class);

    @Autowired
    private CompradorRepository compradorRepository;
    
    @Autowired
    private UsuarioClient usuarioClient;

    public List<CompradorDTO> findall(){
        Log.info("consultando todos los comprador");
        return compradorRepository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    public CompradorDTO findById(Long id){
        Comprador comprador = compradorRepository.findById(id).orElseThrow(() -> new RecursoNoEncontradoException("comprador no Encotrado"+ id));
        return toDTO(comprador);
    }

    public CompradorDTO crear(CompradorCreateDTO dto){
        Log.info("creando Comprador: usuarioId={}",dto.getUsuarioId());
        UsuarioDTO usuario;
        try{
            Log.info("Consultando a Servicio de usuario para el usuarioId={}",dto.getUsuarioId());
            usuario = usuarioClient.obtenerUsuarios(dto.getUsuarioId());
            Log.info("Usuario encotrado: nombre={},correo={}",usuario.getNombre(),usuario.getCorreo());
        } catch (FeignException.NotFound e){
            throw new RecursoNoEncontradoException("Usuario no encotrado"+ dto.getUsuarioId());
        } catch(FeignException e){
            Log.error("Error al cactactar con servico de usuari: {}",e.getMessage());
            throw new ServicioNoDisponibleException("servicio de usuari no dispobibl,intnte nuevamente ");
        }

        Comprador comprador = new Comprador();
        comprador.setUsuarioId(dto.getUsuarioId());
        comprador.setCantidadCompra(dto.getCantidadCompra());
        comprador.setHistorialCompra(dto.getHistorialCompra());
        comprador.setMayorCompra(dto.getMayorCompra());
        comprador.setDetalleMayor(dto.getDetalleMayor());
        comprador.setMenorCompra(dto.getMenorCompra());
        comprador.setDetallemenor(dto.getDetallemenor());

        Comprador guardar = compradorRepository.save(comprador);
        Log.info("Comprador Creado id={} - usuario={} - correo={}",guardar.getId(),usuario.getNombre(),usuario.getCorreo());
        return toDTOConNombre(guardar,usuario.getNombre(),usuario.getCorreo());
    }
    
    public CompradorDTO eliminar(Long id){
        Log.info("eliminando CompradorDTO por Id={}",id);
        Comprador comprador = compradorRepository.findById(id).orElseThrow(()-> new RecursoNoEncontradoException("comprador no ecntrado"+ id));
        return toDTO(compradorRepository.save(comprador));
    }

    private CompradorDTO toDTO(Comprador c){
        return new CompradorDTO(
        c.getId(),
        c.getUsuarioId(),
        null,
        null,
        c.getCantidadCompra(),
        c.getHistorialCompra(),
        c.getMayorCompra(),
        c.getDetalleMayor(),
        c.getMenorCompra(),
        c.getDetallemenor()
        );
    }


    public CompradorDTO actualizar(Long id, CompradorCreateDTO dto){
        Log.info("se actualizo el Comprador id={}",id);
        Comprador comprador = compradorRepository.findById(id).orElseThrow(()-> new RecursoNoEncontradoException("Comprador no Encontrado" + id));
        UsuarioDTO usuario;
        try{
            usuario = usuarioClient.obtenerUsuarios(dto.getUsuarioId());
        } catch(FeignException.NotFound e){
            throw new RecursoNoEncontradoException("el usuario no existe:" +dto.getUsuarioId());
        } catch(FeignException e){
            throw new ServicioNoDisponibleException("servicio de usuario no se esncuetra disponible");
        }

        comprador.setUsuarioId(dto.getUsuarioId());
        comprador.setCantidadCompra(dto.getCantidadCompra());
        comprador.setHistorialCompra(dto.getHistorialCompra());
        comprador.setMayorCompra(dto.getMayorCompra());
        comprador.setDetalleMayor(dto.getDetalleMayor());
        comprador.setMenorCompra(dto.getMenorCompra());
        comprador.setDetallemenor(dto.getDetallemenor());
        Comprador actualizado = compradorRepository.save(comprador);
        Log.info("Comprador Actualizado id={} - usuario={}",actualizado.getId());
        return toDTOConNombre(actualizado,usuario.getNombre(),usuario.getCorreo());
    }

    public CompradorDTO toDTOConNombre(Comprador c, String nombreUsuario, String correoUsuario){
        return new CompradorDTO(
            c.getId(),
            c.getUsuarioId(),
            nombreUsuario,
            correoUsuario,
            c.getCantidadCompra(),
            c.getHistorialCompra(),
            c.getMayorCompra(),
            c.getDetalleMayor(),
            c.getMenorCompra(),
            c.getDetallemenor()
        );
    }


























}