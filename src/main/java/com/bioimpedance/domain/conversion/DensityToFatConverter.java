package com.bioimpedance.domain.conversion;

import com.bioimpedance.domain.calculation.PredictionResult;
import com.bioimpedance.library.conversions.ConversionDefinition;
import org.springframework.stereotype.Service;

/**
 * Fonte: architecture.md §14.
 * Converte BODY_DENSITY em BODY_FAT_PERCENTAGE usando Siri ou Brozek.
 */
@Service
public class DensityToFatConverter {

    public PredictionResult convert(ConversionDefinition conversion, double bodyDensity) {
        if (!"BODY_DENSITY".equals(conversion.inputType())) {
            throw new IllegalArgumentException("Conversor espera BODY_DENSITY, recebeu: " + conversion.inputType());
        }

        double fatPercentage;
        if ("siri".equals(conversion.id())) {
            fatPercentage = (495.0 / bodyDensity) - 450.0;
        } else if ("brozek".equals(conversion.id())) {
            fatPercentage = (457.0 / bodyDensity) - 414.2;
        } else {
            throw new IllegalArgumentException("Conversão desconhecida: " + conversion.id());
        }

        return new PredictionResult(conversion.id(), conversion.outputType(), fatPercentage);
    }
}