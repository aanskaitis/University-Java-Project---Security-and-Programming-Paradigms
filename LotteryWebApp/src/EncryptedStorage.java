import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.*;
import java.util.ArrayList;
import java.util.List;

public class EncryptedStorage {

    private Cipher cipher;
    public KeyPair pair;

    public EncryptedStorage(){}

    public byte[] encryptData(String data) {
        try{
            // do not create another keyPair object if one already exists
            if (pair == null){
                KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
                pair = keyPairGen.generateKeyPair();
            }
            PublicKey publicKey = pair.getPublic();
            cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            cipher.update(data.getBytes());
            return cipher.doFinal();
        }
        catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException ex){
            ex.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void bytesFileWriter(String filename, byte[] data){
        try {
            // create directory if it doesn't exist
            Path path = Paths.get("EncryptedFiles");
            Files.createDirectories(path);

            OutputStream os = new FileOutputStream("EncryptedFiles/" + filename, true);

            // writes bytes (draws) to text file
            os.write(data);
            os.flush();
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public byte[] bytesFileReader(String filename){
        try {
            return Files.readAllBytes(Paths.get("EncryptedFiles/" + filename));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<String> decryptData(byte[] data) {
        try {
            List<String> decipheredText = new ArrayList<String>();
            List<byte[]> byteBlocks = splitBytesArray(data);
            for (byte[] block : byteBlocks){
                cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
                cipher.init(Cipher.DECRYPT_MODE, pair.getPrivate());
                byte[] decipheredBlock = cipher.doFinal(block);
                decipheredText.add(new String(decipheredBlock, StandardCharsets.UTF_8));
            }
            return decipheredText;
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    private List<byte[]> splitBytesArray(byte[] fileBytes){

        List<byte[]> blocks = new ArrayList<byte[]>();
        int offset = 0;
        while(offset < fileBytes.length){
            int blockLength = 256;
            byte[] byteBlock = new byte[blockLength];
            System.arraycopy(fileBytes,offset,byteBlock,0, blockLength);
            offset += blockLength;
            blocks.add(byteBlock);
        }
        return blocks;
    }
}
