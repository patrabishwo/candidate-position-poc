package com.example.candidate_position_poc.Repositories;
import com.example.candidate_position_poc.Entity.Position;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PositionRepository extends JpaRepository<Position, Long> {
    boolean existsByPositionName(String name);


}


