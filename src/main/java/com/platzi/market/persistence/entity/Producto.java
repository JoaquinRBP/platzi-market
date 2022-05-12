package com.platzi.market.persistence.entity;

import javax.persistence.*;
import java.util.List;

@Entity
//Como tenemos nombres diferentes
@Table(name = "productos")
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //Se genera automaticamente
    @Column(name = "id_producto")
    private Long idProducto;
    private String nombre;
    @Column(name = "id_categoria")
    private Long idCategoria;
    @Column(name = "codigo_barras")
    private String codigoBarras;
    @Column(name = "precio_venta")
    private Double precioVentas;
    @Column(name = "cantidad_stock")
    private Integer cantidadStock;
    private Boolean estado;

    @ManyToOne
    @JoinColumn(name = "id_categoria", insertable = false, updatable = false)
    private Categoria categoria;

    @OneToMany(mappedBy = "producto")
    private List<ComprasProducto> comprasProductos;

    public Long getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(Long idProducto) {
        this.idProducto = idProducto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Long getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(Long idCategoria) {
        this.idCategoria = idCategoria;
    }

    public String getCodigoBarras() {
        return codigoBarras;
    }

    public void setCodigoBarras(String codigoBarras) {
        this.codigoBarras = codigoBarras;
    }

    public Double getPrecioVentas() {
        return precioVentas;
    }

    public void setPrecioVentas(Double precioVentas) {
        this.precioVentas = precioVentas;
    }

    public Integer getCantidadStock() {
        return cantidadStock;
    }

    public void setCantidadStock(Integer cantidadStock) {
        this.cantidadStock = cantidadStock;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public List<ComprasProducto> getComprasProductos() {
        return comprasProductos;
    }

    public void setComprasProductos(List<ComprasProducto> comprasProductos) {
        this.comprasProductos = comprasProductos;
    }
}
