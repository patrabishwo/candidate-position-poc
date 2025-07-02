package com.example.candidate_position_poc.Service;

import com.example.candidate_position_poc.DTOs.CreatePositionRequest;
import com.example.candidate_position_poc.Entity.Position;
import com.example.candidate_position_poc.Repositories.PositionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PositionService {

    private   final PositionRepository positionRepository;

    public Position createPosition(CreatePositionRequest request){
        if(positionRepository.existsByPositionName(request.getPositionName())){
            throw  new IllegalArgumentException("Position already exits");
        }

        Position position = new Position();
        position.setPositionName(request.getPositionName());

        return positionRepository.save(position);
    }

    public List<Position>  getAllPosition(){
        return positionRepository.findAll();
    }

    public Position getPositionById(Long id) {
        return positionRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Position not found"));
    }

    public void deletePositionById(Long id) {
        if (!positionRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Position not found");
        }
        positionRepository.deleteById(id);
    }

}
