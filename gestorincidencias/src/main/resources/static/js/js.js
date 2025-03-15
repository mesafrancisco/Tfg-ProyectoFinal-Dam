function limpiarCampos() {
    document.getElementById("titulo").value = "";  // Limpia el campo de título
    document.getElementById("descripcion").value = "";  // Limpia el campo de descripción
    document.getElementById("estado").value = "";  // Limpia el select de estado
    window.location = '/incidencias';  // Redirige a la lista de incidencias sin filtros
}

    let arrow = document.querySelectorAll(".arrow");
    for(let i = 0; i < arrow.length; i++){
        arrow[i].addEventListener("click", (e) => {
            let arrowParent = e.target.parentElement.parentElement;
            arrowParent.classList.toggle("showMenu");
        });
    }

    let sidebar = document.querySelector(".sidebar");
    let sidebarBtn = document.querySelector(".bx-menu");
    console.log(sidebarBtn);
    sidebarBtn.addEventListener("click", () => {
        sidebar.classList.toggle("close");
    });

function toggleFiltro() {
  var filtro = document.getElementById("filtroContainer");
  if (filtro.style.display === "none" || filtro.style.display === "") {
    filtro.style.display = "block";
  } else {
    filtro.style.display = "none";
  }
}

function toggleEditMode() {
    let inputs = document.querySelectorAll(".form-control");
    let isReadOnly = inputs[0].hasAttribute("readonly");

    inputs.forEach(input => {
        if (isReadOnly) {
            input.removeAttribute("readonly");
        } else {
            input.setAttribute("readonly", "readonly");
        }
    });

    document.getElementById("editarBtn").classList.toggle("d-none");
    document.getElementById("guardarBtn").classList.toggle("d-none");
}
