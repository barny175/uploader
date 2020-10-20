package com.barnas.uploadapp.storage.amazon.db;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

/**
 * @author Martin Barnas (martin.barnas@avast.com)
 * @since 07/10/2020.
 */
@Entity
@Getter
@Setter
@RequiredArgsConstructor
@Table(name = "files")
public class FileEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String filename;
    private String description;
    private int size;

    public FileEntity(String filename, String description, int size) {
        this.filename = filename;
        this.description = description;
        this.size = size;
    }
}
