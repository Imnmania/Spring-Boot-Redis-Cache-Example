package me.niloybiswas.cachedemo.controller;

import lombok.AllArgsConstructor;
import me.niloybiswas.cachedemo.entity.Product;
import me.niloybiswas.cachedemo.repository.ProductDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/product")
public class ProductController {

    @Autowired
    private final ProductDao productDao;

    @PostMapping("/save")
//    @CacheEvict(value = "Product", key = "#product.getId()")
    @Caching(evict = {
            @CacheEvict(value = "Products", allEntries = true),
            @CacheEvict(value = "Product", key = "#product.getId()")
    })
    public Product save(@RequestBody Product product) {
        return productDao.saveToRedis(product);
    }

    @GetMapping("/getAll")
    @Cacheable(value = "Products")
    public List<Product> getAll() {
        return productDao.findAllFromRedis();
    }

    @GetMapping("/{id}")
//    @Cacheable(key = "#id", value = "Product", unless = "#result.price > 100")
    @Cacheable(key = "#id", value = "Product")
    public Product findById(@PathVariable int id) {
        return productDao.findByIdFromRedis(id);
    }

    @DeleteMapping("/{id}")
//    @CacheEvict(key = "#id", value = "Product")
    @Caching(evict = {
            @CacheEvict(value = "Products", allEntries = true),
            @CacheEvict(value = "Product", key = "#id")
    })
    public String deleteById(@PathVariable int id) {
        return productDao.deleteFromRedis(id);
    }
}
