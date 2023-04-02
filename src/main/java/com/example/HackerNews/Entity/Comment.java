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
public class Comment implements Serializable {

    @JsonProperty("by")
    String by;
    @JsonProperty("descendants")
    int descendants;
    @Id
    @JsonProperty("id")
    long id;
    @JsonProperty("kids")
    int[] kids;
    @JsonProperty("parent")
    long parent;
    @JsonProperty("time")
    long time;
    @JsonProperty("text")
    String text;
    @JsonProperty("type")
    String type;

    public int getKidsLength() {
        if (getKids() != null)
            return kids.length;
        else return 0;
    }


}
