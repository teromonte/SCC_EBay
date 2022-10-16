package main.java.business.media;

import com.azure.core.util.BinaryData;
import main.java.DAL.BlobStorageLayer;
import main.java.utils.Hash;

public class UploadMediaUseCase {
    BlobStorageLayer blobStorageLayer;

    public UploadMediaUseCase() {
        blobStorageLayer = new BlobStorageLayer();
    }

    public String uploadMedia(byte[] data) {
        String blobID = Hash.of(data);
        BinaryData binaryData = BinaryData.fromBytes(data);
        return blobStorageLayer.upload(blobID, binaryData);
    }
}
