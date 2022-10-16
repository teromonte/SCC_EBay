package main.java.business.media;

import com.azure.core.util.BinaryData;
import main.java.DAL.BlobStorageLayer;
import main.java.utils.Hash;

public class UploadMediaUseCase {
    private static final String IMAGE_FORMAT_NAME = ".jpeg";

    BlobStorageLayer blobStorageLayer;

    public UploadMediaUseCase() {
        blobStorageLayer = new BlobStorageLayer();
    }

    public String uploadMedia(byte[] contents) {
        BinaryData binaryData = BinaryData.fromBytes(contents);
        String blobID = Hash.of(contents);
        blobID = blobID.concat(IMAGE_FORMAT_NAME);
        return blobStorageLayer.upload(blobID, binaryData);
    }
}
