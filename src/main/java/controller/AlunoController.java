package controller;

import models.Aluno;
import models.Matricula;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import repositories.AlunoRepository;
import repositories.ClubeRepository;
import repositories.MatriculaRepository;
import repositories.UserRepository;

import java.time.LocalDate;

@Controller
@RequestMapping("/aluno")
public class AlunoController {

    @Autowired
    private AlunoRepository alunoRepository;

    @Autowired
    private ClubeRepository clubeRepository;

    @Autowired
    private MatriculaRepository matriculaRepository;

    @Autowired
    private UserRepository userRepository;

    // ── Painel aluno ──
    @GetMapping
    public String painel(Authentication authentication, Model model) {
        String email = authentication.getName();
        userRepository.findByEmail(email).ifPresent(user -> {
            alunoRepository.findByUserId(user.getId()).ifPresent(aluno -> {
                model.addAttribute("aluno", aluno);
                model.addAttribute("matriculas", matriculaRepository.findByAlunoId(aluno.getId()));
            });
        });
        model.addAttribute("clubes", clubeRepository.findAll());
        return "alunoHome";
    }

    // ── Matricular em clube ──
    @PostMapping("/matricular/{clubeId}")
    public String matricular(@PathVariable Long clubeId, Authentication authentication) {
        String email = authentication.getName();
        userRepository.findByEmail(email).ifPresent(user -> {
            alunoRepository.findByUserId(user.getId()).ifPresent(aluno -> {
                clubeRepository.findById(clubeId).ifPresent(clube -> {
                    Matricula matricula = new Matricula();
                    matricula.setAluno(aluno);
                    matricula.setClube(clube);
                    matricula.setDataIngresso(LocalDate.now());
                    matriculaRepository.save(matricula);
                });
            });
        });
        return "redirect:/aluno";
    }

    // ── Cancelar matrícula ──
    @GetMapping("/cancelar/{matriculaId}")
    public String cancelar(@PathVariable Long matriculaId) {
        matriculaRepository.deleteById(matriculaId);
        return "redirect:/aluno";
    }
}