package com.example.cookbook;

class Recipe {

    public String name;
    public String image;
    public int rating;
    public int cost;

    public Recipe(String name, String image, int rating, int cost) {
        this.name = name;
        this.image = image;
        this.rating = rating;
        this.cost = cost;
    }

    /**
     * Check method to search the recipe with a string
     *
     * @param search
     * @return - true/false for show or hide item
     */
    public boolean check(String search) {
        if (name.toLowerCase().contains(search.toLowerCase())) { // case insensitive
            return true;
        }
        return false;
    }

}
