package com.example.letprakt.controller;

import com.example.letprakt.model.MediaFile;
import com.example.letprakt.repository.MediaFileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/media")
public class MediaController {

    @Autowired
    private MediaFileRepository mediaFileRepository;

    @GetMapping
    public String showMediaPage(Model model) {
        List<MediaFile> mediaFiles = mediaFileRepository.findAll();
        model.addAttribute("mediaFiles", mediaFiles);
        return "media";
    }

    @PostMapping("/upload")
    public String uploadMediaFile(@RequestParam("file") MultipartFile file, Model model) {
        if (file.isEmpty()) {
            model.addAttribute("error", "No file selected to upload.");
            return "media";
        }
        try {
            MediaFile mediaFile = new MediaFile();
            mediaFile.setFileName(file.getOriginalFilename());
            mediaFile.setData(file.getBytes());
            mediaFile.setFileType(file.getContentType());
            mediaFileRepository.save(mediaFile);
        } catch (IOException e) {
            model.addAttribute("error", "Failed to upload file: " + e.getMessage());
            e.printStackTrace();
            return "media";
        }
        return "redirect:/media";
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<byte[]> downloadMediaFile(@PathVariable Long id) {
        Optional<MediaFile> mediaFileOptional = mediaFileRepository.findById(id);
        if (mediaFileOptional.isPresent()) {
            MediaFile mediaFile = mediaFileOptional.get();
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + mediaFile.getFileName() + "\"")
                    .contentType(MediaType.parseMediaType(mediaFile.getFileType()))
                    .body(mediaFile.getData());
        } else {
            return ResponseEntity.status(404).body(null);
        }
    }
}
