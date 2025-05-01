package com.pymes.inventario.service;

import com.pymes.inventario.model.Producto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductoService {
    Page<Producto> listarProductos(Pageable pageable);
    Producto obtenerProductoPorId(Long id);
    void guardarProducto(Producto producto);
    void eliminarProducto(Long id);
    void aumentarCantidad(Long id);
    void disminuirCantidad(Long id);
}
