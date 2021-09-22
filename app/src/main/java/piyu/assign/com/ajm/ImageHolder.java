package piyu.assign.com.ajm;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;



public class ImageHolder implements  Parcelable{

    public ImageHolder() {

    }



    private String name;
    private String thumb_url;
    private Date date_published;
    private int width;
    private int height;


    private int thumb_width;
    private int thumb_height;

    private String content_url;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getThumb_url() {
        return thumb_url;
    }

    public void setThumb_url(String thumb_url) {
        this.thumb_url = thumb_url;
    }

    public Date getDate_published() {
        return date_published;
    }

    public void setDate_published(Date date_published) {
        this.date_published = date_published;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getThumb_width() {
        return thumb_width;
    }

    public void setThumb_width(int thumb_width) {
        this.thumb_width = thumb_width;
    }

    public int getThumb_height() {
        return thumb_height;
    }

    public void setThumb_height(int thumb_height) {
        this.thumb_height = thumb_height;
    }


    public String getContent_url() {
        return content_url;
    }

    public void setContent_url(String content_url) {
        this.content_url = content_url;
    }

    public ImageHolder(Parcel in) {
        super();
        readFromParcel(in);
    }

    public static final Parcelable.Creator<ImageHolder> CREATOR = new Parcelable.Creator<ImageHolder>() {
        public ImageHolder createFromParcel(Parcel in) {
            return new ImageHolder(in);
        }

        public ImageHolder[] newArray(int size) {

            return new ImageHolder[size];
        }

    };

    public void readFromParcel(Parcel in) {
        this.height = in.readInt();
        this.width = in.readInt();
        this.thumb_height = in.readInt();
        this.thumb_width = in.readInt();
        this.name=in.readString();

        this.date_published = new Date(in.readLong());
        this.content_url=in.readString();
        this.thumb_url=in.readString();


    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.getHeight());
        dest.writeInt(this.getWidth());
        dest.writeInt(this.getThumb_height());
        dest.writeInt(this.getThumb_width());
        dest.writeString(this.getName());
        dest.writeLong(this.getDate_published().getTime());
        dest.writeString(this.getContent_url());
        dest.writeString(this.getThumb_url());
    }
}
