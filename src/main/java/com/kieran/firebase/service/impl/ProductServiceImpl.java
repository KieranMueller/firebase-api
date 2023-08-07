package com.kieran.firebase.service.impl;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;
import com.kieran.firebase.entity.Product;
import com.kieran.firebase.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private static final String COLLECTION_NAME = "product";

    @Override
    public List<Product> getAllProducts() throws ExecutionException, InterruptedException {
        Firestore firestore = FirestoreClient.getFirestore();
        Iterable<DocumentReference> documentReference = firestore
                .collection(COLLECTION_NAME)
                .listDocuments();
        Iterator<DocumentReference> iterator = documentReference.iterator();
        List<Product> productList = new ArrayList<>();
        while (iterator.hasNext()) {
            DocumentReference documentReference1 = iterator.next();
            ApiFuture<DocumentSnapshot> future = documentReference1.get();
            DocumentSnapshot document = future.get();
            Product product = document.toObject(Product.class);
            productList.add(product);
        }
        return productList;
    }

    @Override
    public Product getProductDetails(String name) throws ExecutionException, InterruptedException {
        Firestore firestore = FirestoreClient.getFirestore();
        DocumentReference documentReference = firestore
                .collection(COLLECTION_NAME)
                .document(name);
        ApiFuture<DocumentSnapshot> future = documentReference.get();
        DocumentSnapshot document = future.get();
        if (document.exists())
            return document.toObject(Product.class);
        throw new RuntimeException();
    }

    @Override
    public String saveProduct(Product product) throws ExecutionException, InterruptedException {
        Firestore firestore = FirestoreClient.getFirestore();
        ApiFuture<WriteResult> collectionApiFuture = firestore
                .collection(COLLECTION_NAME)
                .document()
                .set(product);
        return collectionApiFuture.get().getUpdateTime().toString();
    }

    @Override
    public String updateProduct(Product product) throws ExecutionException, InterruptedException {
        Firestore firestore = FirestoreClient.getFirestore();
        ApiFuture<WriteResult> collectionApiFuture = firestore
                .collection(COLLECTION_NAME)
                .document(product.getName())
                .set(product);
        return collectionApiFuture.get().getUpdateTime().toString();
    }

    @Override
    public String deleteProduct(String name) throws ExecutionException, InterruptedException {
        Firestore firestore = FirestoreClient.getFirestore();
        ApiFuture<WriteResult> collectionApiFuture = firestore
                .collection(COLLECTION_NAME)
                .document(name).delete();
        return "%s has been deleted successfully".formatted(name);
    }

}
