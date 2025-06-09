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

document.addEventListener("DOMContentLoaded", function () {
    const toggleButton = document.getElementById("dark-mode-toggle");
    const body = document.body;

    // Verificar si el usuario ya tenía activado el modo oscuro
    if (localStorage.getItem("dark-mode") === "enabled") {
        body.classList.add("dark-mode");
        toggleButton.textContent = "Modo Claro";
    }

    // Evento para cambiar el modo oscuro
    toggleButton.addEventListener("click", function () {
        body.classList.toggle("dark-mode");

        // Guardar preferencia en localStorage
        if (body.classList.contains("dark-mode")) {
            localStorage.setItem("dark-mode", "enabled");
            toggleButton.textContent = "Modo Claro";
        } else {
            localStorage.setItem("dark-mode", "disabled");
            toggleButton.textContent = "Modo Oscuro";
        }
    });
});

function toggleEditMode() {
  const inputs = document.querySelectorAll('form input.form-control');

  const isReadonly = inputs[0].hasAttribute('readonly'); // Asumimos que todos están igual

  inputs.forEach(input => {
    if (isReadonly) {
      input.removeAttribute('readonly');  // Activar edición
    } else {
      input.setAttribute('readonly', 'readonly'); // Volver a solo lectura
    }
  });

  // Alternar visibilidad de botones
  document.getElementById('editarBtn').classList.toggle('d-none');
  document.getElementById('guardarBtn').classList.toggle('d-none');
}




