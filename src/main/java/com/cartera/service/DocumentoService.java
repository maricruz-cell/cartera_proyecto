package com.cartera.service;

import com.cartera.model.DtDocumentosAspirante;
import com.cartera.repository.DtDocumentoAspiranteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.*;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class DocumentoService {

    private final DtDocumentoAspiranteRepository repo;

    private final String UPLOAD_DIR = "uploads/aspirantes/";

    public void guardarDocumento(Long idPersona, Long idTipo, MultipartFile archivo) throws Exception {

        // Crear carpeta si no existe
        Path uploadPath = Paths.get(UPLOAD_DIR + idPersona + "/");
        Files.createDirectories(uploadPath);

        // Obtener nombre original
        String nombre = archivo.getOriginalFilename();

        // Crear ruta completa
        Path rutaArchivo = uploadPath.resolve(nombre);

        // Guardar archivo físicamente
        Files.copy(archivo.getInputStream(), rutaArchivo, StandardCopyOption.REPLACE_EXISTING);

        // Guardar en BD
        DtDocumentosAspirante doc = new DtDocumentosAspirante();
        doc.setIdPersona(idPersona);
        doc.setIdTipoDocumento(idTipo);
        doc.setNombreArchivo(nombre);
        doc.setRutaArchivo(rutaArchivo.toString());
        doc.setFechaSubida(LocalDateTime.now());

        repo.save(doc);
    }
}
