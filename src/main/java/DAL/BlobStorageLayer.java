package main.java.DAL;

import com.azure.core.util.BinaryData;
import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobContainerClientBuilder;

public class BlobStorageLayer {
    private static final String storageConnectionString = "DefaultEndpointsProtocol=https;AccountName=scc55355;AccountKey=J8BcD9IJTwSh1kSa44roaJE+gKm35jfVa3XKhMOAfUWEGS/jejmrFiEk+vcjIy8MkWOkoJImSsD4+AStKIUkCw==;EndpointSuffix=core.windows.net";
    private static final String IMAGE_CONTAINER_NAME = "images";

    public BlobStorageLayer() {

    }

    private BlobContainerClient getBlobContainerClient() {
        return new BlobContainerClientBuilder()
                .connectionString(storageConnectionString)
                .containerName(IMAGE_CONTAINER_NAME)
                .buildClient();
    }

    private BlobClient getBlobClient(String blobID) {
        return getBlobContainerClient().getBlobClient(blobID);
    }

    public String upload(String blobID, BinaryData data) {
        BlobClient b = getBlobClient(blobID);
        b.upload(data);
        return b.getBlobUrl();
    }

    public BinaryData download(String blobID) {
        return getBlobClient(blobID).downloadContent();
    }
}
