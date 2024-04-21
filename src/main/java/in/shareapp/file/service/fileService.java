package in.shareapp.file.service;

import javax.servlet.http.Part;

public interface fileService {
    String save(Part part, String serverFileDirectory);

    String update(String filename, Part part, String serverFileDirectory);

    boolean delete(String filename);

    Part fetch(String filename, String serverFileDirectory);
}
