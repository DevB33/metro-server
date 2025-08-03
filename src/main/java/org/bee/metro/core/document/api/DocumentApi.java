package org.bee.metro.core.document.api;

import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.bee.metro.core.document.application.DocumentService;
import org.bee.metro.core.document.common.DocumentFieldType;
import org.bee.metro.core.document.domain.Document;
import org.bee.metro.core.document.dto.DetailDocumentPayload;
import org.bee.metro.core.document.dto.DocumentCreationAtListRequest;
import org.bee.metro.core.document.dto.DocumentCreationRequest;
import org.bee.metro.core.document.dto.DocumentCreationResponse;
import org.bee.metro.core.document.dto.DocumentTagsUpdateRequest;
import org.bee.metro.core.document.dto.DocumentTreeNode;
import org.bee.metro.core.document.dto.DocumentTreeNodeResponse;
import org.bee.metro.core.document.dto.DocumentUpdateRequest;
import org.bee.metro.global.auth.annotation.Login;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/documents")
@RequiredArgsConstructor
public class DocumentApi {

    private final DocumentService documentService;

    @GetMapping
    public ResponseEntity<DocumentTreeNodeResponse> getDocuments(@Login UUID memberId) {
        List<DocumentTreeNode> documentsByOwnerId = documentService.getDocumentsByOwnerId(memberId);
        return ResponseEntity.ok(new DocumentTreeNodeResponse(documentsByOwnerId));
    }

    @PostMapping
    public ResponseEntity<DocumentCreationResponse> createDocument(
            @Login UUID memberId,
            @RequestBody DocumentCreationRequest documentCreationRequest
    ) {
        Document document = documentService.createDocument(memberId, documentCreationRequest.parentId(),
                documentCreationRequest.upperOrder() + 1);
        return ResponseEntity.ok(new DocumentCreationResponse(document.getId()));
    }

    @PostMapping("/list")
    public ResponseEntity<DocumentCreationResponse> createDocumentAtList(
            @Login UUID memberId,
            @RequestBody DocumentCreationAtListRequest documentCreationRequest
    ) {
        Document document = documentService.createDocumentAtList(memberId, documentCreationRequest.parentId());
        return ResponseEntity.ok(new DocumentCreationResponse(document.getId()));
    }

    @DeleteMapping("/{documentId}")
    public ResponseEntity<Void> deleteDocument(
            @Login UUID memberId,
            @PathVariable(value = "documentId") UUID documentId
    ) {
        documentService.deleteDocument(memberId, documentId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{documentId}")
    public ResponseEntity<DetailDocumentPayload> getDocument(
            @Login UUID memberId,
            @PathVariable(value = "documentId") UUID documentId
    ) {
        DetailDocumentPayload document = documentService.findDocumentById(memberId, documentId);
        return ResponseEntity.ok(document);
    }

    @PatchMapping("/{documentId}/tags")
    public ResponseEntity<Void> updateDocumentTag(
            @Login UUID memberId,
            @PathVariable(value = "documentId") UUID documentId,
            @RequestBody DocumentTagsUpdateRequest documentTagsUpdateRequest
    ) {
        documentService.updateDocumentTags(memberId, documentId, documentTagsUpdateRequest.tags());
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{documentId}/{fieldType}")
    public ResponseEntity<Void> updateDocument(
            @Login UUID memberId,
            @PathVariable(value = "documentId") UUID documentId,
            @PathVariable(value = "fieldType") String fieldType,
            @RequestBody DocumentUpdateRequest documentUpdateRequest
    ) {
        documentService.updateDocument(
                memberId, documentId,
                DocumentFieldType.valueOf(fieldType.toUpperCase()),
                documentUpdateRequest.value()
        );
        return ResponseEntity.ok().build();
    }
}
