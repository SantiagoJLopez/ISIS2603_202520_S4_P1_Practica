package co.edu.uniandes.dse.parcial1.entities;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import lombok.Data;
import uk.co.jemos.podam.common.PodamExclude;

@Data
@Entity
public class UbicacionBodegaEntity extends BaseEntity{
   private Integer numeroEstante;
   private String canasta;
   private Integer pesoMax;

   @PodamExclude
   @OneToMany(mappedBy = "ubicacionBodega", fetch = FetchType.LAZY)
   private List<MercanciaEntity> mercancias;
}
