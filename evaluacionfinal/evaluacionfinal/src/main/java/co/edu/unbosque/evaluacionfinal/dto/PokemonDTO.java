package co.edu.unbosque.evaluacionfinal.dto;

import java.util.List;
import java.util.Map;

public class PokemonDTO {

    private int id;
    private String nombre;
    private double altura;        
    private double peso;           
    private int experienciaBase;
    private List<String> tipos;
    private List<String> habilidades;
    private Map<String, Integer> estadisticas;
    private String imagen;

    public PokemonDTO() {
    }

    public PokemonDTO(int id, String nombre, double altura, double peso, int experienciaBase,
            List<String> tipos, List<String> habilidades, Map<String, Integer> estadisticas, String imagen) {
        this.id = id;
        this.nombre = nombre;
        this.altura = altura;
        this.peso = peso;
        this.experienciaBase = experienciaBase;
        this.tipos = tipos;
        this.habilidades = habilidades;
        this.estadisticas = estadisticas;
        this.imagen = imagen;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getAltura() {
        return altura;
    }

    public void setAltura(double altura) {
        this.altura = altura;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public int getExperienciaBase() {
        return experienciaBase;
    }

    public void setExperienciaBase(int experienciaBase) {
        this.experienciaBase = experienciaBase;
    }

    public List<String> getTipos() {
        return tipos;
    }

    public void setTipos(List<String> tipos) {
        this.tipos = tipos;
    }

    public List<String> getHabilidades() {
        return habilidades;
    }

    public void setHabilidades(List<String> habilidades) {
        this.habilidades = habilidades;
    }

    public Map<String, Integer> getEstadisticas() {
        return estadisticas;
    }

    public void setEstadisticas(Map<String, Integer> estadisticas) {
        this.estadisticas = estadisticas;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }
}
