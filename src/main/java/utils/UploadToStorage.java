package main.java.utils;

import com.azure.core.util.BinaryData;
import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobContainerClientBuilder;

import java.nio.file.Path;


public class UploadToStorage {

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Use: java scc.utils.UploadToStorage filename");
        }
        String filename = args[0];


        // Get connection string in the storage access keys page
        String storageConnectionString = "DefaultEndpointsProtocol=https;AccountName=scc55355;AccountKey=J8BcD9IJTwSh1kSa44roaJE+gKm35jfVa3XKhMOAfUWEGS/jejmrFiEk+vcjIy8MkWOkoJImSsD4+AStKIUkCw==;EndpointSuffix=core.windows.net";

        try {
            BinaryData data = BinaryData.fromFile(Path.of(filename));

            // Get container client
            BlobContainerClient containerClient = new BlobContainerClientBuilder().connectionString(storageConnectionString).containerName("images").buildClient();

            // Get client to blob
            BlobClient blob = containerClient.getBlobClient(filename);

            // Upload contents from BinaryData (check documentation for other alternatives)
            blob.upload(data);

            System.out.println("File updloaded : " + filename);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
