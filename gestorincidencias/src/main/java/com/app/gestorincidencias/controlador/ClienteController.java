package com.app.gestorincidencias.controlador;

import com.app.gestorincidencias.entidad.Cliente;
import com.app.gestorincidencias.entidad.Incidencia;
import com.app.gestorincidencias.servicio.ClienteServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ClienteController {

    @Autowired
    private ClienteServicio clienteServicioservicio;

    @GetMapping({"/clientes", "/"})
    public String listarClientes(Model modelo,
                                 @RequestParam(value = "nombre", required = false) String nombre,
                                 @RequestParam(value = "apellidos", required = false) String apellidos,
                                 @RequestParam(value = "telefono", required = false) String telefono,
                                 @RequestParam(value = "email", required = false) String email,
                                 @RequestParam(value = "page", defaultValue = "0") int page,
                                 @RequestParam(value = "size", defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<Cliente> clientesPage;

        if (nombre != null || apellidos != null || telefono != null || email != null) {
            clientesPage = clienteServicioservicio.buscarPorFiltros(nombre, apellidos, telefono, email, page, size);
        } else {
            clientesPage = clienteServicioservicio.listarTodosLosClientes(page, size);
        }

        modelo.addAttribute("clientes", clientesPage.getContent());
        modelo.addAttribute("currentPage", page);
        modelo.addAttribute("totalPages", clientesPage.getTotalPages());
        modelo.addAttribute("size", size);

        return "clientes";
    }

    @GetMapping("/clientes/nuevo")
    public String mostrarFormularioDeCrearCliente(Model modelo) {
        Cliente cliente = new Cliente();
        modelo.addAttribute("cliente", cliente); // Asegúrate de que el nombre sea "cliente" (en minúsculas)
        return "crear_cliente"; // Esta es la plantilla que vamos a mostrar
    }

    @PostMapping("/clientes")
    public String guardarCliente(@ModelAttribute("cliente") Cliente cliente) {
        clienteServicioservicio.actualizarCliente(cliente);
        return "redirect:/clientes";
    }

    @GetMapping("/detalle/{id}")
    public String detalleCliente(@PathVariable Long id, Model model) {
        Cliente cliente = clienteServicioservicio.obtenerClientePorId(id);
        model.addAttribute("cliente", cliente);
        return "detalle_cliente";
    }

    @PostMapping("/clientes/actualizar")
    public String actualizarCliente(@ModelAttribute("cliente") Cliente cliente) {
        Cliente clienteExistente = clienteServicioservicio.obtenerClientePorId(cliente.getId());
        if (clienteExistente == null) {
            return "redirect:/error";
        }
        clienteServicioservicio.actualizarCliente(cliente);
        return "redirect:/detalle/" + cliente.getId();
    }


}
