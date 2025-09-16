package sit.int202.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import sit.int202.demo.entities.Product;
import sit.int202.demo.repositories.ProductRepository;

import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    private Product getProduct(String productCode) {
        return productRepository.findById(productCode).orElse(null);
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product addProduct(Product product) {
        if (productRepository.existsById(product.getProductCode())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Product already exists");
        }
        productRepository.save(product);
        return product;
    }

    public Product updateProduct(Product product) {
        Product oldProduct = getProduct(product.getProductCode());
        if (oldProduct == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    String.format("Product %s does not exist", product.getProductCode()));
        }
        return productRepository.save(product);
    }

    public Product deleteProduct(String productCode) {
        Product oldProduct = getProduct(productCode);
        if (oldProduct == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    String.format("Product %s does not exist", productCode));
        }
        productRepository.delete(oldProduct);
        return oldProduct;
    }

    public Product getProductByCode(String productCode) {
        return getProduct(productCode);
    }

    public boolean isProductExists(String productCode) {
        return productRepository.existsById(productCode);
    }
}
