package tiendaOnline.Controller;


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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import tiendaOnline.DTO.ProductosDto;
import tiendaOnline.Entity.Categoria;
import tiendaOnline.Entity.ImagenProducto;
import tiendaOnline.Entity.LineaCompra;
import tiendaOnline.Entity.Productos;
import tiendaOnline.Service.CategoriaService;
import tiendaOnline.Service.ClienteService;
import tiendaOnline.Service.ImagenProductoService;
import tiendaOnline.Service.LineaDeCompraService;
import tiendaOnline.Service.ProductoService;


@Controller
@RequestMapping(value = "/Producto")
public class ProductoController {

	@Autowired
	private ProductoService productoServer;
	@Autowired
	private ClienteService clienteServer;
	@Autowired
	private LineaDeCompraService lineaServer;
	@Autowired
	private ImagenProductoService imagenServer;
	@Autowired
	private CategoriaService categoriaServer;

	private int result = 0;

	@RequestMapping(method = RequestMethod.GET, value = "/create-producto")
	public String productsForm(Model model) {
		Productos producto = new Productos();
		model.addAttribute("products", producto);
		return "producto/add-producto";
	}

	@SuppressWarnings("rawtypes")
	@RequestMapping(method = RequestMethod.POST, value = "/create-producto")
	public @ResponseBody ResponseEntity addProducto(@RequestBody Productos producto, BindingResult bindingResult) {

		Productos p = productoServer.findByCodProducto(producto.getCodProducto());

		if (p != null) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
		productoServer.save(producto);
		return new ResponseEntity(HttpStatus.OK);

	}

	@RequestMapping(method = RequestMethod.GET, value = "/editar-producto/{idProducto}")
	public String update_producto(@PathVariable("idProducto") long id, Model model) {
		model.addAttribute("products", productoServer.findById(id));
		return "producto/update-producto";
	}

	@SuppressWarnings("rawtypes")
	@RequestMapping(method = RequestMethod.POST, value = "/editar-producto/{idProducto}")
	public @ResponseBody ResponseEntity update_producto_post(@PathVariable("idProducto") long id,
			@RequestBody @Valid Productos producto, HttpServletRequest request) {

		Productos p = productoServer.findById(id);
		if (p.getCodProducto() != producto.getCodProducto()) {
			if (productoServer.findByCodProducto(producto.getCodProducto()) != null) {
				return new ResponseEntity(HttpStatus.NOT_FOUND);

			}
		}
		productoServer.update(producto);
		return new ResponseEntity(HttpStatus.OK);

	}

	@GetMapping("/list-producto")
	public ModelAndView listAllProductos(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();

		mav.addObject("listaProductos", productoServer.getAll());
		mav.setViewName("producto/list-producto");
		return mav;
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/removeproducto/{idProducto}")
	public @ResponseBody ResponseEntity<Object> removeProducto(@PathVariable("idProducto") long idProducto) {

		Productos producto = productoServer.findById(idProducto);

		List<LineaCompra> linea = lineaServer.findByProducto(producto);
		Categoria categoria = categoriaServer.findByProducto(idProducto);
		List<ImagenProducto> imagen = imagenServer.findByProducto(producto);

		if (producto != null) {
			if (linea != null) {
				for (int i = 0; i < linea.size(); i++) {
					linea.get(i).setProductos(null);
					LineaCompra lineaComp = lineaServer.update(linea.get(i));
					System.err.println(lineaComp + " : Edidtar");

				}
			}

			if (categoria != null) {
				categoriaServer.eliminarProductoCateg(categoria.getIdCategoria(), idProducto);
			}

			if (imagen != null) {
				for (int i = 0; i < imagen.size(); i++) {
					imagenServer.delete(imagen.get(i));
				}

			}

			productoServer.delete(producto);
			return new ResponseEntity<>(HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);

	}

	@GetMapping("/list-product-user/{idCliente}")
	public ModelAndView listAllProductosForClients(@PathVariable("idCliente") long idCliente,
			@RequestParam("page") Optional<Integer> page, @RequestParam("size") Optional<Integer> size) {
		ModelAndView mav = new ModelAndView();

		int currentPage = page.orElse(1);
		int pageSize = size.orElse(8);

		Page<Productos> productoPage = productoServer.paginadaProducto(PageRequest.of(currentPage - 1, pageSize),
				result, 8, productoServer.getAll());

		int totalPages = productoPage.getTotalPages();

		if (totalPages > 0) {
			List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
			mav.addObject("pageNumbers", pageNumbers);
		}

		result = result + 8;
		mav.addObject("productoPage", productoPage);

		mav.addObject("Cliente", clienteServer.findById(idCliente));
		mav.addObject("listaCategoria", categoriaServer.getAll());
		mav.setViewName("producto/list-product-user");
		return mav;
	}

	@GetMapping("/perfil-producto/{idProducto}")
	public ModelAndView perfil_producto(@PathVariable("idProducto") long idProducto) {
		ModelAndView mav = new ModelAndView();

		mav.addObject("listaCategoria", categoriaServer.getAll());
		mav.addObject("Producto", productoServer.findById(idProducto));
		mav.setViewName("producto/perfil-producto");

		return mav;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/searchProducto/{titulo}")
	public @ResponseBody List<ProductosDto> buscarProductos(@PathVariable("titulo") String nombreProducto) {
		List<ProductosDto> listaProducto = productoServer.findByNombreAndCodProducto(nombreProducto);
		return listaProducto;
	}

}
