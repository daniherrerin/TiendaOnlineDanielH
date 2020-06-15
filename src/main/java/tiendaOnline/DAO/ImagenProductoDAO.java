package tiendaOnline.DAO;

import java.util.List;

import tiendaOnline.Entity.ImagenProducto;
import tiendaOnline.Entity.Productos;

public interface ImagenProductoDAO extends GenericDAO<ImagenProducto>{
	
	public List<ImagenProducto> findByProducto(Productos producto);
	
	public List<ImagenProducto> getAll();
	
	public ImagenProducto findByUrl(String url);
	
	public ImagenProducto findByImagen(byte[] imagen);
	
	public ImagenProducto findProductoImagen(long idImagen, long idProducto);


}
