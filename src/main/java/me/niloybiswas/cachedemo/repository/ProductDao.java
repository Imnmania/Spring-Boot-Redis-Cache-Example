package me.niloybiswas.cachedemo.repository;

import me.niloybiswas.cachedemo.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProductDao {

    public static final String HASH_KEY = "Product";
    @Autowired
    private RedisTemplate template;

    public Product saveToRedis(Product product) {
        template.opsForHash().put(HASH_KEY, product.getId(), product);
        return product;
    }

    public List<Product> findAllFromRedis() {
        System.out.println("Called from db...");
        return (List<Product>) template.opsForHash().values(HASH_KEY);
    }

    public Product findByIdFromRedis(int id) {
        System.out.println("Called from db...");
        return (Product) template.opsForHash().get(HASH_KEY, id);
    }

    public String deleteFromRedis(int id) {
        template.opsForHash().delete(HASH_KEY, id);
        return "Product Deleted!";
    }
}
