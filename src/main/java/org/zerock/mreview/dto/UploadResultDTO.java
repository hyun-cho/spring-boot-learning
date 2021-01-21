package org.zerock.mreview.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@Data
@AllArgsConstructor
public class UploadResultDTO {
    private String fileName;
    private String uuid;
    private String folderPath;

    /*
     * UploadResultDTO는 실제 파일과 관련된 모든 정보를 가진다.
     * 전체 경로를 위한 메서드
     * */
    public String getImageURL() {
        try {
            return URLEncoder.encode(folderPath + "/" + uuid + "_" + fileName, "UTF-8");
        }
        catch (UnsupportedEncodingException e){
            e.printStackTrace();
        }
        return "";
    }

}
