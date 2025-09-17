package co.edu.uniandes.dse.parcial1.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.uniandes.dse.parcial1.entities.UbicacionBodegaEntity;
import co.edu.uniandes.dse.parcial1.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.parcial1.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.parcial1.repositories.UbicacionBodegaRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UbicacionBodegaService {

    @Autowired
    private UbicacionBodegaRepository ubicacionBodegaRepository;

    @Transactional
    public UbicacionBodegaEntity createUbicacionBodega(UbicacionBodegaEntity ubicacionBodegaEntity) throws EntityNotFoundException, IllegalOperationException {
        log.info("Inicia la creación de la ubicación bodega");
        if(ubicacionBodegaEntity.getNumeroEstante() == null){
            throw new IllegalOperationException("numero de estante nulo");
        }
        if(ubicacionBodegaEntity.getNumeroEstante() <= 0){
            throw new IllegalOperationException("numero de estante no positivo");
        }
        log.info("Termina el proceso de registro");
        return ubicacionBodegaRepository.save(ubicacionBodegaEntity);
    }

}
