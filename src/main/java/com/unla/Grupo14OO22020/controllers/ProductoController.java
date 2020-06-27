package com.unla.Grupo14OO22020.controllers;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.unla.Grupo14OO22020.converters.LocalConverter;
import com.unla.Grupo14OO22020.converters.PedidoConverter;
import com.unla.Grupo14OO22020.entities.Local;
import com.unla.Grupo14OO22020.entities.Pedido;
import com.unla.Grupo14OO22020.entities.Producto;
import com.unla.Grupo14OO22020.helpers.ViewRouteHelpers;
import com.unla.Grupo14OO22020.models.LocalModel;
import com.unla.Grupo14OO22020.models.ProductoModel;
import com.unla.Grupo14OO22020.repositories.ILocalRepository;
import com.unla.Grupo14OO22020.repositories.IPedidoRepository;
import com.unla.Grupo14OO22020.services.ILocalService;
import com.unla.Grupo14OO22020.services.IPedidoService;
import com.unla.Grupo14OO22020.services.IProductoService;


@Controller
@RequestMapping("/productos")
public class ProductoController {
	
	@Autowired
	@Qualifier("productoService")
	private IProductoService productoService;
	
	@Autowired
	@Qualifier("pedidoRepository")
	private IPedidoRepository pedidoRepository;

	@Autowired
	@Qualifier("pedidoService")
	private IPedidoService pedidoService;

	@Autowired
	@Qualifier("pedidoConverter")
	private PedidoConverter pedidoConverter;

	@Autowired
	@Qualifier("localService")
	private ILocalService localService;

	@Autowired
	@Qualifier("localRepository")
	private ILocalRepository localRepository;


	@Autowired
	@Qualifier("localConverter")
	private LocalConverter localConverter;
	
	////////////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////////
	
	@GetMapping("/indexEmpleado")
	public ModelAndView indexEmpleado() {
		ModelAndView mAV = new ModelAndView(ViewRouteHelpers.PRODUCTO_INDEXEMPLEADO);
		mAV.addObject("productos", productoService.getAll());
		mAV.addObject("producto", new ProductoModel());
		
		return mAV;
	}
	
	@GetMapping("/newEmpleado")
	public ModelAndView crearEmpleado() {
		ModelAndView mAV = new ModelAndView(ViewRouteHelpers.PRODUCTO_ADDEMPLEADO);
		mAV.addObject("producto", new Producto());
		
		return mAV;
	}
	
	@PostMapping("/createEmpleado")
	public RedirectView agregarEmpleado(@ModelAttribute(name="productos") ProductoModel producto ) {
		productoService.insertOrUpdate(producto);
		return new RedirectView(ViewRouteHelpers.PRODUCTO_ROOTEMPLEADO);
	}
	
	@PostMapping("/deleteEmpleado/{id}")
	public RedirectView eliminarEmpleado(@PathVariable("id") int id) {
		productoService.remove(id);
		return new RedirectView(ViewRouteHelpers.PRODUCTO_ROOTEMPLEADO);
	}
	
	@GetMapping("/updateEmpleado/{id}")
	public ModelAndView getEmpleado(@PathVariable("id") int idProducto) {
		ModelAndView mAV = new ModelAndView(ViewRouteHelpers.PRODUCTO_UPDATEEMPLEADO);
		mAV.addObject("producto", productoService.findByIdProducto(idProducto));
		return mAV;
	}
	
	@PostMapping("/updateEmpleado")
	public RedirectView updateEmpleado(@ModelAttribute("producto") ProductoModel productoModel) {
		productoService.insertOrUpdate(productoModel);
		return new RedirectView(ViewRouteHelpers.PRODUCTO_ROOTEMPLEADO);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////////
	
	
	
	
	
	
	
	@GetMapping("")
	public ModelAndView index() {
		ModelAndView mAV = new ModelAndView(ViewRouteHelpers.PRODUCTO_INDEX);
		mAV.addObject("productos", productoService.getAll());
		mAV.addObject("producto", new ProductoModel());
		
		return mAV;
	}
	
	@GetMapping("/new")
	public ModelAndView crear() {
		ModelAndView mAV = new ModelAndView(ViewRouteHelpers.PRODUCTO_ADD);
		mAV.addObject("producto", new Producto());
		
		return mAV;
	}
	
	@PostMapping("/create")
	public RedirectView agregar(@ModelAttribute(name="productos") ProductoModel producto ) {
		productoService.insertOrUpdate(producto);
		return new RedirectView(ViewRouteHelpers.PRODUCTO_ROOT);
	}
	
	@PostMapping("/delete/{id}")
	public RedirectView eliminar(@PathVariable("id") int id) {
		productoService.remove(id);
		return new RedirectView(ViewRouteHelpers.PRODUCTO_ROOT);
	}
	
	@GetMapping("/{id}")
	public ModelAndView get(@PathVariable("id") int idProducto) {
		ModelAndView mAV = new ModelAndView(ViewRouteHelpers.PRODUCTO_UPDATE);
		mAV.addObject("producto", productoService.findByIdProducto(idProducto));
		return mAV;
	}
	
	@PostMapping("/update")
	public RedirectView update(@ModelAttribute("producto") ProductoModel productoModel) {
		productoService.insertOrUpdate(productoModel);
		return new RedirectView(ViewRouteHelpers.PRODUCTO_ROOT);
	}
	
//*******************************************************************************************************	
	
	@GetMapping("/getLocalYFechas")
	public ModelAndView pedirLocalYFecha() {// aunque parezca igual al "distancia_L" van por rutas distintas 
		ModelAndView mAV = new ModelAndView(ViewRouteHelpers.PRODUCTO_PEDIRFECHAS);
		mAV.addObject("locales", localService.getAll());
		return mAV;
	}	
	
	@RequestMapping(value = "/devolverLocalYFechas", method = RequestMethod.POST)
	public ModelAndView traerLocalYFechas(@ModelAttribute("local") LocalModel local,
			@RequestParam("fecha1") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha1,
			@RequestParam("fecha2") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha2, Model model) {
		ModelAndView mAV = new ModelAndView(ViewRouteHelpers.MOSTRAR_PRODUCTO_ENTRE_FECHAS);
		Local localE = localRepository.findByIdLocal(local.getIdLocal());
		mAV.addObject("fecha1",fecha1);
		mAV.addObject("fecha2",fecha2);
		mAV.addObject("local",localConverter.entityToModel(localE));
		List<Pedido> pedidos = pedidosParaProductosVendidosEntreFechasDeUnLocal(localE,fecha1,fecha2); 
		if(!pedidos.isEmpty()) {
			mAV.addObject("pedidos",pedidos);
		}else {
			mAV.addObject("noHayVentas","No hay Productos vendidos para el local en el rango de fechas seleccionado");
		}
		return mAV;
	}



	//**********************************************************************	
	public List<Pedido> pedidosParaProductosVendidosEntreFechasDeUnLocal(Local local,LocalDate fecha1,LocalDate fecha2) {//se usa pedido solo para mostrar la cantidad y total de producto
		List<Pedido> listaPedSuma = new ArrayList<Pedido>();
		listaPedSuma = pedUnicosAcptdsDeLocEntreFechasAux(local,fecha1,fecha2); //hago esto para poder hacer cambios en la lista y devolver la misma
		for(Pedido pedido: listaPedSuma) {
			int cantidad=0;
			for(Pedido pediAux: pedidosPorLocal(local)) {//recorro todos los pedidos
				if(pediAux.isAceptado()==true && pediAux.getProducto().getNombre()==pedido.getProducto().getNombre()) {
 				 cantidad+=pediAux.getCantidad();
              }// cierra if 1
			}//cierra for pediAux
			pedido.setTotal(pedido.getProducto().getPrecio()*cantidad);
			pedido.setCantidad(cantidad);
		}//cierra for pedido
				
		return listaPedSuma;
	}		  

 public List<Pedido> pedUnicosAcptdsDeLocEntreFechasAux(Local local,LocalDate fecha1,LocalDate fecha2) {	
	 List<Pedido> listaPedidos = new ArrayList<Pedido>();
//   if(fecha1.isAfter(fecha2)){//si la Fecha1 es mayor a fecha2   ... en este caso se invierten para poder buscar en la BD
	  // listaPedidos = pedidoRepository.pedUnicosAceptadosDeUnLocalEntreFechas(local.getIdLocal(),fecha2,fecha1);
	  //  System.out.println( "Fecha 1 es mayor a fecha2");
	//}else {//si la Fecha2 es mayor a fecha1   o son iguales
		listaPedidos = pedidoRepository.pedUnicosAceptadosDeUnLocalEntreFechas(local.getIdLocal(),fecha1,fecha2);
	//}
	return listaPedidos;
 }	
	public List<Pedido> pedidosPorLocal(Local local){
		List<Pedido> listaPedido = new ArrayList<Pedido>();
		for(Pedido pedido: local.getPedidos()) {//recorro todos los pedidos
			listaPedido.add(pedido);//guardo el pedido
		}//for pedido
		return listaPedido;
	}	
	
//*******************************************************************************************************	

}//Fin class
