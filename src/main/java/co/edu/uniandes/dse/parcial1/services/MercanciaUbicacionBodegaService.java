package co.edu.uniandes.dse.parcial1.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.uniandes.dse.parcial1.entities.MercanciaEntity;
import co.edu.uniandes.dse.parcial1.entities.UbicacionBodegaEntity;
import co.edu.uniandes.dse.parcial1.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.parcial1.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.parcial1.repositories.MercanciaRepository;
import co.edu.uniandes.dse.parcial1.repositories.UbicacionBodegaRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MercanciaUbicacionBodegaService {

    @Autowired
    private MercanciaRepository mercanciaRepository;

    @Autowired
    private UbicacionBodegaRepository ubicacionBodegaRepository;

    @Transactional
    public MercanciaEntity assignUbicacionBodegaToMercancia(Long mercanciaID, Long ubicacionBodegaID) throws EntityNotFoundException, IllegalOperationException {
        log.info("Inicia proceso de asignar ubicacion bodega {} a la mercancía {}", ubicacionBodegaID, mercanciaID);

        MercanciaEntity mercanciaEntity = mercanciaRepository.findById(mercanciaID)
                .orElseThrow(() -> new EntityNotFoundException("La mercancía con id " + mercanciaID + " no existe"));

        UbicacionBodegaEntity ubicacionBodegaEntity = ubicacionBodegaRepository.findById(ubicacionBodegaID)
                .orElseThrow(() -> new EntityNotFoundException("La ubicación bodega con id " + ubicacionBodegaID + " no existe"));

        if (mercanciaEntity.getUbicacionBodega() != null) {
            throw new IllegalOperationException("La mercancía ya tiene una ubicación bodega asignada");
        }
        if (ubicacionBodegaEntity.getMercancias().contains(mercanciaEntity)) {
            throw new IllegalOperationException("La ubicación bodega ya tiene asignada esta mercancía");
        }

        mercanciaEntity.setUbicacionBodega(ubicacionBodegaEntity);
        ubicacionBodegaEntity.getMercancias().add(mercanciaEntity);
        return mercanciaRepository.save(mercanciaEntity);
    }

}
