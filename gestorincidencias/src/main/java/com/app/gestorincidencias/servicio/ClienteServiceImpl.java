package com.app.gestorincidencias.servicio;


import com.app.gestorincidencias.entidad.Cliente;
import com.app.gestorincidencias.repositorio.ClienteRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class ClienteServiceImpl implements ClienteServicio{

    @Autowired
    private ClienteRepositorio repositorio;

    @Override
    public Page<Cliente> buscarPorFiltros(String nombre, String apellidos, Long  telefono, String email, int page, int size) {
        return repositorio.buscarPorFiltros(nombre, apellidos, telefono, email, PageRequest.of(page, size));
    }

    @Override
    public Page<Cliente> listarClientes(int page, int size) {
        return repositorio.findAll(PageRequest.of(page, size));
    }

    @Override
    public Cliente guardarCliente(Cliente cliente) {
        return repositorio.save(cliente);
    }

    @Override
    public Cliente obtenerClientePorId(Long id) {
        return repositorio.findById(id).orElse(null);
    }

    @Override
    public Cliente actualizarCliente(Cliente cliente) {
        return repositorio.save(cliente);
    }

    @Override
    public Cliente eliminarCliente(Long id) {
        Cliente cliente = obtenerClientePorId(id);
        if (cliente != null) {
            repositorio.deleteById(id);
        }
        return cliente;
    }

}
