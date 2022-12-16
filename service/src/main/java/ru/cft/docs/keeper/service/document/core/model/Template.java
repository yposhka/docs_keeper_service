package ru.cft.docs.keeper.service.document.core.model;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.Value;

@Value
@RequiredArgsConstructor
@Builder(builderClassName = "Builder", toBuilder = true)
public class Template {

    String name;

    TemplateElement[] structure;
}
