package com.app.gestorincidencias.controlador;

import com.app.gestorincidencias.entidad.Cliente;
import com.app.gestorincidencias.entidad.Incidencia;
import com.app.gestorincidencias.servicio.ClienteServicio;
import com.app.gestorincidencias.servicio.IncidenciaServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class ClienteController {

    @Autowired
    private ClienteServicio clienteServicio;

    @Autowired
    private IncidenciaServicio incidenciaServicio;

    // Listar clientes con filtros y paginación
    @GetMapping({"/clientes", "/"})
    public String listarClientes(
            Model modelo,
            @RequestParam(value = "nombre", required = false) String nombre,
            @RequestParam(value = "apellidos", required = false) String apellidos,
            @RequestParam(value = "telefono", required = false) Long  telefono,
            @RequestParam(value = "email", required = false) String email,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {

        Page<Cliente> clientesPage;

        if ((nombre != null && !nombre.isEmpty()) ||
                (apellidos != null && !apellidos.isEmpty()) ||
                (telefono != null && telefono != 0) ||
                (email != null && !email.isEmpty())) {

            clientesPage = clienteServicio.buscarPorFiltros(nombre, apellidos, telefono, email, page, size);
        } else {
            clientesPage = clienteServicio.listarClientes(page, size);
        }

        modelo.addAttribute("clientesPage", clientesPage);
        modelo.addAttribute("nombre", nombre);
        modelo.addAttribute("apellidos", apellidos);
        modelo.addAttribute("telefono", telefono);
        modelo.addAttribute("email", email);
        modelo.addAttribute("currentPage", page);
        modelo.addAttribute("totalPages", clientesPage.getTotalPages());
        modelo.addAttribute("size", size);


        return "clientes";
    }


    // Mostrar formulario para crear nuevo cliente
    @GetMapping("/clientes/nuevo")
    public String mostrarFormularioNuevoCliente(Model modelo) {
        Cliente cliente = new Cliente();
        modelo.addAttribute("cliente", cliente);
        return "crear_cliente"; // nombre de la plantilla para crear cliente
    }

    // Guardar nuevo cliente
    @PostMapping("/clientes")
    public String guardarCliente(@ModelAttribute("cliente") Cliente cliente) {
        clienteServicio.guardarCliente(cliente);
        return "redirect:/clientes";
    }

    // Mostrar formulario para editar cliente
    @GetMapping("/clientes/editar/{id}")
    public String mostrarFormularioEditarCliente(@PathVariable Long id, Model modelo) {
        Cliente cliente = clienteServicio.obtenerClientePorId(id);

        // Obtener las incidencias del cliente
        List<Incidencia> incidencias = incidenciaServicio.obtenerIncidenciasPorClienteId(id);

        modelo.addAttribute("cliente", cliente);
        modelo.addAttribute("incidencias", incidencias); // Añadir incidencias al modelo

        return "editar_cliente";
    }


    // Actualizar cliente
    @PostMapping("/clientes/{id}")
    public String actualizarCliente(@PathVariable Long id, @ModelAttribute("cliente") Cliente cliente) {
        Cliente clienteExistente = clienteServicio.obtenerClientePorId(id);
        clienteExistente.setId(id);
        clienteExistente.setNombre(cliente.getNombre());
        clienteExistente.setApellidos(cliente.getApellidos());
        clienteExistente.setDni(cliente.getDni());
        clienteExistente.setEmail(cliente.getEmail());
        clienteExistente.setTelefono(cliente.getTelefono());
        clienteServicio.actualizarCliente(clienteExistente);
        return "redirect:/clientes";
    }

    // Eliminar cliente
    @GetMapping("/clientes/eliminar/{id}")
    public String eliminarCliente(@PathVariable Long id) {
        clienteServicio.eliminarCliente(id);
        return "redirect:/clientes";
    }

    @GetMapping("/detalle/{id}")
    public String mostrarDetalleCliente(@PathVariable Long id, Model modelo) {
        Cliente cliente = clienteServicio.obtenerClientePorId(id);
        if (cliente == null) {
            return "redirect:/clientes";
        }
        modelo.addAttribute("cliente", cliente);

        List<Incidencia> incidencias = incidenciaServicio.listarPorClienteId(id);
        modelo.addAttribute("incidencias", incidencias);

        return "detalle_cliente";
    }

    @GetMapping("/clientes/{clienteId}/incidencias")
    public String listarIncidenciasDeCliente(@PathVariable Long clienteId, Model modelo) {
        List<Incidencia> incidencias = incidenciaServicio.listarPorClienteId(clienteId);
        modelo.addAttribute("incidencias", incidencias);
        return "incidencias_cliente"; // nombre de la vista .html/.jsp
    }

}
