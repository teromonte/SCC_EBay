package main.java.srv.resources;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import main.java.business.media.DownloadMediaUseCase;
import main.java.business.media.UploadMediaUseCase;

/**
 * Resource for managing media files, such as images.
 */
@Path("/media")
public class MediaResource {

    /**
     * Post a new image.The id of the image is its hash.
     */
    @POST
    @Path("/")
    @Consumes(MediaType.APPLICATION_OCTET_STREAM)
    @Produces(MediaType.APPLICATION_JSON)
    public String upload(byte[] contents) {
        UploadMediaUseCase uploadMediaUseCase = new UploadMediaUseCase();
        return uploadMediaUseCase.uploadMedia(contents);
    }

    /**
     * Return the contents of an image. Throw an appropriate error message if
     * id does not exist.
     */
    @GET
    @Path("/{blobID}")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public byte[] download(@PathParam("blobID") String blobID) {
        DownloadMediaUseCase downloadMediaUseCase = new DownloadMediaUseCase();
        return downloadMediaUseCase.downloadMedia(blobID).toBytes();
    }

}