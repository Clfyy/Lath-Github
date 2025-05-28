package com.example.Simple_Blog.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.Simple_Blog.model.Post;
import com.example.Simple_Blog.repository.PostRepository;

@Controller
public class PostController {

    private PostRepository repo;

    public PostController(PostRepository postRepository) {
        this.repo = postRepository;
    }

    // Halaman utama: daftar semua post
    @GetMapping("/")
    public String index(Model model) {
        List<Post> posts = repo.findAll();
        model.addAttribute("posts", posts);
        return "index";
    }

    // Tampilkan detail post
    @GetMapping("/posts/{id}")
    public String detail(@PathVariable Long id, Model model) {
        Post post = repo.findById(id).orElse(null);
        if (post == null) {
            return "404";
        }
        model.addAttribute("post", post);
        return "detail";
    }

    // Form tambah post baru
    @GetMapping("/posts/new")
    public String newPostForm(Model model) {
        model.addAttribute("post", new Post());
        model.addAttribute("error", "");
        return "new_post";
    }

    // Simpan post baru
    @PostMapping("/posts")
    public String savePost(@ModelAttribute Post post, Model model) {
        if (post.getTitle() == null || post.getTitle().trim().isEmpty()
                || post.getContent() == null || post.getContent().trim().isEmpty()) {
            model.addAttribute("post", post);
            model.addAttribute("error", "Judul dan isi tidak boleh kosong.");
            return "new_post";
        }

        repo.save(post);
        return "redirect:/";
    }

    // Form edit post
    @GetMapping("/posts/edit/{id}")
    public String editPostForm(@PathVariable Long id, Model model) {
        Post post = repo.findById(id).orElse(null);
        if (post == null) {
            return "404";
        }
        model.addAttribute("post", post);
        model.addAttribute("error", "");
        return "edit_post";
    }

    // Simpan hasil edit
    @PostMapping("/posts/update")
    public String updatePost(@ModelAttribute Post post, Model model) {
        if (post.getTitle() == null || post.getTitle().trim().isEmpty()
                || post.getContent() == null || post.getContent().trim().isEmpty()) {
            model.addAttribute("post", post);
            model.addAttribute("error", "Judul dan isi tidak boleh kosong.");
            return "edit_post";
        }

        repo.save(post);
        return "redirect:/";
    }

    // Hapus post
    @PostMapping("/posts/delete/{id}")
    public String deletePost(@PathVariable Long id) {
        repo.deleteById(id);
        return "redirect:/";
    }
}
