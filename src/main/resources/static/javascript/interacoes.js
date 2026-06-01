document.addEventListener("DOMContentLoaded", () => {

    // ─── ADMIN: painel criar/editar ───────────────────────────────
    const btnClubes = document.querySelector("#btn-clubes");
    const painelClube = document.querySelector(".sidebar");
    const painelCriar = document.querySelector(".painel-criar");
    const painelEditar = document.querySelector(".painel-editar");
    const criarClube = document.querySelector('.card-add');

    function mostrarPainel(painelAtivo) {
        if (!painelCriar || !painelEditar) return;
        painelCriar.style.display = "none";
        painelEditar.style.display = "none";

        if (painelAtivo) {
            painelClube.style.display = "flex";
            painelAtivo.style.display = "block";
        } else {
            painelClube.style.display = "none";
        }
    }

    if (criarClube) {
        criarClube.addEventListener('click', () => mostrarPainel(painelCriar));
    }

    if (btnClubes) {
        btnClubes.onclick = () => mostrarPainel(painelCriar);
    }

    // ─── CARDS ────────────────────────────────────────────────────
    const cards = document.querySelectorAll(".card");
    const sidebar = document.querySelector(".sidebar-inscricao");
    const sidebarImg = document.getElementById("sidebar-img");
    const sidebarTitulo = document.getElementById("sidebar-titulo");
    const sidebarDescricao = document.getElementById("sidebar-descricao");
    const sidebarDias = document.getElementById("sidebar-dias");
    const sidebarHorario = document.getElementById("sidebar-horario");

    let clubeSelecionadoId = null;

    cards.forEach(card => {
        card.addEventListener("click", () => {
            const titulo = card.querySelector("h3").innerText;
            const descricao = card.querySelector("p").innerText;
            const imgSrc = card.querySelector(".card-image").getAttribute("src");

            sidebarImg.src = imgSrc;
            sidebarImg.alt = `Imagem do ${titulo}`;
            sidebarTitulo.innerText = titulo;
            sidebarDescricao.innerText = descricao;
            sidebarDias.innerText = card.dataset.dias || "--";
            sidebarHorario.innerText = card.dataset.horario || "--";

            clubeSelecionadoId = card.dataset.id;

            // Atualiza form de matrícula (página do aluno)
            const formMatricula = document.getElementById("form-matricula");
            if (formMatricula) {
                formMatricula.setAttribute("action", `/aluno/matricular/${clubeSelecionadoId}`);
            }

            console.log("ID do clube:", clubeSelecionadoId);

            sidebar.classList.add("active");
        });
    });

    // ─── ADMIN: excluir ───────────────────────────────────────────
    const btnExcluir = document.getElementById("btn-excluir");
    if (btnExcluir) {
        btnExcluir.addEventListener("click", () => {
            if (!clubeSelecionadoId) return;
            const confirmou = confirm("Tem certeza que deseja excluir este clube?");
            if (confirmou) {
                window.location.href = `/admin/clube/deletar/${clubeSelecionadoId}`;
            }
        });
    }

    // ─── ADMIN: editar ────────────────────────────────────────────
    const btnEditar = document.getElementById("btn-editar");
    if (btnEditar) {
        btnEditar.addEventListener("click", () => {
            if (!clubeSelecionadoId) return;

            document.getElementById("edit-nome").value = sidebarTitulo.innerText;
            document.getElementById("edit-descricao").value = sidebarDescricao.innerText;
            document.getElementById("edit-diaSemana").value = sidebarDias.innerText;
            document.getElementById("edit-horario").value = sidebarHorario.innerText;
            document.getElementById("form-editar").action = `/admin/clube/editar/${clubeSelecionadoId}`;

            sidebar.classList.remove("active");
            mostrarPainel(painelEditar);
        });
    }

    // ─── FECHAR AO CLICAR FORA ────────────────────────────────────
    document.addEventListener("click", (e) => {
        const sidebarInscricao = document.querySelector(".sidebar-inscricao");
        const sidebarCriar = document.querySelector(".sidebar");

        if (!sidebarInscricao || !sidebarCriar) return;

        const clicouForaDaInscricao = !sidebarInscricao.contains(e.target);
        const clicouForaDoCriar = !sidebarCriar.contains(e.target);
        const clicouNoCard = e.target.closest(".card");
        const clicouNoBtnClubes = e.target.closest("#btn-clubes");
        const clicouNoBtnCriar = e.target.closest(".card-add");
        const clicouNaSidebarInscricao = sidebarInscricao.contains(e.target);

        if (clicouForaDaInscricao && !clicouNoCard) {
            sidebarInscricao.classList.remove("active");
        }

        if (clicouForaDoCriar && !clicouNoBtnClubes && !clicouNoBtnCriar && !clicouNaSidebarInscricao) {
            mostrarPainel(null);
        }
    });

    // ─── UPLOAD IMAGENS ───────────────────────────────────────────
    const inputImagem = document.getElementById('imagem');
    if (inputImagem) {
        inputImagem.addEventListener('change', function () {
            document.getElementById('nome-arquivo').textContent = this.files[0]?.name || 'Nenhum arquivo';
        });
    }

    const inputImagemEditar = document.getElementById('imagem-editar');
    if (inputImagemEditar) {
        inputImagemEditar.addEventListener('change', function () {
            document.getElementById('nome-arquivo-editar').textContent = this.files[0]?.name || 'Nenhum arquivo';
        });
    }

    // ─── ALUNO: fechar sidebar ────────────────────────────────────
    const btnConfirmar = document.getElementById("btn-confirmar");
        if (btnConfirmar) {
            btnConfirmar.addEventListener("click", () => {
                sidebar.classList.remove("active");
        });
    }
});