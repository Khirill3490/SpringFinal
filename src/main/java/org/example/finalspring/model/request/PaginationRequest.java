package org.example.finalspring.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaginationRequest {

    private Integer pageSize = 5;
    private Integer pageNumber = 0;
}

