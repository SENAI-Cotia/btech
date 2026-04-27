package br.com.btech.models;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "matriculas")
public class Matriculas {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "aluno_id")
    private Aluno aluno;

    @ManyToOne
    @JoinColumn(name = "clube_id")
    private Clube clube;

    private LocalDate dataIngresso;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Aluno getAluno() { return aluno; }
    public void setAluno(Aluno aluno) { this.aluno = aluno; }

    public Clube getClube() { return clube; }
    public void setClube(Clube clube) { this.clube = clube; }

    public LocalDate getDataIngresso() { return dataIngresso; }
    public void setDataIngresso(LocalDate dataIngresso) { this.dataIngresso = dataIngresso; }
}