package hung.deptrai.comicapp.model;

import android.content.Context;
import android.util.Log;

import androidx.core.content.ContextCompat;

import com.google.gson.annotations.SerializedName;

import java.util.Random;

public class Category {
    public static final String[] colorNames = {"blue","pink","orange","white","emerald_green","red","cyan"};
    @SerializedName("genreID")
    private String id;
    @SerializedName("genrename")
    private String Cat_name;
    private String description;
    private int ColorResources;
    private Context context;

    public Category(String id, String cat_name, String description) {
        this.id = id;
        Cat_name = cat_name;
        this.description = description;

    }

    public Category() {
    }

    public Category(String id, String cat_name, String description, Context context) {
        this.id = id;
        Cat_name = cat_name;
        this.description = description;
        this.context = context;
        ColorResources = getRandomColor();
    }
    private int getRandomColor(){
        int randomIndex = new Random().nextInt(colorNames.length);
        // Lấy resource id của màu từ tên màu
        int colorResource = context.getResources().getIdentifier(colorNames[randomIndex], "color", context.getPackageName());
        Log.e("color_res:",String.valueOf(colorResource) + " " + colorNames[randomIndex]);
        // Trả về giá trị màu ngẫu nhiên
        return ContextCompat.getColor(context,colorResource);
    }

    public int getColorResources() {
        return ColorResources;
    }

    public void setColorResources(int colorResources) {
        ColorResources = colorResources;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCat_name() {
        return Cat_name;
    }

    public void setCat_name(String cat_name) {
        Cat_name = cat_name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
