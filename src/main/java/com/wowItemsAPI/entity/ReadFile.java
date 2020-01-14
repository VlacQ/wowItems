package com.wowItemsAPI.entity;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ReadFile {
    MultipartFile file;
    Boolean isDates;
}
