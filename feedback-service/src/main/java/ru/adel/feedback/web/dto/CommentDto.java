package ru.adel.feedback.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {
    private String parentId;
    private String text;
    private Long cryptoId;
    private String userId;
}
