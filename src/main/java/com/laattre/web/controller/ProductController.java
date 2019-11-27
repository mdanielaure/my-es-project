package com.laattre.web.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.laattre.persistence.model.Category;
import com.laattre.persistence.model.Product;
import com.laattre.persistence.model.User;
import com.laattre.security.ISecurityUserService;
import com.laattre.service.CategoryService;
import com.laattre.service.ProductService;
import com.laattre.service.UserService;

@Controller
@RequestMapping("/product")
public class ProductController {

	@Autowired
	private ProductService productService;
	
	@Autowired
	private ISecurityUserService securityUserService; 
	
	@Autowired
	private UserService userService; 
	
	@Autowired
	private CategoryService categoryService;
	
	List<User> listUsers;
	List<Category> categories;
	List<Category> subCategories;

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String addProduct(Model model) {
		Product product = new Product();
		model.addAttribute("product", product);
		
		listUsers = userService.findAll();
		model.addAttribute("users", listUsers);
		
		categories = categoryService.findAll();
		model.addAttribute("categories", categories);
		
		return "addProduct";
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String addProductPost(@ModelAttribute("product") Product product, HttpServletRequest request) {
	    	
	    	final User createdBy = securityUserService.getCurrentUser();

	     	
	   	product.setCreateDate(new Date());
	   	product.setCreatedBy(createdBy);
		productService.save(product);

		MultipartFile productImage = product.getProductImage();

		try {
			byte[] bytes = productImage.getBytes();
			String name = product.getId() + ".png";
			BufferedOutputStream stream = new BufferedOutputStream(
					new FileOutputStream(new File("src/main/resources/static/image/product/" + name)));
			stream.write(bytes);
			stream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "redirect:productList";
	}
	
	@RequestMapping("/productInfo")
	public String productInfo(@RequestParam("id") Long id, Model model) {
		Product product = productService.findOne(id).get();
		model.addAttribute("product", product);
		
		return "productInfo";
	}
	
	@RequestMapping("/updateProduct")
	public String updateProduct(@RequestParam("id") Long id, Model model) {
		Optional<Product> product = productService.findOne(id);
		model.addAttribute("product", product);
		
		return "updateProduct";
	}
	
	@RequestMapping(value="/updateProduct", method=RequestMethod.POST)
	public String updateProductPost(@ModelAttribute("product") Product product, HttpServletRequest request) {
		productService.save(product);
		
		MultipartFile productImage = product.getProductImage();
		
		if(!productImage.isEmpty()) {
			try {
				byte[] bytes = productImage.getBytes();
				String name = product.getId() + ".png";
				
				Files.delete(Paths.get("src/main/resources/static/image/product/"+name));
				
				BufferedOutputStream stream = new BufferedOutputStream(
						new FileOutputStream(new File("src/main/resources/static/image/product/" + name)));
				stream.write(bytes);
				stream.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return "redirect:/product/productInfo?id="+product.getId();
	}
	
	@RequestMapping("/productList")
	public String productList(Model model) {
		List<Product> productList = productService.findAll();
		model.addAttribute("productList", productList);		
		return "productList";
		
	}
	
	@RequestMapping(value="/remove", method=RequestMethod.POST)
	public String remove(
			@ModelAttribute("id") String id, Model model
			) {
		productService.removeOne(Long.parseLong(id.substring(11)));
		List<Product> productList = productService.findAll();
		model.addAttribute("productList", productList);
		
		return "redirect:/product/productList";
	}

}
