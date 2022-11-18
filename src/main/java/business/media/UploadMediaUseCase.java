package main.java.business.media;

import com.azure.core.util.BinaryData;
import main.java.DAL.BlobStorageLayer;
import main.java.utils.Hash;

public class UploadMediaUseCase {
    private static final String IMAGE_FORMAT_NAME = ".jpeg";

    public static String uploadMedia(byte[] contents) {
        BlobStorageLayer blobStorageLayer = new BlobStorageLayer();
        BinaryData binaryData = BinaryData.fromBytes(contents);
        String blobID = Hash.of(System.currentTimeMillis());
        blobID = blobID.concat(IMAGE_FORMAT_NAME);
        return blobStorageLayer.upload(blobID, binaryData);

    }
}
