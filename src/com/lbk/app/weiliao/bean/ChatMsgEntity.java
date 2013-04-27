package com.lbk.app.weiliao.bean;

public class ChatMsgEntity {
	private String name;
	private String date;
	private String text;
	private String imageUrl;
	private int layoutID;

	private int TtmType;
	private int TtmTuID;
	private int TtmToUserId;
	private String TtmContent;
	private String ImageFile;
	private String VoiceFile;
	private String TtmTime;

	private String imageId;

	private int type;

	private int isRead; // 0表示未读，1表示已读
	private int isReplyLocation; // 0表示未回复，1表示已回复

	private int isSendImg;

	public ChatMsgEntity() {
	}

	public ChatMsgEntity(String TtmContent, int layoutID, String imageId,
			String TtmTime) {

		this.layoutID = layoutID;
		this.imageId = imageId;
		this.TtmContent = TtmContent;
		this.TtmTime = TtmTime;
	}

	public ChatMsgEntity(String text, int layoutID, String imageId,
			String ImageFile, String VoiceFile) {

		this.text = text;
		this.layoutID = layoutID;
		this.imageId = imageId;
		this.ImageFile = ImageFile;
		this.VoiceFile = VoiceFile;
	}

	public ChatMsgEntity(int TtmTuID, int TtmToUserId, String TtmContent,
			int layoutID, String imageId, int TtmType, String TtmTime) {

		this.TtmTuID = TtmTuID;
		this.TtmToUserId = TtmToUserId;
		this.TtmContent = TtmContent;
		this.layoutID = layoutID;
		this.imageId = imageId;
		this.TtmType = TtmType;
		this.TtmTime = TtmTime;
	}

	public ChatMsgEntity(int TtmType, int TtmTuID, int TtmToUserId,
			String TtmContent, String TtmTime, int isRead, int isReplyLocation) {

		this.TtmType = TtmType;
		this.TtmTuID = TtmTuID;
		this.TtmToUserId = TtmToUserId;
		this.TtmContent = TtmContent;
		this.TtmTime = TtmTime;
		this.isRead = isRead;
		this.isReplyLocation = isReplyLocation;

	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public int getLayoutID() {
		return layoutID;
	}

	public void setLayoutID(int layoutID) {
		this.layoutID = layoutID;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public int getTtmType() {
		return TtmType;
	}

	public void setTtmType(int ttmType) {
		TtmType = ttmType;
	}

	public int getTtmTuID() {
		return TtmTuID;
	}

	public void setTtmTuID(int ttmTuID) {
		TtmTuID = ttmTuID;
	}

	public int getTtmToUserId() {
		return TtmToUserId;
	}

	public void setTtmToUserId(int ttmToUserId) {
		TtmToUserId = ttmToUserId;
	}

	public String getTtmContent() {
		return TtmContent;
	}

	public void setTtmContent(String ttmContent) {
		TtmContent = ttmContent;
	}

	public String getTtmTime() {
		return TtmTime;
	}

	public void setTtmTime(String ttmTime) {
		TtmTime = ttmTime;
	}

	public String getImageId() {
		return imageId;
	}

	public void setImageId(String imageId) {
		this.imageId = imageId;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getImageFile() {
		return ImageFile;
	}

	public void setImageFile(String imageFile) {
		ImageFile = imageFile;
	}

	public String getVoiceFile() {
		return VoiceFile;
	}

	public void setVoiceFile(String voiceFile) {
		VoiceFile = voiceFile;
	}

	public int getIsRead() {
		return isRead;
	}

	public void setIsRead(int isRead) {
		this.isRead = isRead;
	}

	public int getIsReplyLocation() {
		return isReplyLocation;
	}

	public void setIsReplyLocation(int isReplyLocation) {
		this.isReplyLocation = isReplyLocation;
	}

	public int getIsSendImg() {
		return isSendImg;
	}

	public void setIsSendImg(int isSendImg) {
		this.isSendImg = isSendImg;
	}

}
