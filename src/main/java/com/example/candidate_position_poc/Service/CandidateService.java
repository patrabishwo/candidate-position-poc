package com.example.candidate_position_poc.Service;

import com.example.candidate_position_poc.DTOs.CreateCandidateRequest;
import com.example.candidate_position_poc.DTOs.UpdateCandidateRequest;
import com.example.candidate_position_poc.Entity.Candidate;
import com.example.candidate_position_poc.Entity.Position;
import com.example.candidate_position_poc.Repositories.CandidateRepository;
import com.example.candidate_position_poc.Repositories.PositionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class CandidateService {


    private  final CandidateRepository candidateRepository;

    private  final PositionRepository positionRepository;

    public Candidate createCandidate(CreateCandidateRequest request) {
        if (candidateRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }

        Period age = Period.between(request.getDob(), LocalDate.now());
        if (age.getYears() < 18) {
            throw new IllegalArgumentException("Candidate must be at least 18 years old");
        }

        List<Position> positions = positionRepository.findAllById(request.getPositionIds());
        if (positions.size() != request.getPositionIds().size()) {
            throw new IllegalArgumentException("One or more Position IDs are invalid");
        }

        Candidate candidate = new Candidate();
        candidate.setName(request.getName());
        candidate.setEmail(request.getEmail());
        candidate.setDob(request.getDob());
        candidate.setPositions(positions);

        return candidateRepository.save(candidate);
    }

    public Candidate updateCandidate(Long id, UpdateCandidateRequest request) {
        Candidate candidate = candidateRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Candidate not found"));

        if (request.getName() != null) {
            candidate.setName(request.getName());
        }

        if (request.getEmail() != null) {
            if (!candidate.getEmail().equals(request.getEmail()) &&
                    candidateRepository.existsByEmail(request.getEmail())) {
                throw new IllegalArgumentException("Email already in use");
            }
            candidate.setEmail(request.getEmail());
        }

        if (request.getDob() != null) {
            Period age = Period.between(request.getDob(), LocalDate.now());
            if (age.getYears() < 18) {
                throw new IllegalArgumentException("Candidate must be at least 18 years old");
            }
            candidate.setDob(request.getDob());
        }

        if (request.getPositionIds() != null) {
            List<Position> positions = positionRepository.findAllById(request.getPositionIds());
            if (positions.size() != request.getPositionIds().size()) {
                throw new IllegalArgumentException("Invalid position IDs");
            }
            candidate.setPositions(positions);
        }

        return candidateRepository.save(candidate);
    }
    public List<Candidate> getAllCandidates() {
        return candidateRepository.findAll();
    }


    public Candidate getCandidateById(Long id) {
        return candidateRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Candidate not found"));
    }


    public void deleteCandidateById(Long id) {
        if (!candidateRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Candidate not found");
        }
        candidateRepository.deleteById(id);
    }
}

