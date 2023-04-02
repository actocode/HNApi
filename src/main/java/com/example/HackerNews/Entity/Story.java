package com.example.HackerNews.Entity;

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
public class Story implements Serializable {


    @JsonProperty("by")
    String by;
    @JsonProperty("descendants")
    int descendants;
    @Id
    @JsonProperty("id")
    int id;
    @JsonProperty("kids")
    int[] kids;
    @JsonProperty("score")
    int score;
    @JsonProperty("time")
    long time;
    @JsonProperty("title")
    String title;
    @JsonProperty("type")
    String type;
    @JsonProperty("url")
    String url;


}
