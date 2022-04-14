package com.rifatul.trackroom.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PostFile {

    @SerializedName("file")
    @Expose
    private String file;

    @SerializedName("file_type")
    @Expose
    private String file_type;


    public PostFile(String file, String file_type) {
        this.file = file;
        this.file_type = file_type;

    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getFile_type() {
        return file_type;
    }

    public void setFile_type(String file_type) {
        this.file_type = file_type;
    }
}
