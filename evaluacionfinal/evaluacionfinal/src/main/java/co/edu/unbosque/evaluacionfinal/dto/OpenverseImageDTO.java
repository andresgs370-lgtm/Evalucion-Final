package co.edu.unbosque.evaluacionfinal.dto;

public class OpenverseImageDTO {
	
	private String id;
	private String titulo;
	private String url;
	private String miniatura;
	private String autor;
	private String licencia;
	private String licenciaVersion;
	private String enlaceOriginal;
	
	  public OpenverseImageDTO() {
	    }

	    public OpenverseImageDTO(String id, String titulo, String url,
	            String miniatura, String autor, String licencia,
	            String licenciaVersion, String enlaceOriginal) {
	        this.id = id;
	        this.titulo = titulo;
	        this.url = url;
	        this.miniatura = miniatura;
	        this.autor = autor;
	        this.licencia = licencia;
	        this.licenciaVersion = licenciaVersion;
	        this.enlaceOriginal = enlaceOriginal;
	    }

	    public String getId() {
	        return id;
	    }

	    public void setId(String id) {
	        this.id = id;
	    }

	    public String getTitulo() {
	        return titulo;
	    }

	    public void setTitulo(String titulo) {
	        this.titulo = titulo;
	    }

	    public String getUrl() {
	        return url;
	    }

	    public void setUrl(String url) {
	        this.url = url;
	    }

	    public String getMiniatura() {
	        return miniatura;
	    }

	    public void setMiniatura(String miniatura) {
	        this.miniatura = miniatura;
	    }

	    public String getAutor() {
	        return autor;
	    }

	    public void setAutor(String autor) {
	        this.autor = autor;
	    }

	    public String getLicencia() {
	        return licencia;
	    }

	    public void setLicencia(String licencia) {
	        this.licencia = licencia;
	    }

	    public String getLicenciaVersion() {
	        return licenciaVersion;
	    }

	    public void setLicenciaVersion(String licenciaVersion) {
	        this.licenciaVersion = licenciaVersion;
	    }

	    public String getEnlaceOriginal() {
	        return enlaceOriginal;
	    }

	    public void setEnlaceOriginal(String enlaceOriginal) {
	        this.enlaceOriginal = enlaceOriginal;
	    }

}
