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
public class Story {

    @Id
    @JsonProperty("id")
    int story_id;
    String by;
    int descendants;
    int[] kids;
    int score;
    long time;
    String title;
    String type;
    String url;
}
