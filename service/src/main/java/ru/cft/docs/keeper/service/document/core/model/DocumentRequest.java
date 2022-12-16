package ru.cft.docs.keeper.service.document.core.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DocumentRequest {

    String title;

    DataField[] data;

    String type;

    @Override
    public String toString() {
        StringBuilder field = new StringBuilder("[");
        for (DataField currentDocument : this.data) {
            String currentDocumentString =
                    "{\"type\":\"" + currentDocument.getType() + "\","
                    + "\"name\":\"" + currentDocument.getName() + "\","
                    + "\"title\":\"" + currentDocument.getTitle() + "\","
                    + "\"content\":\"" + currentDocument.getContent() + "\"},";
            field.append(currentDocumentString);
        }
        field.deleteCharAt(field.length() - 1).append("]");
        return field.toString();
    }
}
