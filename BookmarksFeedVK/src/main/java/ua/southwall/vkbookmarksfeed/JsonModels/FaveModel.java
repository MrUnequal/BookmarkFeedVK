package ua.southwall.vkbookmarksfeed.JsonModels;

/**
 * Created by mac on 2/27/15.
 */
public class FaveModel {
    private String id;
    private String name;
    private String phone;
    private int canSeeAllPosts;
    private String imageUrl;

    public String getName() {
        return this.name;
    }

    public String getPhone() {
        return this.phone;
    }

    public String getImageUrl() {
        return this.imageUrl;
    }

    public static FaveModel fromJson(String recievedMessage) {
        FaveModel b = new FaveModel();
        //recievedMessage.Json
        // Deserialize json into object fields
        /*try {
            b.id = jsonObject.getString("id");
            b.name = jsonObject.getString("name");
            b.phone = jsonObject.getString("display_phone");
            b.imageUrl = jsonObject.getString("image_url");
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }*/
        // Return new object
        return b;
    }
}
