const btnClubes = document.querySelector("#btn-clubes");

const painelClube = document.querySelector(".sidebar");
const painelCriar = document.querySelector(".painel-criar");

const itens = document.querySelectorAll('.card');

const criarClube = document.querySelector('.card-add');


function mostrarPainel(painelAtivo) {
    if (painelClube) painelClube.style.display = "flex";
    painelCriar.style.display = "none";

    painelAtivo.style.display = "block";

}

if (criarClube) {
    criarClube.addEventListener('click', () => {
        mostrarPainel(painelCriar);
    });
}


btnClubes.onclick = function() {
   mostrarPainel(painelCriar);
};



// CLUBES DINÂMICOS
document.addEventListener("DOMContentLoaded", () => {

    const cards = document.querySelectorAll(".card");
    const sidebar = document.querySelector(".sidebar-inscricao");
    const sidebarImg = document.getElementById("sidebar-img");
    const sidebarTitulo = document.getElementById("sidebar-titulo");
    const sidebarDescricao = document.getElementById("sidebar-descricao");
    const sidebarDias = document.getElementById("sidebar-dias");
    const sidebarHorario = document.getElementById("sidebar-horario");

    const infosAdicionais = {
        "Clube do UNO": { dias: "Segunda e Quinta", horario: "12hrs" },
        "Clube de Xadrez": { dias: "Terça e Sexta", horario: "12hrs" },
        "Clube da Música": { dias: "Quarta e Sexta", horario: "12hrs" },
        "Quebra Cabeça": { dias: "Terça e Quinta", horario: "12hrs" },
        "Futebol": { dias: "Quarta", horario: "12hrs" },
        "Clube do Cinema": { dias: "Todos os dias", horario: "12hrs" }
    };

    cards.forEach(card => {
        card.addEventListener("click", () => {
            const titulo = card.querySelector("h3").innerText;
            const descricao = card.querySelector("p").innerText;
            const imgSrc = card.querySelector(".card-image").getAttribute("src");

            sidebarImg.src = imgSrc;
            sidebarImg.alt = `Imagem do ${titulo}`;
            sidebarTitulo.innerText = titulo;
            sidebarDescricao.innerText = descricao;

            if (infosAdicionais[titulo]) {
                sidebarDias.innerText = infosAdicionais[titulo].dias;
                sidebarHorario.innerText = infosAdicionais[titulo].horario;
            } else {
                sidebarDias.innerText = "--";
                sidebarHorario.innerText = "--";
            }

            sidebar.classList.add("active");
        });
    });

    document.getElementById("btn-confirmar").addEventListener("click", () => {
        sidebar.classList.remove("active");
    });
});


//CLUBES DO ALUNO
const container = document.getElementById("clubes-container");

clubes.forEach(clube => {

    const card = document.createElement("div");
    card.classList.add("card-clube");

    card.innerHTML = `
        <img src="${clube.imagem}" alt="${clube.nome}">

        <div class="card-info">
            <h3>${clube.nome}</h3>
            <p>${clube.descricao}</p>
        </div>
    `;

    container.appendChild(card);
});


//  RESPONSIVO TELA HOME ALUNO

const sidebar = document.querySelector("#sidebar-inscricao");
const mainEl = document.querySelector("#main-content");

sidebar.classList.add("active");
if (window.innerWidth > 600) mainEl.classList.add("shifted");

sidebar.classList.remove("active");
mainEl.classList.remove("shifted");