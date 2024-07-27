package com.example.HackerNews.Entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
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
public class Comment {

    @Id
    long id;
    String by;
    int descendants;
    int[] kids;
    long parent;
    long time;
    @Column(name = "text", length = 1888)
    String text;
    String type;

    public int getKidsLength() {
        if (getKids() != null)
            return kids.length;
        return 0;
    }

}
