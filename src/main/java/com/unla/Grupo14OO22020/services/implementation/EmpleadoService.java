package com.unla.Grupo14OO22020.services.implementation;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.unla.Grupo14OO22020.converters.EmpleadoConverter;
import com.unla.Grupo14OO22020.converters.LocalConverter;
import com.unla.Grupo14OO22020.entities.Empleado;
import com.unla.Grupo14OO22020.entities.Local;
import com.unla.Grupo14OO22020.entities.Pedido;
import com.unla.Grupo14OO22020.models.EmpleadoModel;
import com.unla.Grupo14OO22020.repositories.IEmpleadoRepository;
import com.unla.Grupo14OO22020.repositories.ILocalRepository;
import com.unla.Grupo14OO22020.repositories.IPedidoRepository;
import com.unla.Grupo14OO22020.services.ILocalService;
import com.unla.Grupo14OO22020.services.IPedidoService;
import com.unla.Grupo14OO22020.services.IEmpleadoService;

@Service("empleadoService")
public class EmpleadoService implements IEmpleadoService{
	
	@Autowired
	@Qualifier("empleadoRepository")
	private IEmpleadoRepository empleadoRepository;
	
	@Autowired
	@Qualifier("localService")
	private ILocalService localService;
	
	@Autowired
	@Qualifier("localRepository")
	private ILocalRepository localRepository;
	
	@Autowired
	@Qualifier("empleadoConverter")
	private EmpleadoConverter empleadoConverter;
	
	@Autowired
	@Qualifier("localConverter")
	private LocalConverter localConverter;
	
	@Autowired
	@Qualifier("pedidoService")
	private IPedidoService pedidoService;
	
	@Autowired
	@Qualifier("pedidoRepository")
	private IPedidoRepository pedidoRepository;
	
	
	@Override
	public List<Empleado> getAll(){
		return empleadoRepository.findAll();
	}
	
	@Override
	public EmpleadoModel insertOrUpdate(EmpleadoModel empleadoModel) {
		Local local = localRepository.findByIdLocal(empleadoModel.getLocal().getIdLocal());
		Empleado empleado = empleadoConverter.modelToEntity(empleadoModel);
        empleado.setLocal(local);
		empleado = empleadoRepository.save(empleado);
		return empleadoConverter.entityToModel(empleado);
	}
	
	@Override
	public EmpleadoModel findByIdPersona(int id) {
		return empleadoConverter.entityToModel(empleadoRepository.findByIdPersona(id));
	}
	
	@Override
	public boolean remove(int id) {
		try {
			empleadoRepository.deleteById(id);
			return true;
		}
		catch(Exception e) {
			return false;
		}
	}
	
	@Override
	public List<Empleado> actualSueldosEmpleadosDelLocal(int idLocal,LocalDate fecha1,LocalDate fecha2)
	{
		List<Pedido> pedidos = pedidoRepository.pedidosAceptadosEntreFechas(fecha1, fecha2);
		Local local = localRepository.findByIdLocal(idLocal);
		List<Empleado> empleadosDelLocal=traerEmpleadosDelLocal(local);
		
		for(Pedido p :pedidos)
		{
			for(Empleado empleadoAux: empleadosDelLocal) {//for 2
				if(p.getVendedorAuxiliar()==null && empleadoAux.equals(p.getVendedorOriginal())) {
					empleadoAux.setSueldo(empleadoAux.getSueldo()+p.getTotal()*0.05f);
				}else if(p.getVendedorAuxiliar()!=null) {
					if(p.getVendedorAuxiliar()!=null && empleadoAux.equals(p.getVendedorOriginal())) {//if 2
						empleadoAux.setSueldo(empleadoAux.getSueldo()+p.getTotal()*0.03f);
					}//fin if 2 
					if(p.getVendedorAuxiliar()!=null && empleadoAux.equals(p.getVendedorAuxiliar())) {//if 3
						empleadoAux.setSueldo(empleadoAux.getSueldo()+p.getTotal()*0.02f);
					}//fin if 3
				}//fin else if, if 1
			}//fin for 2
		}
		return empleadosDelLocal;
		
	}
	
	@Override
	public Empleado actualizacionSueldoEmpleado(int idEmpleado,LocalDate fecha1,LocalDate fecha2)
	{
		List<Pedido> pedidos = pedidoRepository.pedidosAceptadosEntreFechas(fecha1, fecha2);
		Empleado empleado=empleadoRepository.findByIdPersona(idEmpleado);
		for(Pedido p :pedidos)
		{
			
				if(p.getVendedorAuxiliar()==null && empleado.equals(p.getVendedorOriginal())) {
					empleado.setSueldo(empleado.getSueldo()+p.getTotal()*0.05f);
				}else if(p.getVendedorAuxiliar()!=null) {
					if(p.getVendedorAuxiliar()!=null && empleado.equals(p.getVendedorOriginal())) {//if 2
						empleado.setSueldo(empleado.getSueldo()+p.getTotal()*0.03f);
					}//fin if 2 
					if(p.getVendedorAuxiliar()!=null && empleado.equals(p.getVendedorAuxiliar())) {//if 3
						empleado.setSueldo(empleado.getSueldo()+p.getTotal()*0.02f);
					}//fin if 3
				}//fin else if, if 1
		}
		return empleado;
		
	}
	
//	@Override
//	public List<String> detalleSueldosEmpleadosDelLocal(int idLocal,LocalDate fecha1,LocalDate fecha2)
//	{
//		List<Pedido> pedidos = pedidoRepository.pedidosAceptadosEntreFechas(fecha1, fecha2);
//		Local local = localRepository.findByIdLocal(idLocal);
//		List<Empleado> empleadosDelLocal=traerEmpleadosDelLocal(local);
//		float comisionOriginal[]= new float [empleadosDelLocal.size()];
//		float comisionAuxiliar[]= new float [empleadosDelLocal.size()];
//		List<String> suedoDetallado= new ArrayList<String>(); 
//	
//		for(Pedido p :pedidos)
//		{
//			for(Empleado empleadoAux: empleadosDelLocal) {//for 2
//				if(p.getVendedorAuxiliar()==null && empleadoAux.equals(p.getVendedorOriginal())) {
//					empleadoAux.setSueldo(empleadoAux.getSueldo()+p.getTotal()*0.05f);
//				}else if(p.getVendedorAuxiliar()!=null) {
//					if(p.getVendedorAuxiliar()!=null && empleadoAux.equals(p.getVendedorOriginal())) {//if 2
//						empleadoAux.setSueldo(empleadoAux.getSueldo()+p.getTotal()*0.03f);
//					}//fin if 2 
//					if(p.getVendedorAuxiliar()!=null && empleadoAux.equals(p.getVendedorAuxiliar())) {//if 3
//						empleadoAux.setSueldo(empleadoAux.getSueldo()+p.getTotal()*0.02f);
//					}//fin if 3
//				}//fin else if, if 1
//			}//fin for 2
//		}
//		return empleadosDelLocal;
//		
//	}
	
	@Override
	public List<Empleado> traerEmpleadosDelLocal(Local local){//
		List<Empleado> listaEmpleado = new ArrayList<Empleado>();		
		for(Empleado empleado: local.getEmpleados()) {
			listaEmpleado.add(empleado);
		}
		return listaEmpleado;
	}		
	

}
