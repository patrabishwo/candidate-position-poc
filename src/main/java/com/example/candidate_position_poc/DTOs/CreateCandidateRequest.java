package com.example.candidate_position_poc.DTOs;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateCandidateRequest {
    @NotBlank(message = "Name  is  required")
    @Size(max=50, message = "Name  must not  exceed  50  characters")
    private String name;
    @NotBlank(message = "Email is  required")
    @Email(message = "Invalid email  format")
    private  String email;;
    @NotNull(message = "DOB is required")
    private LocalDate dob;
    @NotEmpty(message = "At  least  one position  id  is required")
    private List<Long> positionIds;

}
