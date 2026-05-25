package br.com.btech.repositories;

import br.com.btech.models.Aluno;
import br.com.btech.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface AlunoRepository extends JpaRepository<Aluno, Long> {
    Optional<Aluno> findByUser_Id(Long userId);
}