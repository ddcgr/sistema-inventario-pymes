package com.pymes.inventario.controller;

import com.pymes.inventario.model.Producto;
import com.pymes.inventario.service.ProductoService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/productos")
public class ProductoController {

    private final ProductoService productoService;

    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    @GetMapping
    public String listarProductos(Model model,
                                   @RequestParam(defaultValue = "0") int page) {
        Pageable pageable = PageRequest.of(page, 5);
        Page<Producto> productos = productoService.listarProductos(pageable);
        model.addAttribute("productos", productos);
        model.addAttribute("paginaActual", page);
        return "productos/listar";
    }

    @GetMapping("/nuevo")
    public String mostrarFormularioProducto(Model model) {
        model.addAttribute("producto", new Producto());
        return "productos/formulario";
    }

    @PostMapping
    public String guardarProducto(@ModelAttribute("producto") @Valid Producto producto,
                                   BindingResult result,
                                   RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "productos/formulario";
        }
        productoService.guardarProducto(producto);
        redirectAttributes.addFlashAttribute("mensaje", "Producto guardado exitosamente");
        return "redirect:/productos";
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Long id, Model model) {
        Producto producto = productoService.obtenerProductoPorId(id);
        model.addAttribute("producto", producto);
        return "productos/formulario";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarProducto(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        productoService.eliminarProducto(id);
        redirectAttributes.addFlashAttribute("mensaje", "Producto eliminado exitosamente");
        return "redirect:/productos";
    }

    @PostMapping("/aumentar/{id}")
    public String aumentarCantidad(@PathVariable Long id) {
        productoService.aumentarCantidad(id);
        return "redirect:/productos";
    }

    @PostMapping("/disminuir/{id}")
    public String disminuirCantidad(@PathVariable Long id) {
        productoService.disminuirCantidad(id);
        return "redirect:/productos";
    }
}
