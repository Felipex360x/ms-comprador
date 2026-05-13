package com.tiendacarta.comprador.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tiendacarta.comprador.Model.Comprador;

@Repository
public interface CompradorRepository extends JpaRepository<Comprador,Long> {
    
}
