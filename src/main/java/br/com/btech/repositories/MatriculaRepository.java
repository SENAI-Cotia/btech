package br.com.btech.repositories;

import br.com.btech.models.Matricula;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface MatriculaRepository extends JpaRepository<Matricula, Long> {

    @Query("SELECT m FROM Matricula m JOIN FETCH m.clube WHERE m.aluno.id = :alunoId")
    List<Matricula> findByAlunoId(@Param("alunoId") Long alunoId);
}