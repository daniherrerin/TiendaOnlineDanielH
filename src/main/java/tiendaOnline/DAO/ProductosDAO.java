package tiendaOnline.DAO;

import java.util.List;

import tiendaOnline.DTO.ProductosDto;
import tiendaOnline.Entity.Categoria;
import tiendaOnline.Entity.Productos;

public interface ProductosDAO extends GenericDAO<Productos>{


	public Productos findById(long id);

	public Productos findByCodProducto(long codProducto);
	
	public List<Productos> findByNombre(String nombreProdcuto);

	public List<Productos> getAll();
	
	public List<ProductosDto> findByNombreAndCodProducto(String nombreCodProducto);
	
	public List<Productos> findByCategoria(Categoria categoria);
	
	public List<Productos> getProductoDescuentoMayorQue0();

}
