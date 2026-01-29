package appcrm.backend.model.DTOs;

import java.math.BigDecimal;

public record LeadDto(String description, BigDecimal initPrice, String email) {
}
