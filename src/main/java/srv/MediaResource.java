package main.java.srv;

import com.azure.core.util.BinaryData;
import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobContainerClientBuilder;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import main.java.utils.Hash;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Resource for managing media files, such as images.
 */
@Path("/media")
public class MediaResource {

    Map<String,byte[]> map = new HashMap<String,byte[]>();

    // Get connection string in the storage access keys page
    String storageConnectionString = "DefaultEndpointsProtocol=https;AccountName=scc55355;AccountKey=J8BcD9IJTwSh1kSa44roaJE+gKm35jfVa3XKhMOAfUWEGS/jejmrFiEk+vcjIy8MkWOkoJImSsD4+AStKIUkCw==;EndpointSuffix=core.windows.net";

    /**
     * Post a new image.The id of the image is its hash.
     */
    @POST
    @Path("/")
    @Consumes(MediaType.APPLICATION_OCTET_STREAM)
    @Produces(MediaType.APPLICATION_JSON)
    public String upload(InputStream contents) throws IOException {

        byte[] b = contents.readAllBytes();

        if(b.length==0){
            return "error";
        }
        BinaryData data = BinaryData.fromBytes(b);

        // Get container client
        BlobContainerClient containerClient = new BlobContainerClientBuilder().connectionString(storageConnectionString).containerName("images").buildClient();

        String key = Hash.of(b);
        map.put( key, b);

        BlobClient blob = containerClient.getBlobClient(key);

        // Upload contents from BinaryData (check documentation for other alternatives)
        blob.upload(data);

        return key;
    }

    /**
     * Return the contents of an image. Throw an appropriate error message if
     * id does not exist.
     */
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public byte[] download(@PathParam("id") String id) {
        BlobContainerClient containerClient = new BlobContainerClientBuilder().connectionString(storageConnectionString).containerName("images").buildClient();

        // Get client to blob
        BlobClient blob = containerClient.getBlobClient(id);

        // Download contents to BinaryData (check documentation for other alternatives)
        BinaryData data = blob.downloadContent();

        return data.toBytes();
    }

}