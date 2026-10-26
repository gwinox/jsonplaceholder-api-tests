package api;

import models.posts.Post;
import utils.Endpoints;
import java.util.List;

public class PostsApi extends BaseApi {

    private PostsApi() {
    }

    public static ApiResponse<List<Post>> getAllPosts() {
        return getList(Endpoints.POSTS, Post.class);
    }

    public static ApiResponse<Post> getPostById(int id) {
        String endpoint = String.format("%s/%d", Endpoints.POSTS, id);
        return get(endpoint, Post.class);
    }

    public static ApiResponse<Post> createPost(Post postObj) {
        return post(Endpoints.POSTS, postObj, Post.class);
    }
}