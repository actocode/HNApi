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
public class StoryDAO implements Serializable {


    @JsonIgnore
    @Id
    int id;
    @JsonProperty("by")
    String by;
    @JsonProperty("score")
    int score;
    @JsonProperty("time")
    long time;
    @JsonProperty("title")
    String title;
    @JsonProperty("url")
    String url;

    public StoryDAO(int score, long time, String title, String url, String by) {
        this.score = score;
        this.time = time;
        this.title = title;
        this.url = url;
        this.by = by;
    }


}
