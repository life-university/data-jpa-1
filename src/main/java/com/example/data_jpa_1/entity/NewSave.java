package com.example.data_jpa_1.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.domain.Persistable;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@AllArgsConstructor
public class NewSave implements Persistable<String> {

    @Id
    private String id;

    @CreatedDate
    private LocalDateTime createdAt;

    public NewSave(String id) {
        this.id = id;
    }

    @Override
    public boolean isNew() {
        return createdAt == null;
    }
}
