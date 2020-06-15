package tiendaOnline.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import tiendaOnline.DTO.ProductosDto;
import tiendaOnline.Entity.Categoria;
import tiendaOnline.Entity.Productos;
import tiendaOnline.Service.CategoriaService;
import tiendaOnline.Service.ImagenProductoService;
import tiendaOnline.Service.ProductoService;

@Controller
@RequestMapping(value = "/Categoria")
public class CategoriaController {

	@Autowired
	private CategoriaService categoriaServer;

	@Autowired
	private ProductoService productoServer;

	@Autowired
	private ImagenProductoService imagenServer;

	private int result = 0;

	@RequestMapping(method = RequestMethod.GET, value = "/lista-categoria")
	public ModelAndView listaCategoria(HttpServletRequest request, @RequestParam("page") Optional<Integer> page,
			@RequestParam("size") Optional<Integer> size) {
		ModelAndView mav = new ModelAndView();
		long idCliente = (long) request.getSession().getAttribute("idUsuario");
		Categoria categoria = new Categoria();
		List<Categoria> listaCategoria = categoriaServer.getAll();

		mav.addObject("categoria", categoria);
		mav.addObject("listaCategoria", listaCategoria);
		mav.setViewName("categoria/list-categoria");

		return mav;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/lista_producto_nombreCategoria/{idCategoria}")
	public ModelAndView listaProductoCategoria(@PathVariable("idCategoria") long idCategoria, Model theModel,
			@RequestParam("page") Optional<Integer> page, @RequestParam("size") Optional<Integer> size) {
		ModelAndView mav = new ModelAndView();

		int currentPage = page.orElse(1);
		int pageSize = size.orElse(4);

		Page<Productos> productosPage = categoriaServer
				.findPaginatedCategProductos(PageRequest.of(currentPage - 1, pageSize), idCategoria, result, 4);
		mav.addObject("productoPage", productosPage);

		int totalPages = productosPage.getTotalPages();

		if (totalPages > 0) {
			List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
			mav.addObject("pageNumbers", pageNumbers);
		}

		mav.addObject("categoria", categoriaServer.findById(idCategoria));
		mav.addObject("imagenServer", imagenServer);
		mav.addObject("listaCategoria", categoriaServer.getAll());
		mav.addObject("listaProductos", categoriaServer.findCategProductosPaginada(idCategoria, result, 4));
		mav.setViewName("producto/list-product-user");
		result = result + 4;

		return mav;

	}

	@RequestMapping(method = RequestMethod.POST, value = "/create-categoria")
	public @ResponseBody Categoria crear_Categoria(@RequestBody String nombreCategoria) {

		System.err.println(nombreCategoria);

		if (categoriaServer.findByNombre(nombreCategoria) != null) {
			return null;
		}

		Categoria categoria = new Categoria();
		categoria.setNombreCategoria(nombreCategoria);
		categoria.setProducto(null);

		Categoria categSave = categoriaServer.save(categoria);

		return categSave;

	}

	@RequestMapping(method = RequestMethod.GET, value = "/{idCategoria}")
	public ModelAndView perfilCategoria(@PathVariable("idCategoria") long idCategoria) {
		ModelAndView mav = new ModelAndView();
		Categoria categoria = categoriaServer.findById(idCategoria);

		mav.addObject("categoria", categoria);
		mav.setViewName("categoria/perfil-categoria");
		return mav;
	}

	@SuppressWarnings("rawtypes")
	@RequestMapping(method = RequestMethod.POST, value = "/insertarProducto/{idCategoria}")
	public @ResponseBody ResponseEntity insertarProducto(@PathVariable("idCategoria") long idCategoria,
			@RequestBody ProductosDto productoDto) {
		Categoria categoria = categoriaServer.saveProductoCateg(idCategoria,
				productoServer.findById(productoDto.getIdProducto()));
		if (categoria == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity(HttpStatus.OK);

	}

	@SuppressWarnings("rawtypes")
	@RequestMapping(method = RequestMethod.DELETE, value = "/eliminarProducto/{idCategoria}/{idProducto}")
	public @ResponseBody ResponseEntity eliminarProducto(@PathVariable("idCategoria") Long idCategoria,
			@PathVariable("idProducto") long idProducto) {

		Categoria categoria = categoriaServer.eliminarProductoCateg(idCategoria, idProducto);

		if (categoria == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity(HttpStatus.OK);
	}

	@SuppressWarnings("rawtypes")
	@RequestMapping(method = RequestMethod.POST, value = "/update-nombreCategoria/{idCategoria}")
	public @ResponseBody ResponseEntity update_nombreCategoria(@PathVariable("idCategoria") Long idCategoria,
			@RequestBody String nombreCategoria) {
		Categoria categoria = categoriaServer.findById(idCategoria);

		categoria.setNombreCategoria(nombreCategoria);

		Categoria upd = categoriaServer.edit(categoria);
		if (upd == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity(HttpStatus.OK);
	}

	@SuppressWarnings("rawtypes")
	@RequestMapping(method = RequestMethod.DELETE, value = "/eliminarCategoria/{idCategoria}")
	public @ResponseBody ResponseEntity eliminarCategoria(@PathVariable("idCategoria") Long idCategoria) {

		categoriaServer.delete(categoriaServer.findById(idCategoria));

		return new ResponseEntity(HttpStatus.OK);
	}

}
