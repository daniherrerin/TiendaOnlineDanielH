package tiendaOnline.Controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import tiendaOnline.DAO.RolRepository;
import tiendaOnline.Entity.Banco;
import tiendaOnline.Entity.Clientes;
import tiendaOnline.Entity.Compra;
import tiendaOnline.Entity.LineaCompra;
import tiendaOnline.Service.BancoService;
import tiendaOnline.Service.CategoriaService;
import tiendaOnline.Service.ClienteService;
import tiendaOnline.Service.CompraService;
import tiendaOnline.Service.LineaDeCompraService;


@Controller
@RequestMapping(value = "/Cliente")
public class ClienteController {
	@Autowired
	private ClienteService ClienteServer;
	@Autowired
	private BancoService bancoServer;
	@Autowired
	private LineaDeCompraService lineaServer;
	@Autowired
	private CompraService compraServer;
	@Autowired
	private RolRepository rol;
	@Autowired
	private CategoriaService categoriaServer;
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@RequestMapping(method = RequestMethod.GET, value = "/signup")
	public String showForm(Model theModel) {
		Clientes cliente = new Clientes();
		theModel.addAttribute("listaCategoria", categoriaServer.getAll());
		theModel.addAttribute("Cliente", cliente);
		return "signup";
	}

	@SuppressWarnings("rawtypes")
	@RequestMapping(method = RequestMethod.POST, value = "/signup")
	public @ResponseBody ResponseEntity create_cliente(@RequestBody Clientes cliente, BindingResult bindingResult) {
		if (ClienteServer.findByEmail(cliente.getEmail()) != null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		ClienteServer.save(cliente);
		return new ResponseEntity<>(HttpStatus.OK);

	}

	@RequestMapping(method = RequestMethod.GET, value = "/perfil-cliente/{idCliente}")
	public ModelAndView profile(HttpServletRequest request, @PathVariable("idCliente") long idCliente) {
		ModelAndView mav = new ModelAndView();
		HttpSession session = request.getSession(true);
		if (session != null && session.getAttribute("idUsuario") != null) {
			mav.addObject("Cliente", ClienteServer.findById(idCliente));
			mav.addObject("listaBanco", bancoServer.findByCliente(ClienteServer.findById(idCliente)));
			mav.addObject("listaCategoria", categoriaServer.getAll());
			mav.setViewName("perfil-cliente");
		} else {
			mav.setViewName("indice");
		}

		return mav;
	}

	@GetMapping("/list/{idCliente}")
	public ModelAndView listUsers(@PathVariable("idCliente") long idCliente, Model theModel) {
		ModelAndView mav = new ModelAndView();
		List<Clientes> lCliente = ClienteServer.getAll();
		theModel.addAttribute("listaCliente", lCliente);
		mav.addObject("Cliente", ClienteServer.findById(idCliente));
		mav.setViewName("list-cliente");
		return mav;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/editar-cliente/{idCliente}")
	public String get_update_cliente(@PathVariable("idCliente") long id, Model model) {
		Clientes cliente = ClienteServer.findById(id);
		model.addAttribute("Cliente", cliente);
		model.addAttribute("listaCategoria", categoriaServer.getAll());
		return "update-cliente";

	}

	@SuppressWarnings("rawtypes")
	@RequestMapping(method = RequestMethod.POST, value = "/editar-cliente/{idCliente}")
	public @ResponseBody ResponseEntity post_update_cliente(@PathVariable("idCliente") long idCliente,
			@RequestBody Clientes cliente, BindingResult result) {

		Clientes clienteAnt = ClienteServer.findById(idCliente);

		if (!clienteAnt.getEmail().equalsIgnoreCase(cliente.getEmail())) {
			if (ClienteServer.findByEmail(cliente.getEmail()) != null) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}

		}

		if (!passwordEncoder.matches(cliente.getPassword(), clienteAnt.getPassword())) {
			clienteAnt.setPassword(passwordEncoder.encode(cliente.getPassword()));
		}

		clienteAnt.setApellido(cliente.getApellido());
		clienteAnt.setNombre(cliente.getNombre());
		clienteAnt.setDireccion(cliente.getDireccion());
		clienteAnt.setEmail(cliente.getEmail());
		clienteAnt.setFnacimiento(cliente.getFnacimiento());

		ClienteServer.update(clienteAnt);
		return new ResponseEntity<>(HttpStatus.OK);

	}

	@RequestMapping(method = RequestMethod.GET, value = "/delete-cliente/{idCliente}")
	public ModelAndView delete_cliente(@PathVariable("idCliente") long idCliente) {
		ModelAndView mav = new ModelAndView();

		Clientes cliente = ClienteServer.findById(idCliente);

		if (cliente != null) {
			List<Banco> listaBanco = bancoServer.findByCliente(cliente);
			List<Compra> compra = compraServer.findByCliente(cliente);

			if (listaBanco != null) {
				for (int i = 0; i < listaBanco.size(); i++) {
					bancoServer.delete(listaBanco.get(i).getIdBanco());
				}
			}

			if (compra != null) {
				for (int i = 0; i < compra.size(); i++) {
					List<LineaCompra> linea = lineaServer.findByCompra(compra.get(i));
					if (linea != null) {
						for (int j = 0; j < linea.size(); j++) {
							linea.get(i).setProductos(null);
							LineaCompra comp = lineaServer.update(linea.get(i));
							if (comp != null) {
								lineaServer.delete(linea.get(j).getIdLineaCompra());
							}
						}
						compra.get(i).setClientes(null);
						Compra compMod = compraServer.update(compra.get(i));
						if (compMod != null) {
							compraServer.delete(compra.get(i).getIdCompra());
						}
					}
				}

			}

			if (cliente != null) {
				ClienteServer.delete(cliente);
			}

			List<Clientes> listaCliente = ClienteServer.getAll();
			mav.addObject("listaCategoria", categoriaServer.getAll());
			mav.addObject("listaCliente", listaCliente);
			mav.setViewName("list-cliente");

		}
		return mav;
	}

}
