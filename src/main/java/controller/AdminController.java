package controller;

import models.Clube;
import models.Aluno;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import repositories.AlunoRepository;
import repositories.ClubeRepository;
import repositories.MatriculaRepository;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private ClubeRepository clubeRepository;

    @Autowired
    private AlunoRepository alunoRepository;

    @Autowired
    private MatriculaRepository matriculaRepository;

    // ── Painel admin ──
    @GetMapping
    public String painel(Model model) {
        model.addAttribute("clubes", clubeRepository.findAll());
        model.addAttribute("alunos", alunoRepository.findAll());
        return "admHome";
    }

    // ── Clubes ──
    @GetMapping("/clube/novo")
    public String formNovoClube(Model model) {
        model.addAttribute("clube", new Clube());
        return "formClube";
    }

    @PostMapping("/clube/salvar")
    public String salvarClube(@ModelAttribute Clube clube) {
        clubeRepository.save(clube);
        return "redirect:/admin";
    }

    @GetMapping("/clube/editar/{id}")
    public String formEditarClube(@PathVariable Long id, Model model) {
        Clube clube = clubeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Clube não encontrado"));
        model.addAttribute("clube", clube);
        return "formClube";
    }

    @GetMapping("/clube/deletar/{id}")
    public String deletarClube(@PathVariable Long id) {
        clubeRepository.deleteById(id);
        return "redirect:/admin";
    }

    // ── Alunos ──
    @GetMapping("/alunos")
    public String listarAlunos(Model model) {
        model.addAttribute("alunos", alunoRepository.findAll());
        return "listaAlunos";
    }

    @GetMapping("/aluno/deletar/{id}")
    public String deletarAluno(@PathVariable Long id) {
        alunoRepository.deleteById(id);
        return "redirect:/admin/alunos";
    }
}