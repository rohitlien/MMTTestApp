package com.rohit.mmttestapp.pojo;

import java.util.ArrayList;

public class Variants {
    private ArrayList<VariantGroups> variant_groups;
    private ArrayList<ArrayList<ExcludedVariant>> exclude_list;

    public ArrayList<VariantGroups> getVariant_groups() {
        return variant_groups;
    }

    public void setVariant_groups(ArrayList<VariantGroups> variant_groups) {
        this.variant_groups = variant_groups;
    }

    public ArrayList<ArrayList<ExcludedVariant>> getExclude_list() {
        return exclude_list;
    }

    public void setExclude_list(ArrayList<ArrayList<ExcludedVariant>> exclude_list) {
        this.exclude_list = exclude_list;
    }
}
