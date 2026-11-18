package co.edu.unbosque.evaluacionfinal.dto;

import java.util.List;

public class OpenverseResponseDTO {
	
	private int cantidadResultados;
	private List<OpenverseImageDTO> imagenes;
	
	 public OpenverseResponseDTO() {
	    }

	    public OpenverseResponseDTO(int cantidadResultados,
	            List<OpenverseImageDTO> imagenes) {
	        this.cantidadResultados = cantidadResultados;
	        this.imagenes = imagenes;
	    }

	    public int getCantidadResultados() {
	        return cantidadResultados;
	    }

	    public void setCantidadResultados(int cantidadResultados) {
	        this.cantidadResultados = cantidadResultados;
	    }

	    public List<OpenverseImageDTO> getImagenes() {
	        return imagenes;
	    }

	    public void setImagenes(List<OpenverseImageDTO> imagenes) {
	        this.imagenes = imagenes;
	    }

}
