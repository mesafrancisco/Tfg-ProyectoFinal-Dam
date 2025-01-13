function limpiarCampos() {
    document.getElementById("titulo").value = "";  // Limpia el campo de título
    document.getElementById("descripcion").value = "";  // Limpia el campo de descripción
    document.getElementById("estado").value = "";  // Limpia el select de estado
    window.location = '/incidencias';  // Redirige a la lista de incidencias sin filtros
}
