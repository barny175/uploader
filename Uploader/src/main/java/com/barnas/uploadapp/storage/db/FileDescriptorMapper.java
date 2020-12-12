package com.barnas.uploadapp.storage.db;

import com.barnas.uploadapp.storage.FileDescriptor;

/**
 * @author Martin Barnas (martin.barnas@avast.com)
 * @since 07/10/2020.
 */
public class FileDescriptorMapper {
    public static FileDescriptor map(FileEntity fileEntity) {
        return new FileDescriptor(fileEntity.getFilename(), fileEntity.getSize(), fileEntity.getDescription(), fileEntity.getId());
    }

    public static FileEntity map(FileDescriptor fileDescriptor) {
        FileEntity fileEntity = new FileEntity();
        fileEntity.setDescription(fileDescriptor.getDescription());
        fileEntity.setFilename(fileDescriptor.getName());
        fileEntity.setSize(fileDescriptor.getSize());
        return fileEntity;
    }
}
