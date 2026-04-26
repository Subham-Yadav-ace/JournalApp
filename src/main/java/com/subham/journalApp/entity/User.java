package com.subham.journalApp.entity;

import lombok.Data;
import lombok.NonNull;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "users")
public class User {
    private ObjectId id;
    @Indexed(unique=true)
    @NonNull
    private String userName;
    @NonNull
    private String password;

}
