package tests;

import api.ApiResponse;
import api.PostsApi;
import api.UsersApi;
import aquality.selenium.browser.AqualityServices;
import base.BaseTest;
import models.posts.Post;
import models.users.User;
import org.apache.http.HttpStatus;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import utils.ConfigManager;
import utils.RandomUtils;
import utils.TestDataManager;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class PostTest extends BaseTest {

    @Test
    public void postTest() {
        AqualityServices.getLogger().info("Get all posts");

        ApiResponse<List<Post>> allPostsResponse = PostsApi.getAllPosts();
        Assert.assertEquals(allPostsResponse.getStatusCode(), HttpStatus.SC_OK,
                String.format("Expected status code %s, but got %s", HttpStatus.SC_OK, allPostsResponse.getStatusCode()));

        List<Post> postList = allPostsResponse.getBody();
        List<Post> expectedSortedList = postList.stream()
                .sorted(Comparator.comparingInt(Post::getId))
                .collect(Collectors.toList());

        Assert.assertEquals(postList, expectedSortedList, "Posts are not sorted by ID");

        AqualityServices.getLogger().info("Get post by id");

        int expectedId = ConfigManager.getInt("/testData/postToGet/id");
        int expectedUserId = ConfigManager.getInt("/testData/postToGet/userId");

        ApiResponse<Post> singlePostResponse = PostsApi.getPostById(expectedId);
        Assert.assertEquals(singlePostResponse.getStatusCode(), HttpStatus.SC_OK,
                String.format("Expected status code %s, but got %s", HttpStatus.SC_OK, singlePostResponse.getStatusCode()));

        Post post = singlePostResponse.getBody();

        SoftAssert postAssert = new SoftAssert();
        postAssert.assertEquals(post.getId(), expectedId,
                String.format("Post ID should be %s, but was %s", expectedId, post.getId()));
        postAssert.assertEquals(post.getUserId(), expectedUserId,
                String.format("User ID should be %s, but was %s", expectedUserId, post.getUserId()));
        postAssert.assertFalse(post.getTitle().isEmpty(),
                String.format("Title of post with ID %s is empty", expectedId));
        postAssert.assertFalse(post.getBody().isEmpty(),
                String.format("Body of post with ID %s is empty", expectedId));
        postAssert.assertAll();

        AqualityServices.getLogger().info("Get non-existent post");

        int invalidPostId = ConfigManager.getInt("/testData/invalidPostId");

        ApiResponse<Post> emptyPostResponse = PostsApi.getPostById(invalidPostId);
        Assert.assertEquals(emptyPostResponse.getStatusCode(), HttpStatus.SC_NOT_FOUND,
                String.format("Expected status code %s, but got %s", HttpStatus.SC_NOT_FOUND, emptyPostResponse.getStatusCode()));

        Post emptyPost = emptyPostResponse.getBody();

        Assert.assertEquals(emptyPost, new Post(),
                String.format("Body of post with ID %s should be empty", invalidPostId));

        AqualityServices.getLogger().info("Create post");

        Post postToCreate = new Post();
        postToCreate.setUserId(ConfigManager.getInt("/testData/userIdToCreate"));
        postToCreate.setBody(RandomUtils.generateRandomString());
        postToCreate.setTitle(RandomUtils.generateRandomString());

        ApiResponse<Post> createPostResponse = PostsApi.createPost(postToCreate);
        Assert.assertEquals(createPostResponse.getStatusCode(), HttpStatus.SC_CREATED,
                String.format("Expected status code %s, but got %s", HttpStatus.SC_CREATED, createPostResponse.getStatusCode()));

        Post createdPost = createPostResponse.getBody();

        SoftAssert createdPostAssert = new SoftAssert();
        createdPostAssert.assertEquals(createdPost.getUserId(), postToCreate.getUserId(),
                String.format("User ID should be %s, but was %s", postToCreate.getUserId(), createdPost.getUserId()));
        createdPostAssert.assertEquals(createdPost.getTitle(), postToCreate.getTitle(),
                String.format("Title should be %s, but was %s", postToCreate.getTitle(), createdPost.getTitle()));
        createdPostAssert.assertEquals(createdPost.getBody(), postToCreate.getBody(),
                String.format("Body should be %s, but was %s", postToCreate.getBody(), createdPost.getBody()));
        createdPostAssert.assertTrue(createdPost.getId() > 0,
                String.format("Generated ID should be greater than 0, but was %s", createdPost.getId()));
        createdPostAssert.assertAll();

        AqualityServices.getLogger().info("Get all users");

        ApiResponse<List<User>> allUsersResponse = UsersApi.getAllUsers();
        Assert.assertEquals(allUsersResponse.getStatusCode(), HttpStatus.SC_OK,
                String.format("Expected status code %s, but got %s", HttpStatus.SC_OK, allUsersResponse.getStatusCode()));

        List<User> userList = allUsersResponse.getBody();
        User expectedUser = TestDataManager.getExpectedUser();
        User actualUser = userList.stream()
                .filter(item -> item.getId() == expectedUser.getId())
                .findFirst()
                .orElse(null);

        Assert.assertEquals(actualUser, expectedUser,
                String.format("Actual user data for user ID %s does not match expected user data", expectedUser.getId()));

        AqualityServices.getLogger().info("Get user by id");

        ApiResponse<User> singleUserResponse = UsersApi.getUserById(expectedUser.getId());
        Assert.assertEquals(singleUserResponse.getStatusCode(), HttpStatus.SC_OK,
                String.format("Expected status code %s, but got %s", HttpStatus.SC_OK, singleUserResponse.getStatusCode()));

        User singleUser = singleUserResponse.getBody();

        Assert.assertEquals(singleUser, actualUser,
                String.format("Single user data does not match the user data from the list for user ID %s", expectedUser.getId()));
    }
}