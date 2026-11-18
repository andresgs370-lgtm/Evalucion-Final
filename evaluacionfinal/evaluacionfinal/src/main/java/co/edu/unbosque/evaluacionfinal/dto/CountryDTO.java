
package co.edu.unbosque.evaluacionfinal.dto;

public class CountryDTO {

    private String nombre;
    private String nombreOficial;
    private String capital;
    private String region;
    private String subregion;
    private long poblacion;
    private double area;
    private String idiomas;
    private String moneda;
    private String codigo;
    private String bandera;

    public CountryDTO() {
    }

    public CountryDTO(String nombre, String nombreOficial, String capital,
            String region, String subregion, long poblacion, double area,
            String idiomas, String moneda, String codigo, String bandera) {
        this.nombre = nombre;
        this.nombreOficial = nombreOficial;
        this.capital = capital;
        this.region = region;
        this.subregion = subregion;
        this.poblacion = poblacion;
        this.area = area;
        this.idiomas = idiomas;
        this.moneda = moneda;
        this.codigo = codigo;
        this.bandera = bandera;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNombreOficial() {
        return nombreOficial;
    }

    public void setNombreOficial(String nombreOficial) {
        this.nombreOficial = nombreOficial;
    }

    public String getCapital() {
        return capital;
    }

    public void setCapital(String capital) {
        this.capital = capital;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getSubregion() {
        return subregion;
    }

    public void setSubregion(String subregion) {
        this.subregion = subregion;
    }

    public long getPoblacion() {
        return poblacion;
    }

    public void setPoblacion(long poblacion) {
        this.poblacion = poblacion;
    }

    public double getArea() {
        return area;
    }

    public void setArea(double area) {
        this.area = area;
    }

    public String getIdiomas() {
        return idiomas;
    }

    public void setIdiomas(String idiomas) {
        this.idiomas = idiomas;
    }

    public String getMoneda() {
        return moneda;
    }

    public void setMoneda(String moneda) {
        this.moneda = moneda;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getBandera() {
        return bandera;
    }

    public void setBandera(String bandera) {
        this.bandera = bandera;
    }
}
