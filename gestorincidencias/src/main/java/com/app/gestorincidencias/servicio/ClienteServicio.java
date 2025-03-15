package com.app.gestorincidencias.servicio;

import com.app.gestorincidencias.entidad.Cliente;
import org.springframework.data.domain.Page;

public interface ClienteServicio {
    Page<Cliente> buscarPorFiltros(String nombre, String apellidos, String telefono, String email, int page, int size);
    Page<Cliente> listarTodosLosClientes(int page, int size);
    Cliente guardarCliente(Cliente cliente);
    Cliente obtenerClientePorId(Long id);
    Cliente actualizarCliente(Cliente cliente);
    void eliminarCliente(Long id);
}
