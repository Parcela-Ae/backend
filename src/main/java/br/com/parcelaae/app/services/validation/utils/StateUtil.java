package br.com.parcelaae.app.services.validation.utils;

import lombok.experimental.UtilityClass;

import java.util.Map;

import static java.util.Map.entry;

@UtilityClass
public class StateUtil {

    Map<String, Integer> stateMap = Map.ofEntries(
            entry("RO", 11),
            entry("AC", 12),
            entry("AM", 13),
            entry("RR", 14),
            entry("PA", 15),
            entry("AP", 16),
            entry("TO", 17),
            entry("MA", 21),
            entry("PI", 22),
            entry("CE", 23),
            entry("RN", 24),
            entry("PB", 25),
            entry("PE", 26),
            entry("AL", 27),
            entry("SE", 28),
            entry("BA", 29),
            entry("MG", 31),
            entry("ES", 32),
            entry("RJ", 33),
            entry("SP", 35),
            entry("PR", 41),
            entry("SC", 42),
            entry("RS", 43),
            entry("MS", 50),
            entry("MT", 51),
            entry("GO", 52),
            entry("DF", 53)
    );

    public static Integer getStateIdByUf(String uf) {
        return stateMap.get(uf);
    }
}
