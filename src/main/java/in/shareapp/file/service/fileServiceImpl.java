package in.shareapp.file.service;

import javax.servlet.http.Part;

public class fileServiceImpl implements fileService{
    @Override
    public String save(Part part, String serverFileDirectory) {
        return null;
    }

    @Override
    public String update(String filename, Part part, String serverFileDirectory) {
        return null;
    }

    @Override
    public boolean delete(String filename) {
        return false;
    }

    @Override
    public Part fetch(String filename, String serverFileDirectory) {
        return null;
    }

    public String extractFilename(Part part) {
        return part.getSubmittedFileName().isEmpty() ? "PostNotReceived" : part.getSubmittedFileName();
    }
}
