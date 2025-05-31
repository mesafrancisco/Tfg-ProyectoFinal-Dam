package com.app.gestorincidencias.servicio;

import com.app.gestorincidencias.entidad.Cliente;
import org.springframework.data.domain.Page;

public interface ClienteServicio {

    Page<Cliente> listarClientes(int page, int size);

    Page<Cliente> buscarPorFiltros(String nombre, String apellidos, Long  telefono, String email, int page, int size);

    Cliente guardarCliente(Cliente cliente);

    Cliente obtenerClientePorId(Long id);

    Cliente actualizarCliente(Cliente cliente);

    Cliente eliminarCliente(Long id);
}
