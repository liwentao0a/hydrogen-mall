package com.lwt.hmall.page.config.fdfs;

import org.csource.common.MyException;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;

import java.io.IOException;

/**
 * @Author lwt
 * @Date 2020/3/20 23:13
 * @Description
 */
public class FastDFSClient {

    private String protocol = "http://";
    private String separator = "/";
    private String trackerNginxAddr = "118.178.194.239";
    private String trackerNginxPort = "8999";

    private TrackerClient trackerClient;
    private TrackerServer trackerServer;
    private StorageClient storageClient;

    public FastDFSClient() throws IOException {
        trackerClient=new TrackerClient();
        trackerServer=trackerClient.getTrackerServer();
        storageClient=new StorageClient(trackerServer);
    }

    public FastDFSClient(String protocol, String separator, String trackerNginxAddr, String trackerNginxPort) throws IOException {
        this();
        this.protocol = protocol;
        this.separator = separator;
        this.trackerNginxAddr = trackerNginxAddr;
        this.trackerNginxPort = trackerNginxPort;
    }

    /**
     * 文件上传
     * @param fileBuff
     * @param fileExtName
     * @param metaList
     * @return
     * @throws IOException
     * @throws MyException
     */
    public String uploadFile(byte[] fileBuff,String fileExtName, NameValuePair[] metaList) throws IOException, MyException {
        String[] uploadResults = storageClient.upload_file(fileBuff, fileExtName, metaList);
        String groupName = uploadResults[0];
        String remoteFileName = uploadResults[1];
        String fileAbsolutePath = getFileAbsolutePath(groupName, remoteFileName);
        return fileAbsolutePath;
    }

    /**
     * 文件下载路径拼接
     * @param groupName
     * @param remoteFileName
     * @return
     */
    private String getFileAbsolutePath(String groupName, String remoteFileName) {
        return protocol + trackerNginxAddr + (trackerNginxPort==null?"":":") + trackerNginxPort +
                separator + groupName +
                separator + remoteFileName;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getSeparator() {
        return separator;
    }

    public void setSeparator(String separator) {
        this.separator = separator;
    }

    public String getTrackerNginxAddr() {
        return trackerNginxAddr;
    }

    public void setTrackerNginxAddr(String trackerNginxAddr) {
        this.trackerNginxAddr = trackerNginxAddr;
    }

    public String getTrackerNginxPort() {
        return trackerNginxPort;
    }

    public void setTrackerNginxPort(String trackerNginxPort) {
        this.trackerNginxPort = trackerNginxPort;
    }

    public TrackerClient getTrackerClient() {
        return trackerClient;
    }

    public void setTrackerClient(TrackerClient trackerClient) {
        this.trackerClient = trackerClient;
    }

    public TrackerServer getTrackerServer() {
        return trackerServer;
    }

    public void setTrackerServer(TrackerServer trackerServer) {
        this.trackerServer = trackerServer;
    }

    public StorageClient getStorageClient() {
        return storageClient;
    }

    public void setStorageClient(StorageClient storageClient) {
        this.storageClient = storageClient;
    }
}
