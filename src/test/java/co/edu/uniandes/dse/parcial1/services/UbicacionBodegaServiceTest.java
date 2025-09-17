package co.edu.uniandes.dse.parcial1.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;

import co.edu.uniandes.dse.parcial1.entities.UbicacionBodegaEntity;
import co.edu.uniandes.dse.parcial1.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.parcial1.exceptions.IllegalOperationException;
import jakarta.transaction.Transactional;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

@DataJpaTest
@Transactional
@Import(UbicacionBodegaService.class)
public class UbicacionBodegaServiceTest {

    @Autowired
    private UbicacionBodegaService ubicacionBodegaService;

    @Autowired
    private TestEntityManager entityManager;

    private PodamFactory factory = new PodamFactoryImpl();

    private List<UbicacionBodegaEntity> ubicacionBodegaEntities = new ArrayList<>();

    @BeforeEach
    void setUp() {
        clearData();
        insertData();
    }

    private void clearData() {
        entityManager.getEntityManager().createQuery("delete from UbicacionBodegaEntity").executeUpdate();
        ubicacionBodegaEntities.clear();
    }

    private void insertData() {
        for (int i = 0; i < 3; i++) {
            UbicacionBodegaEntity ubicacionBodegaEntity = factory.manufacturePojo(UbicacionBodegaEntity.class);
            ubicacionBodegaEntity.setCanasta("canasta "+i);
            ubicacionBodegaEntity.setNumeroEstante(i+1);
            ubicacionBodegaEntity.setPesoMax(i+100);
            ubicacionBodegaEntities.add(ubicacionBodegaEntity);
        }
    }

    @Test
    void testCreateUbicacionBodega() throws IllegalOperationException, EntityNotFoundException {
        UbicacionBodegaEntity newEntity = factory.manufacturePojo(UbicacionBodegaEntity.class);
        newEntity.setCanasta("canasta 10");
        newEntity.setNumeroEstante(10);
        newEntity.setPesoMax(200);

        UbicacionBodegaEntity result = ubicacionBodegaService.createUbicacionBodega(newEntity);
        assertNotNull(result);

        UbicacionBodegaEntity entity = entityManager.find(UbicacionBodegaEntity.class, result.getId());
        assertEquals(newEntity.getNumeroEstante(), entity.getNumeroEstante());
    }

    @Test
    void testCreateUbicacionBodegaNegativeNumeroEstante() {
        UbicacionBodegaEntity newEntity = factory.manufacturePojo(UbicacionBodegaEntity.class);
        newEntity.setCanasta("canasta 10");
        newEntity.setNumeroEstante(-10);
        newEntity.setPesoMax(200);

        assertThrows(IllegalOperationException.class, () -> ubicacionBodegaService.createUbicacionBodega(newEntity));
    }

    @Test
    void testCreateUbicacionBodegaZeroNumeroEstante() {
        UbicacionBodegaEntity newEntity = factory.manufacturePojo(UbicacionBodegaEntity.class);
        newEntity.setCanasta("canasta 10");
        newEntity.setNumeroEstante(0);
        newEntity.setPesoMax(200);

        assertThrows(IllegalOperationException.class, () -> ubicacionBodegaService.createUbicacionBodega(newEntity));
    }
    
}
