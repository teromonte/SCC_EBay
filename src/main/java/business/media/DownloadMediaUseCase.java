package main.java.business.media;

import com.azure.core.util.BinaryData;
import main.java.DAL.BlobStorageLayer;

public class DownloadMediaUseCase {
    BlobStorageLayer blobStorageLayer;

    public DownloadMediaUseCase() {
        blobStorageLayer = new BlobStorageLayer();
    }

    public BinaryData downloadMedia(String blobID) {
        return blobStorageLayer.download(blobID);
    }
}
