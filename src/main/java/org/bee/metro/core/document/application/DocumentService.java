package org.bee.metro.core.document.application;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.bee.metro.core.document.domain.Document;
import org.bee.metro.core.document.domain.DocumentRepository;
import org.bee.metro.core.document.dto.DocumentTreeNode;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DocumentService {

    private final DocumentRepository documentRepository;

    public List<DocumentTreeNode> getDocumentsByOwnerId(UUID ownerId) {
        List<Document> documents = documentRepository.findByOwnerId(ownerId);
        Map<UUID, List<Document>> documentsByParentId = documents.stream()
                .collect(Collectors.groupingBy(Document::getParentId));

        List<DocumentTreeNode> rootNodes = getDocumentRootNodes(documentsByParentId);
        buildDocumentTree(rootNodes, documentsByParentId);
        return rootNodes;
    }

    private static List<DocumentTreeNode> getDocumentRootNodes(Map<UUID, List<Document>> documentsByParentId) {
        List<DocumentTreeNode> rootNodes = new ArrayList<>();
        List<Document> rootDocuments = documentsByParentId.getOrDefault(Document.ROOT_DOCUMENT_PARENT_ID,
                Collections.emptyList());

        for (Document document : rootDocuments) {
            rootNodes.add(DocumentTreeNode.createByDocument(document));
        }
        return rootNodes;
    }

    private static void buildDocumentTree(List<DocumentTreeNode> nodes, Map<UUID, List<Document>> documentsByParentId) {
        Queue<DocumentTreeNode> queue = new LinkedList<>(nodes);

        while (!queue.isEmpty()) {
            DocumentTreeNode currentNode = queue.poll();
            List<Document> childDocuments = documentsByParentId.getOrDefault(currentNode.id(), Collections.emptyList());

            for (Document childDocument : childDocuments) {
                DocumentTreeNode childNode = DocumentTreeNode.createByDocument(childDocument);
                currentNode.children().add(childNode);
                queue.add(childNode);
            }
        }
    }

    public Document createDocument(UUID ownerId, UUID parentId) {
        Document document = Document.builder()
                .parentId(parentId)
                .ownerId(ownerId)
                .build();
        return documentRepository.save(document);
    }

    public void deleteDocument(UUID id) {
        documentRepository.deleteById(id);
    }
}
