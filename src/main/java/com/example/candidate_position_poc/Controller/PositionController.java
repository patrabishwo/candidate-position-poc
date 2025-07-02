package com.example.candidate_position_poc.Controller;

import com.example.candidate_position_poc.DTOs.CreatePositionRequest;
import com.example.candidate_position_poc.Entity.Position;
import com.example.candidate_position_poc.Service.PositionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/positions")
@RequiredArgsConstructor
public class PositionController {

    private final PositionService positionService;
    @PostMapping
    public ResponseEntity<Position> createPosition(@Valid @RequestBody CreatePositionRequest request) {
        Position savedPosition = positionService.createPosition(request);
        return new ResponseEntity<>(savedPosition, HttpStatus.CREATED);
    }


    @GetMapping
    public ResponseEntity<List<Position>> getAllPositions() {
        return ResponseEntity.ok(positionService.getAllPosition());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Position> getPositionById(@PathVariable Long id) {
        Position position = positionService.getPositionById(id);
        return ResponseEntity.ok(position);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePositionById(@PathVariable Long id) {
        positionService.deletePositionById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
