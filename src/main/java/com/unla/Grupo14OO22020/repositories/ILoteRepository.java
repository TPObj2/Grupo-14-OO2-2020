package com.unla.Grupo14OO22020.repositories;


import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.unla.Grupo14OO22020.entities.Lote;



@Repository("loteRepository")
public interface ILoteRepository extends JpaRepository<Lote, Serializable>{
	public abstract Lote findByIdLote(int idLote);

	@Query(nativeQuery=true,value="select id_lote, cantidad_actual,cantidad_inicial,estado,fecha_ingreso,id_local,producto_id_producto from lote where id_local=(:idLocal) order by id_lote asc;")
	public abstract List<Lote> lotesDeUnLocalOrdenadosPorID(int idLocal);	
	
}//Fin class
