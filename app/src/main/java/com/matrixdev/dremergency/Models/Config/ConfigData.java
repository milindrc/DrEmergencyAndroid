package com.matrixdev.dremergency.Models.Config;

import java.util.ArrayList;

/**
 * Created by Milind on 7/13/2018.
 */

public class ConfigData {
    ArrayList<TypeData> types;
    ArrayList<CategoryData> categories;

    public ArrayList<TypeData> getTypes() {
        return types;
    }

    public void setTypes(ArrayList<TypeData> types) {
        this.types = types;
    }

    public ArrayList<CategoryData> getCategories() {
        return categories;
    }

    public void setCategories(ArrayList<CategoryData> categories) {
        this.categories = categories;
    }
}
