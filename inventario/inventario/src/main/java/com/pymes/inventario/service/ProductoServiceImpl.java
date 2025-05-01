package com.pymes.inventario.service;

import com.pymes.inventario.model.Producto;
import com.pymes.inventario.model.User;
import com.pymes.inventario.repository.ProductoRepository;
import com.pymes.inventario.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductoServiceImpl implements ProductoService {

    private final ProductoRepository productoRepository;
    private final UserRepository userRepository;
    private final AlertaCorreoService alertaCorreoService;

    public ProductoServiceImpl(ProductoRepository productoRepository,
                               UserRepository userRepository,
                               AlertaCorreoService alertaCorreoService) {
        this.productoRepository = productoRepository;
        this.userRepository = userRepository;
        this.alertaCorreoService = alertaCorreoService;
    }

    @Override
    public Page<Producto> listarProductos(Pageable pageable) {
        return productoRepository.findAll(pageable);
    }

    @Override
    public Producto obtenerProductoPorId(Long id) {
        return productoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado: " + id));
    }

    @Override
    public void guardarProducto(Producto producto) {
        productoRepository.save(producto);
    }

    @Override
    public void eliminarProducto(Long id) {
        productoRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void aumentarCantidad(Long id) {
        Producto producto = obtenerProductoPorId(id);
        producto.setCantidad(producto.getCantidad() + 1);
        productoRepository.save(producto);
    }

    @Override
    @Transactional
    public void disminuirCantidad(Long id) {
        Producto producto = obtenerProductoPorId(id);

        if (producto.getCantidad() > 0) {
            producto.setCantidad(producto.getCantidad() - 1);
            productoRepository.save(producto);

            if (producto.getCantidad() <= 5) {
                Authentication auth = SecurityContextHolder.getContext().getAuthentication();
                String username = auth.getName();
                User usuario = userRepository.findByUsername(username);

                if (usuario != null && usuario.getEmail() != null && !usuario.getEmail().isBlank()) {
                    alertaCorreoService.enviarAlerta(
                            usuario.getEmail(),
                            "⚠️ Inventario bajo: " + producto.getNombre(),
                            "Hola,\n\nEl producto '" + producto.getNombre() +
                                    "' tiene solo " + producto.getCantidad() + " unidades disponibles.\n\nSaludos,\nSistema de Inventario"
                    );
                }
            }
        }
    }
}
