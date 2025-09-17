package co.edu.uniandes.dse.parcial1.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;

import co.edu.uniandes.dse.parcial1.entities.MercanciaEntity;
import co.edu.uniandes.dse.parcial1.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.parcial1.exceptions.IllegalOperationException;
import jakarta.transaction.Transactional;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

@DataJpaTest
@Transactional
@Import(MercanciaService.class)
public class MercanciaServiceTest {
    
    @Autowired
    private MercanciaService mercanciaService;

    @Autowired
    private TestEntityManager entityManager;

    private PodamFactory factory = new PodamFactoryImpl();

    private List<MercanciaEntity> mercanciaEntities = new ArrayList<>();

    @BeforeEach
    void setUp() {
        clearData();
        insertData();
    }

    private void clearData() {
        entityManager.getEntityManager().createQuery("delete from MercanciaEntity").executeUpdate();
        mercanciaEntities.clear();
    }

    private void insertData() {
        for (int i = 0; i < 3; i++) {
            MercanciaEntity mercancia = factory.manufacturePojo(MercanciaEntity.class);
            mercancia.setCantidadDisponible(i+2);
            mercancia.setNombre("producto" + i);
            mercancia.setCodigoBarras("COD"+i);
            mercancia.setFechaRecepcion(LocalDateTime.now().minusYears(i));
            mercanciaEntities.add(mercancia);
        }
    }

    @Test
    void testCreateMercancia() throws IllegalOperationException, EntityNotFoundException {
        MercanciaEntity newEntity = factory.manufacturePojo(MercanciaEntity.class);
        newEntity.setCantidadDisponible(10);
        newEntity.setNombre("producto" + 10);
        newEntity.setCodigoBarras("COD"+10);
        newEntity.setFechaRecepcion(LocalDateTime.now().minusYears(10));

        MercanciaEntity result = mercanciaService.createMercancia(newEntity);
        assertNotNull(result);

        MercanciaEntity entity = entityManager.find(MercanciaEntity.class, result.getId());
        assertEquals(newEntity.getCodigoBarras(), entity.getCodigoBarras());
        assertEquals(newEntity.getNombre(), entity.getNombre());
    }

    @Test
    void testCreateMercanciaInvalidNombre() {
        MercanciaEntity newEntity = factory.manufacturePojo(MercanciaEntity.class);
        newEntity.setCantidadDisponible(10);
        newEntity.setNombre(" ");
        newEntity.setCodigoBarras("COD"+10);
        newEntity.setFechaRecepcion(LocalDateTime.now().minusYears(10));

        assertThrows(IllegalOperationException.class, () -> mercanciaService.createMercancia(newEntity));
    }
    
}
