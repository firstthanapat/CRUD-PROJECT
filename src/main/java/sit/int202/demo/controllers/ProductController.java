package sit.int202.demo.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import sit.int202.demo.entities.Product;
import sit.int202.demo.services.ProductService;

import java.io.IOException;

@Controller
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }


    // get product list
    @GetMapping("/all")
    public String allProducts(ModelMap modelMap) {
        modelMap.addAttribute("products", productService.getAllProducts());
        return "product_list";
    }

    // pop message and return to product list page after delete
    @GetMapping("/delete-product")
    public String deleteProduct(ModelMap modelMap, @RequestParam String productCode, RedirectAttributes redirectAttributes) {
        Product oldproduct = productService.deleteProduct(productCode);
        redirectAttributes.addFlashAttribute("deleteMessage", "Product deleted successfully!!");
        modelMap.addAttribute("product", oldproduct);
        return "redirect:/products/all";
    }

    // get product_from
    @GetMapping("/add-product")
    public String addProduct() {
        return "product_form";
    }

    // add new product
    @PostMapping("/add-product")
    public String addProduct(Product product, ModelMap modelMap, RedirectAttributes redirectAttributes) {
        //if product cannot added,return to product-from page and pop error
        if (productService.isProductExists(product.getProductCode())) {
            redirectAttributes.addFlashAttribute("DuplicateProductCodeMessage", "Productcode already exists");
            return "redirect:/products/product-form";

        }
        //If the product can be added, return to the product list page.
        Product product1 = productService.addProduct(product);
        modelMap.addAttribute("product", product1);
        redirectAttributes.addFlashAttribute("SuccessMessage", "Product added successfully!!");
        return "redirect:/products/all";
    }

    //get product from
    @GetMapping("/product-form")
    public String getProductForm() {
        return "product_form";
    }

    //get update from
    @GetMapping("/update-form")
    public String getProductForm(@RequestParam String productCode, ModelMap modelMap) {
        Product productform = productService.getProductByCode(productCode);
        modelMap.addAttribute("product", productform);
        return "update_form";
    }

    //return product list after update productfrom
    @PostMapping("/update-product")
    public String updateProduct(Product product, ModelMap modelMap, RedirectAttributes redirectAttributes) {
        Product product1 = productService.updateProduct(product);
        redirectAttributes.addFlashAttribute("updateMessage", "Product updated successfully!!");
        modelMap.addAttribute("product", product1);
        return "redirect:/products/all";
    }

}
