package com.pymes.inventario;

import com.pymes.inventario.model.Producto;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ProductoTest {

    @Test
    public void testProductoCreacion() {
        Producto producto = new Producto();
        producto.setNombre("Galletas");
        producto.setCantidad(10);
        producto.setPrecio(25.5);

        assertEquals("Galletas", producto.getNombre());
        assertEquals(10, producto.getCantidad());
        assertEquals(25.5, producto.getPrecio());
    }
}
