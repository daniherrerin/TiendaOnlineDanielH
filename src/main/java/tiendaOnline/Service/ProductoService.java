package tiendaOnline.Service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import tiendaOnline.DTO.ProductosDto;
import tiendaOnline.Entity.Categoria;
import tiendaOnline.Entity.Productos;

public interface ProductoService {

	public Productos save(Productos producto);

	public Productos findById(long id);

	public Productos findByCodProducto(long codProducto);

	public List<Productos> findByNombre(String valor);

	public void delete(Productos producto);

	public Productos update(Productos producto);

	public List<Productos> getAll();

	public List<ProductosDto> findByNombreAndCodProducto(String nombreCodProducto);

	public List<Productos> findByCategoria(Categoria categoria);

	public Page<Productos> paginadaProducto(Pageable pageable, int index, int limit, List<Productos> listaProducto);
	

	
	public List<Productos> getProductoDescuentoMayorQue0();


}