package com.unla.Grupo14OO22020.repositories;


import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.unla.Grupo14OO22020.entities.Producto;



@Repository("productoRepository")
public interface IProductoRepository extends JpaRepository<Producto, Serializable>{
	public abstract Producto findByIdProducto(int id);
	
	
	@Query(nativeQuery=true,value="select sum(p.cantidad) as ranking,pr.nombre,pr.descripcion,pr.precio from Pedido p inner join Producto pr on pr.id_producto=p.producto_id_producto group by p.producto_id_producto order by ranking desc;")
	public abstract List<String> Ranking();

}//Fin class
