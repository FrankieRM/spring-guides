package com.example.uploadingfiles;

import com.example.uploadingfiles.storage.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

import static java.lang.String.format;
import static java.util.stream.Collectors.toList;
import static org.springframework.http.HttpHeaders.CONTENT_DISPOSITION;

@Controller
@RequestMapping("/")
public class FileUploadController {

    private static final String CONTENT_DISPOSITION_FILENAME = "attachment; filename=\"%s\"";
    private static final String FILE_UPLOAD_MESSAGE = "You successfully uploaded %s!";
    private final StorageService storageService;

    @Autowired
    public FileUploadController(StorageService storageService) {
        this.storageService = storageService;
    }

    @GetMapping
    public String listUploadedFiles(Model model) {
        List<String> files = storageService.loadAll()
                .map(path -> MvcUriComponentsBuilder.fromMethodName(
                        FileUploadController.class,
                        "serveFile",
                        path.getFileName().toString())
                        .build().toUri().toString())
                .collect(toList());

        model.addAttribute("files", files);
        return "uploadForm";
    }

    @GetMapping("files/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {
        Resource file = storageService.loadAsResource(filename);
        return ResponseEntity.ok()
                .header(CONTENT_DISPOSITION,
                        format(CONTENT_DISPOSITION_FILENAME, file.getFilename()))
                .body(file);
    }

    @PostMapping
    public String handleFileUpload(@RequestParam MultipartFile file,
                                   RedirectAttributes redirectAttributes) {
        storageService.store(file);
        redirectAttributes.addFlashAttribute("message",
                format(FILE_UPLOAD_MESSAGE, file.getOriginalFilename()));

        return "redirect:/";
    }
}