package com.lwt.hmall.page.util.file.type;

public enum FileTypeEnum {

    JPEG("FFD8FF","image"),
    PNG("89504E47","image"),
    GIF("47494638","image"),
    TIFF("49492A00",""),
    RTF("7B5C727466",""),
    DOC("D0CF11E0",""),
    XLS("D0CF11E0",""),
    MDB("5374616E64617264204A",""),
    BMP("424D","image"),
    DWG("41433130",""),
    PSD("38425053",""),
    XML("3C3F786D6C",""),
    HTML("68746D6C3E",""),
    PDF("255044462D312E",""),
    ZIP("504B0304",""),
    RAR("52617221",""),
    WAV("57415645",""),
    AVI("41564920","");

    private String value = "";
    private String type;

    private FileTypeEnum(String value,String type) {
        this.value = value;
        this.type=type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}