package com.unla.Grupo14OO22020.services;

import java.time.LocalDate;
import java.util.List;

import com.unla.Grupo14OO22020.entities.Empleado;
import com.unla.Grupo14OO22020.entities.Local;
import com.unla.Grupo14OO22020.models.EmpleadoModel;



public interface IEmpleadoService {
	
	public abstract List<Empleado> getAll();
	
	public EmpleadoModel insertOrUpdate(EmpleadoModel empleadoModel);
	
	public EmpleadoModel findByIdPersona(int id);
	
	public boolean remove(int id);

	public List<Empleado> actualSueldosEmpleadosDelLocal(int idLocal,LocalDate fecha1,LocalDate fecha2);

	public Empleado actualizacionSueldoEmpleado(int idEmpleado,LocalDate fecha1,LocalDate fecha2);
	
	public List<Empleado> traerEmpleadosDelLocal(Local local);
	
}
