package com.unla.Grupo14OO22020.repositories;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.unla.Grupo14OO22020.entities.Pedido;

@Repository("pedidoRepository")
public interface IPedidoRepository extends JpaRepository<Pedido, Serializable>{
	
	public abstract Pedido findByIdPedido(int idPedido);
	
	@Query(nativeQuery=true,value="select (p.cantidad*pr.precio) from pedido p inner join producto pr on p.producto_id_producto=pr.id_producto group by p.id_pedido;")
	public abstract List<Object> calcularSubtotal();
	
	//recorro los lotes de un local sumando la cantidad actual de un producto y los agrupo  por id producto e id local
	@Query(nativeQuery=true,value="select sum(cantidad_actual) from Lote where producto_id_producto=(:idProducto) and id_local=(:idLocal) group by producto_id_producto,id_local")
	public abstract int StockLocal(int idProducto,int idLocal);
	
	@Query(nativeQuery=true,value="select distinct(p.nombre),pe.id_pedido,pe.aceptado,pe.cantidad,pe.fecha,pe.subtotal,pe.cliente_id_persona,pe.local_id_local,pe.producto_id_producto,pe.vendedor_auxiliar_id_persona,pe.vendedor_original_id_persona from Pedido pe inner join Producto p on pe.producto_id_producto=p.id_producto group by p.nombre")
	public abstract List<Pedido> traerPedidosUnicos();	
	
	@Query(nativeQuery=true,value="select distinct(p.nombre),pe.id_pedido,pe.aceptado,pe.cantidad,pe.fecha,pe.subtotal,pe.cliente_id_persona,pe.local_id_local,pe.producto_id_producto,	pe.vendedor_auxiliar_id_persona,pe.vendedor_original_id_persona from pedido pe inner join producto p on pe.producto_id_producto=p.id_producto inner join local l on pe.local_id_local=l.id_local where l.id_local=(:idLocal) and pe.aceptado=1 group by p.nombre")
	public abstract List<Pedido> traerPedidosUnicosAceptadosDeUnLocal(int idLocal);
	
	
	@Query(nativeQuery=true,value="select distinct(p.nombre),pe.id_pedido,pe.aceptado,pe.cantidad,pe.fecha,pe.subtotal,pe.cliente_id_persona,pe.local_id_local,pe.producto_id_producto,pe.vendedor_auxiliar_id_persona,pe.vendedor_original_id_persona from Pedido pe \n" + 
			"inner join Producto p on pe.producto_id_producto=p.id_producto \n" + 
			"inner join Local l on pe.local_id_local=l.id_local where l.id_local=(:idLocal) and pe.aceptado=1 and pe.fecha between (:fecha1) and (:fecha2) group by p.nombre;")
	public abstract List<Pedido> pedUnicosAceptadosDeUnLocalEntreFechas(int idLocal,LocalDate fecha1,LocalDate fecha2);
	
	
	@Query(nativeQuery=true,value="select distinct(p.nombre),pe.id_pedido,pe.aceptado,pe.cantidad,pe.fecha,pe.subtotal,pe.cliente_id_persona,pe.local_id_local,pe.producto_id_producto,pe.vendedor_auxiliar_id_persona,pe.vendedor_original_id_persona from Pedido pe inner join Producto p on pe.producto_id_producto=p.id_producto where pe.fecha=(:fechaPedido)group by p.nombre")
	public abstract List<Pedido> fecha(LocalDate fechaPedido);
	
	@Query(nativeQuery=true,value="	select p.* from Pedido p\n" + 
			"	inner join Empleado e on e.id_persona=p.vendedor_original_id_persona\n" + 
			"	inner join Local l on l.id_local=p.local_id_local\n" + 
			"	where p.aceptado=true and p.vendedor_auxiliar_id_persona is null\n" + 
			"	and e.id_local=p.local_id_local\n" + 
			"	and p.fecha BETWEEN (:fecha1) and (:fecha2)\n" + 
			"	and p.vendedor_original_id_persona=(:idEmpOrig)")
	public abstract List<Pedido> pedidosAceptadosPropiosDeUnEmpleado(int idEmpOrig,LocalDate fecha1,LocalDate fecha2);
	
	@Query(nativeQuery=true,value="	select p.* from Pedido p\n" + 
			"	inner join Empleado e on e.id_persona=p.vendedor_original_id_persona\n" + 
			"	inner join Local l on l.id_local=p.local_id_local\n" + 
			"	where p.aceptado=true and p.vendedor_auxiliar_id_persona is not null\n" + 
			"	and e.id_local=p.local_id_local\n" + 
			"	and p.fecha BETWEEN (:fecha1) and (:fecha2)\n" + 
			"	and p.vendedor_original_id_persona=(:idEmpOrig)")
	public abstract List<Pedido> pedidosAjenosPropiosDeUnEmpleado(int idEmpOrig,LocalDate fecha1,LocalDate fecha2);
	
	@Query(nativeQuery=true,value="select * from Pedido where aceptado=true and fecha BETWEEN (:fecha1) and (:fecha2)")
	public abstract List<Pedido> pedidosAceptadosEntreFechas(LocalDate fecha1,LocalDate fecha2);
	
	@Query(nativeQuery=true,value="select * from Pedido where aceptado=true and fecha BETWEEN (:fecha1) and (:fecha2)  and local_id_local=(:idLocDelPed)")
	public abstract List<Pedido> pedidosDeUnLocalAceptadosEntreFechas(int idLocDelPed,LocalDate fecha1,LocalDate fecha2);
}
