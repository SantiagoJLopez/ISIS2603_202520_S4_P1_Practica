package co.edu.uniandes.dse.parcial1.services;

import java.time.Duration;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.uniandes.dse.parcial1.entities.MercanciaEntity;
import co.edu.uniandes.dse.parcial1.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.parcial1.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.parcial1.repositories.MercanciaRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MercanciaService {
    
    @Autowired
    private MercanciaRepository mercanciaRepository;

    @Transactional
    public MercanciaEntity createMercancia(MercanciaEntity mercanciaEntity) throws EntityNotFoundException, IllegalOperationException {
        log.info("Inicia la creación de la mercancía");
        if(mercanciaEntity.getCodigoBarras() == null){
            throw new IllegalOperationException("código de barras no es válido");
        }
        MercanciaEntity mercancias = mercanciaRepository.findByCodigoBarras(mercanciaEntity.getCodigoBarras());
        if(mercancias != null){
            throw new IllegalOperationException("código de barras ya registrado");
        }
        if(mercanciaEntity.getNombre() == null || mercanciaEntity.getNombre() == " " || mercanciaEntity.getNombre() == ""){
            throw new IllegalOperationException("nombre no es válido");
        }
        if(Duration.between(mercanciaEntity.getFechaRecepcion(), LocalDateTime.now()).isNegative()){
            throw new IllegalOperationException("fecha de recepción inválida");
        }
        log.info("Termina el proceso de registro");
        return mercanciaRepository.save(mercanciaEntity);
    }
}
