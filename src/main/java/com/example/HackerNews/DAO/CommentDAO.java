package com.example.HackerNews.DAO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class CommentDAO implements Serializable {

    @JsonIgnore
    @Id
    int id;
    @JsonProperty("text")
    String text;
    @JsonProperty("by")
    String by;

    public CommentDAO(String text, String by) {
        this.text = text;
        this.by = by;
    }
}
