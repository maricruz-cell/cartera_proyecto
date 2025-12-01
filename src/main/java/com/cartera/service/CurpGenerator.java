package com.cartera.service;

import com.cartera.model.RegistroDto;
import org.springframework.stereotype.Service;

import java.text.Normalizer;
import java.time.LocalDate;

@Service
public class CurpGenerator {

    public String generarCurp(RegistroDto dto) {

        // 1. Limpiar y normalizar nombres y apellidos
        String nombreLimpio = limpiarNombre(dto.getNombre());
        String nombre = normalizar(nombreLimpio);
        String ap = normalizar(nullToEmpty(dto.getApellidoPaterno()));
        String am = normalizar(nullToEmpty(dto.getApellidoMaterno()));

        LocalDate fn = LocalDate.parse(dto.getFechaNacimiento());

        // Sexo: H / M
        char sexo = 'X';
        if (dto.getSexo() != null && !dto.getSexo().isBlank()) {
            sexo = Character.toUpperCase(dto.getSexo().charAt(0)); // H / M
        }

        // Estado: a partir del id_estado seleccionado en el combo
        String estado = obtenerClaveEstado(dto.getIdEstado());        // Integer -> código CURP (MC, DF, etc.)

        StringBuilder curp = new StringBuilder(18);

        // 2. Apellido paterno
        curp.append(getOrX(ap, 0));
        curp.append(primeraVocalInterna(ap));

        // 3. Apellido materno (si no hay, X)
        curp.append(getOrX(am, 0));

        // 4. Nombre
        curp.append(getOrX(nombre, 0));

        // 5. Fecha de nacimiento (AAMMDD)
        curp.append(String.format("%02d", fn.getYear() % 100));
        curp.append(String.format("%02d", fn.getMonthValue()));
        curp.append(String.format("%02d", fn.getDayOfMonth()));

        // 6. Sexo
        curp.append(sexo);

        // 7. Estado
        curp.append(estado);

        // 8. Primeras consonantes internas
        curp.append(primeraConsonanteInterna(ap));
        curp.append(primeraConsonanteInterna(am));
        curp.append(primeraConsonanteInterna(nombre));

        // 9. Homoclave (simplificada; si luego quieres ponemos la oficial)
        curp.append("01");

        return curp.toString();
    }

    // ========================
    //   FUNCIONES DE APOYO
    // ========================

    // Quita JOSE / MARIA como primer nombre
    private String limpiarNombre(String nombreOriginal) {
        if (nombreOriginal == null) return "";
        String n = nombreOriginal.trim().toUpperCase();
        if (n.isEmpty()) return "";

        String[] partes = n.split("\\s+");
        if (partes.length > 1) {
            String p0 = partes[0];
            if (p0.equals("JOSE") || p0.equals("J") || p0.equals("J.")
                    || p0.equals("MARIA") || p0.equals("MA") || p0.equals("MA.")) {
                // Usar el siguiente nombre
                StringBuilder sb = new StringBuilder();
                for (int i = 1; i < partes.length; i++) {
                    if (i > 1) sb.append(" ");
                    sb.append(partes[i]);
                }
                return sb.toString();
            }
        }
        return n;
    }

    // Normaliza: mayúsculas, sin acentos, Ñ -> X
    private static String normalizar(String s) {
        String upper = s.toUpperCase();
        String sinAcentos = Normalizer.normalize(upper, Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "");
        sinAcentos = sinAcentos.replace("Ñ", "X");
        return sinAcentos;
    }

    private static String nullToEmpty(String s) {
        return (s == null) ? "" : s;
    }

    private static char getOrX(String s, int index) {
        if (s == null || s.isEmpty() || index >= s.length()) return 'X';
        char c = s.charAt(index);
        if (!Character.isLetter(c)) return 'X';
        return c;
    }

    private static char primeraVocalInterna(String s) {
        if (s == null) return 'X';
        for (int i = 1; i < s.length(); i++) {
            char c = s.charAt(i);
            if ("AEIOU".indexOf(c) >= 0) return c;
        }
        return 'X';
    }

    private static char primeraConsonanteInterna(String s) {
        if (s == null) return 'X';
        for (int i = 1; i < s.length(); i++) {
            char c = s.charAt(i);
            if (!Character.isLetter(c)) continue;
            if ("AEIOU".indexOf(c) == -1) return c; // No es vocal -> consonante
        }
        return 'X';
    }

    // id_estado (1–32) -> código CURP (AS, BC, DF, MC, etc.)
    private String obtenerClaveEstado(Integer idEstado) {
        if (idEstado == null) return "NE";
        return switch (idEstado) {
            case 1 -> "AS"; // Aguascalientes
            case 2 -> "BC"; // Baja California
            case 3 -> "BS"; // Baja California Sur
            case 4 -> "CC"; // Campeche
            case 5 -> "CL"; // Coahuila
            case 6 -> "CM"; // Colima
            case 7 -> "CS"; // Chiapas
            case 8 -> "CH"; // Chihuahua
            case 9 -> "DF"; // Ciudad de México
            case 10 -> "DG"; // Durango
            case 11 -> "GT"; // Guanajuato
            case 12 -> "GR"; // Guerrero
            case 13 -> "HG"; // Hidalgo
            case 14 -> "JC"; // Jalisco
            case 15 -> "MC"; // Estado de México
            case 16 -> "MN"; // Michoacán
            case 17 -> "MS"; // Morelos
            case 18 -> "NT"; // Nayarit
            case 19 -> "NL"; // Nuevo León
            case 20 -> "OC"; // Oaxaca
            case 21 -> "PL"; // Puebla
            case 22 -> "QT"; // Querétaro
            case 23 -> "QR"; // Quintana Roo
            case 24 -> "SP"; // San Luis Potosí
            case 25 -> "SL"; // Sinaloa
            case 26 -> "SR"; // Sonora
            case 27 -> "TC"; // Tabasco
            case 28 -> "TS"; // Tamaulipas
            case 29 -> "TL"; // Tlaxcala
            case 30 -> "VZ"; // Veracruz
            case 31 -> "YN"; // Yucatán
            case 32 -> "ZS"; // Zacatecas
            default -> "NE";
        };
    }
}
