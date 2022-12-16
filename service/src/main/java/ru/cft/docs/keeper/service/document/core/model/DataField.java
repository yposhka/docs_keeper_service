package ru.cft.docs.keeper.service.document.core.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DataField {

    String type;

    String name;

    String title;

    String content;
}
