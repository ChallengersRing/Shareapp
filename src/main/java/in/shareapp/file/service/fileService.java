package in.shareapp.file.service;

import javax.servlet.http.Part;

public interface fileService {
    public String save(Part part, String serverFileDirectory);

    public String update(String filename, Part part, String serverFileDirectory);

    public boolean delete(String filename);

    public Part fetch(String filename, String serverFileDirectory);
}
