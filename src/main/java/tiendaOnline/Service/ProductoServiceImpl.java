package tiendaOnline.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tiendaOnline.DAO.*;
import tiendaOnline.DTO.ProductosDto;
import tiendaOnline.Entity.*;

@Service
public class ProductoServiceImpl implements ProductoService {

	@Autowired
	private ProductosDAO productoDAO;
	
	List<Productos> lista = new ArrayList<>();

	@Override
	@Transactional
	public Productos save(Productos producto) {
		return productoDAO.create(producto);
	}

	@Override
	@Transactional
	public Productos findById(long id) {
		return productoDAO.findById(id);

	}

	@Override
	@Transactional
	public void delete(Productos producto) {
		File file = new File(System.getProperty("user.home") + producto.getImagen());
		if (file.exists()) {
			file.delete();
		}
		// Delete
		productoDAO.delete(producto.getIdProducto());
	}

	@Override
	@Transactional
	public List<Productos> getAll() {
		return productoDAO.getAll();
	}

	@Override
	@Transactional
	public Productos update(Productos producto) {
		return productoDAO.update(producto);
	}

	@Override
	@Transactional
	public Productos findByCodProducto(long codProducto) {
		return productoDAO.findByCodProducto(codProducto);
	}

	@Override
	@Transactional
	public List<Productos> findByNombre(String nombreProducto) {
		return productoDAO.findByNombre(nombreProducto);
	}

	@Override
	@Transactional
	public List<ProductosDto> findByNombreAndCodProducto(String nombreCodProducto) {
		return productoDAO.findByNombreAndCodProducto(nombreCodProducto);
	}

	@Override
	@Transactional
	public List<Productos> findByCategoria(Categoria categoria) {
		return productoDAO.findByCategoria(categoria);
	}

	@Override
	@Transactional
	public Page<Productos> paginadaProducto(Pageable pageable, int index, int limit, List<Productos> productos) {
		int pageSize = pageable.getPageSize();
		int currentPage = pageable.getPageNumber();
		int startItem = currentPage * pageSize;

		List<Productos> listaProducto = productos;

		if (lista.size() < startItem) {
			lista = Collections.emptyList();
		} else {
			int toIndex = Math.min(startItem + pageSize, listaProducto.size());
			lista = listaProducto.subList(startItem, toIndex);
		}

		System.err.println("...." + pageSize + " " + currentPage + " " + startItem + " ");

		Page<Productos> productoPage = new PageImpl<Productos>(lista, PageRequest.of(currentPage, pageSize),
				listaProducto.size());
		

		return productoPage;
	}

	@Override
	public List<Productos> getProductoDescuentoMayorQue0() {
		return productoDAO.getProductoDescuentoMayorQue0();
	}
}