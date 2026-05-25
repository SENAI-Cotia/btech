package br.com.btech.controller;

import br.com.btech.models.Clube;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import br.com.btech.repositories.AlunoRepository;
import br.com.btech.repositories.ClubeRepository;
import br.com.btech.repositories.MatriculaRepository;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

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
        model.addAttribute("novoClube", new Clube());
        return "admHome";
    }

    // ── Clubes ──
    @GetMapping("/clube/novo")
    public String formNovoClube(Model model) {
        model.addAttribute("clube", new Clube());
        return "formClube";
    }

    @PostMapping("/clube/salvar")
    public String salvarClube(@ModelAttribute Clube clube,
                              @RequestParam("imagemFile") MultipartFile imagemFile) throws IOException {

        if (!imagemFile.isEmpty()) {
            String nomeArquivo = imagemFile.getOriginalFilename();
            String caminho = "src/main/resources/static/image/" + nomeArquivo;
            Files.write(Paths.get(caminho), imagemFile.getBytes());
            clube.setImagem(nomeArquivo);
        }

        clubeRepository.save(clube);
        return "redirect:/admin";
    }

    @PostMapping("/clube/editar/{id}")
    public String editarClube(@PathVariable Long id,
                              @ModelAttribute Clube clube,
                              @RequestParam("imagemFile") MultipartFile imagemFile) throws IOException {

        Clube existente = clubeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Clube não encontrado"));

        existente.setNome(clube.getNome());
        existente.setDescricao(clube.getDescricao());
        existente.setDiaSemana(clube.getDiaSemana());
        existente.setHorario(clube.getHorario());

        if (!imagemFile.isEmpty()) {
            String nomeArquivo = imagemFile.getOriginalFilename();
            String caminho = "src/main/resources/static/image/" + nomeArquivo;
            Files.write(Paths.get(caminho), imagemFile.getBytes());
            existente.setImagem(nomeArquivo);
        }

        clubeRepository.save(existente);
        return "redirect:/admin";
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