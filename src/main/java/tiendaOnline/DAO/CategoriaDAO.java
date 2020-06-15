package tiendaOnline.DAO;

import java.util.List;

import tiendaOnline.Entity.Categoria;
import tiendaOnline.Entity.Productos;

public interface CategoriaDAO extends GenericDAO<Categoria> {
	
	public List<Categoria> getAll();
	
	public Categoria findProducto(long idProducto);
	
	public Categoria saveProductoCateg(long idCategoria, Productos producto);
	
	public List<Productos> findCategProductos(long idCategoria);
	
	public Categoria findByNombre(String nombreCategoria);

	public Categoria eliminarProductoCateg(long idCategoria, Productos producto);

	List<Productos> findCategProductosPaginada(long idCategoria, int index, int limit);
}
