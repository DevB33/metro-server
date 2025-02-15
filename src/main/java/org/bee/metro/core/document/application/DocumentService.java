package org.bee.metro.core.document.application;

import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Stack;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.bee.metro.core.block.application.BlockService;
import org.bee.metro.core.block.domain.Block;
import org.bee.metro.core.document.common.DocumentFieldType;
import org.bee.metro.core.document.domain.Document;
import org.bee.metro.core.document.domain.DocumentRepository;
import org.bee.metro.core.document.dto.DetailDocumentPayload;
import org.bee.metro.core.document.dto.DocumentTreeNode;
import org.bee.metro.core.document.exception.DocumentErrorCode;
import org.bee.metro.global.exception.type.BadRequestException;
import org.bee.metro.global.exception.type.NotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DocumentService {

    private final DocumentRepository documentRepository;
    private final BlockService blockService;

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

    public Document getDocument(UUID id) {
        return documentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("해당 문서가 존재하지 않습니다.", DocumentErrorCode.NOT_FOUND));
    }

    @Transactional
    public void deleteDocument(UUID ownerId, UUID id) {
        Document document = getDocument(id);
        if (document.isNotOwner(ownerId)) {
            throw new BadRequestException("해당 문서의 삭제 권한이 없습니다.", DocumentErrorCode.UNAUTHORIZED);
        }
        deleteDocumentTree(document);
    }

    private void deleteDocumentTree(Document document) {
        Stack<Document> stack = new Stack<>();
        stack.push(document);

        while (!stack.isEmpty()) {
            Document currentDocument = stack.pop();
            List<Document> childDocuments = documentRepository.findByParentId(currentDocument.getId());

            for (Document childDocument : childDocuments) {
                stack.push(childDocument);
            }
            documentRepository.deleteById(currentDocument.getId());
        }
    }

    public DetailDocumentPayload findDocumentById(UUID memberId, UUID id) {
        Document document = getDocument(id);
        if (document.isNotOwner(memberId)) {
            throw new BadRequestException("해당 문서의 조회 권한이 없습니다.", DocumentErrorCode.UNAUTHORIZED);
        }

        List<Block> blocksInDocument = blockService.findByDocumentId(document.getId());
        return DetailDocumentPayload.createByDocumentAndBlocks(document, blocksInDocument);
    }

    @Transactional
    public void updateDocument(UUID memberId, UUID documentId, DocumentFieldType documentFieldType, String value) {
        Document document = getDocument(documentId);
        if (document.isNotOwner(memberId)) {
            throw new BadRequestException("해당 문서의 수정 권한이 없습니다.", DocumentErrorCode.UNAUTHORIZED);
        }


    }
}
