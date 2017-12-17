package com.mycompany.capstoneproject.viewModels;

import com.mycompany.capstoneproject.dto.BlogPostCategory;

import java.util.List;

/**
 * Created by paulharding on 10/13/16.
 */
public class AddBlogPostCategoryViewModel {

    private BlogPostCategory category;
    private List<BlogPostCategory> fullCategoryList;

    public BlogPostCategory getCategory() {
        return category;
    }

    public void setCategory(BlogPostCategory category) {
        this.category = category;
    }

    public List<BlogPostCategory> getFullCategoryList() {
        return fullCategoryList;
    }

    public void setFullCategoryList(List<BlogPostCategory> fullCategoryList) {
        this.fullCategoryList = fullCategoryList;
    }

}
