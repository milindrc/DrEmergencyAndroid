package com.matrixdev.dremergency.Models.Tips;

import com.google.gson.annotations.SerializedName;

public class TipData {
    String solution;
    @SerializedName("updated_at")
    String updatedAt;
    @SerializedName("problem_id")
    int problemId;
    @SerializedName("created_at")
    String createdAt;
    int id;

    public String getSolution() {
        return solution;
    }

    public void setSolution(String solution) {
        this.solution = solution;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public int getProblemId() {
        return problemId;
    }

    public void setProblemId(int problemId) {
        this.problemId = problemId;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
