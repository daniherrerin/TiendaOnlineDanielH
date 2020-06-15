package tiendaOnline.Utilidades;

import java.io.File;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import org.springframework.web.multipart.MultipartFile;

import tiendaOnline.Entity.ImagenProducto;

public class Utilidades {

	private static final String rutaImage = System.getProperty("user.home") + "/images/";

	public static boolean isNumeric(String cadena) {
		try {
			Long.parseLong(cadena);
			return true;
		} catch (NumberFormatException nfe) {
			return false;
		}
	}

	public static <T> Set<T> convertListToSet(List<T> list) {
		Set<T> set = new HashSet<>();

		for (T t : list)
			set.add(t);

		return set;
	}

	public static ImagenProducto convertImage(MultipartFile imagenFile) throws IOException {

		ImagenProducto imagenProducto = new ImagenProducto();

		String ofn = imagenFile.getOriginalFilename();

		String suffix = ofn.substring(ofn.lastIndexOf("."));

		String filename = UUID.randomUUID().toString() + suffix;
		String filePath = rutaImage + filename;

		File file = new File(filePath);
		if (!file.getParentFile().exists()) {
			file.getParentFile().mkdirs();
		}
		
		try {
			imagenFile.transferTo(file);

		} catch (IllegalStateException | IOException e) {
			e.printStackTrace();
		}
		
		String urlImagen = "/images/" + filename;
		imagenProducto.setUrlImagen(urlImagen);

		return imagenProducto;

	}

	public static boolean validarImagen(String suffix) {
		if (suffix.equalsIgnoreCase("png") && suffix.equalsIgnoreCase("jpg") && suffix.equalsIgnoreCase("jpeg")
				&& suffix.equalsIgnoreCase("git")) {
			return true;
		} else {
			return false;
		}
	}

}
